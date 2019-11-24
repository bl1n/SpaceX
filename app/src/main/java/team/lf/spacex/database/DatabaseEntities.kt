package team.lf.spacex.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class Launch(
    val details: String,
    @PrimaryKey val flight_number: Int,
    val launch_date_local: String,
    val launch_date_unix: Int,
    val launch_date_utc: String,
    @Embedded val launch_site: LaunchSite,
    val launch_success: Boolean,
    val launch_year: String,
    @Embedded val links: Links,
    val mission_id: List<String>,
    val mission_name: String,
    val ships: List<String>
)
data class Links(
    val article_link: String,
    val flickr_images: List<String>,
    val mission_patch: String,
    val mission_patch_small: String,
    val presskit: String,
    val reddit_campaign: String,
    val reddit_launch: String,
    val reddit_media: String,
    val video_link: String,
    val wikipedia: String,
    val youtube_id: String
)
data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String
)