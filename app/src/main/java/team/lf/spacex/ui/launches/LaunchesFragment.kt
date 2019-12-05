package team.lf.spacex.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import team.lf.spacex.EventObserver
import team.lf.spacex.databinding.FragmentAllLaunchesBinding
import team.lf.spacex.domain.Launch
import timber.log.Timber

class LaunchesFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentAllLaunchesBinding

    private val viewModel by viewModels<LaunchesViewModel>()

    private lateinit var listAdapter: LaunchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAllLaunchesBinding.inflate(
            inflater, container, false
        ).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupNavigation()
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer {
            if (it) onNetworkError()
        })
        viewModel.refreshAllLaunchesFromRepository()
    }

    private fun setupNavigation() {
        viewModel.openLaunchEvent.observe(this, EventObserver {
            openLaunch(it)
        })
    }

    private fun openLaunch(it: Launch) {
        findNavController().navigate(
            LaunchesFragmentDirections.actionLaunchesFragmentToViewPagerFragment(
                it
            )
        )
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = LaunchesAdapter(viewModel)
            viewDataBinding.recycler.adapter = listAdapter
        }else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}