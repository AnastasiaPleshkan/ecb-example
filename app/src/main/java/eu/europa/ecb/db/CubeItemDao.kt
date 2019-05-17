package eu.europa.ecb.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.europa.ecb.vo.CubeItem

/**
 * Interface for database access on CubeItem related operations.
 */
@Dao
abstract class CubeItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg item: CubeItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertItems(list: List<CubeItem>)

    @Query("SELECT * FROM cubeitem")
    abstract fun loadItems(): LiveData<List<CubeItem>>
}
