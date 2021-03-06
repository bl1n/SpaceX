package team.lf.spacex.ui.company_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.lf.spacex.data.Event
import team.lf.spacex.data.repository.SpaceXRepository
import team.lf.spacex.data.database.entity.CompanyInfo
import team.lf.spacex.data.database.entity.HistoryEvent
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel for the CompanyInfo fragment.
 *
 */

class CompanyInfoViewModel @Inject constructor(private val repository: SpaceXRepository) :
    ViewModel() {

    private val _companyInfo = repository.companyInfo
    val companyInfo: LiveData<CompanyInfo> = _companyInfo

    private val _historyEvents = repository.historyEvents
    val historyEvents: LiveData<List<HistoryEvent>> = _historyEvents

    private val _networkErrorEvent = MutableLiveData<Event<Boolean>>()
    val networkErrorEvent: LiveData<Event<Boolean>> = _networkErrorEvent

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                repository.refreshCompanyInfo()
                repository.refreshHistory()
                _networkErrorEvent.value = Event(false)
            } catch (networkError: IOException) {
                _networkErrorEvent.value = Event(true)
            }
        }
    }


}
