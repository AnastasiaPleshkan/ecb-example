package eu.europa.ecb.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.europa.ecb.vo.CubeTime

/**
 * Interface for database access on CubeTime related operations.
 */
@Dao
abstract class CubeTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg cubeTimes: CubeTime)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createIfNotExists(cubeTime: CubeTime): Long

    @Query("SELECT * FROM CubeTime")
    abstract fun load(): LiveData<CubeTime>
}
