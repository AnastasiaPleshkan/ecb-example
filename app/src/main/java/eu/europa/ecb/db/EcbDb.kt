package eu.europa.ecb.db


import androidx.room.Database
import androidx.room.RoomDatabase
import eu.europa.ecb.vo.CubeItem
import eu.europa.ecb.vo.CubeTime

/**
 * Main database description.
 */
@Database(
        entities = [
            CubeTime::class,
            CubeItem::class],
        version = 1,
        exportSchema = false
)
abstract class EcbDb : RoomDatabase() {

    abstract fun cubeTimeDao(): CubeTimeDao
    abstract fun cubeItemDao(): CubeItemDao
}
