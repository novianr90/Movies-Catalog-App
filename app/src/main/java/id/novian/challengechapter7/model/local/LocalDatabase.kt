package id.novian.challengechapter7.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.novian.challengechapter7.model.local.dao.ImageSourceDao
import id.novian.challengechapter7.model.local.dao.ProfileDao
import id.novian.challengechapter7.model.local.entity.ImageSource
import id.novian.challengechapter7.model.local.entity.Profile

@Database(entities = [Profile::class, ImageSource::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun imageSourceDao(): ImageSourceDao

    companion object {
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                synchronized(LocalDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java, "LocalDatabase.db"
                    )
//                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}