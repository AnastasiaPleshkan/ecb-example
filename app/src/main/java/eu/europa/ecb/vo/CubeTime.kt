package eu.europa.ecb.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Using name/owner_login as primary key instead of id since name/owner_login is always available
 * vs id is not.
 */
@Entity
data class CubeTime(
        @PrimaryKey
        val id: Int,
        val rateTime: String?,
        val updatedTime: Long
) {
    constructor(rateTime: String?, updatedTime: Long) : this(0, rateTime, updatedTime)
}
