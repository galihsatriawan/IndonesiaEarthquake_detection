package `in`.umum.database

import `in`.umum.database.daos.OrderDao


import `in`.umum.model.OrderModel.OrderItem
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

// We list entities included in the database
@Database(entities = [(OrderItem::class)],version = 2,exportSchema = false)

abstract class AppDatabase :RoomDatabase(){
    //We extend an abstract class with RoomDatabase that gives us the Dao

    abstract fun orderDao() : OrderDao
    companion object {
        /**
         * The only instance
         */
        private var appInstance: AppDatabase? = null
        /**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         */

        @Synchronized
    fun getInstance(context: Context) :AppDatabase{
            if(appInstance==null){
                appInstance = Room
                        .databaseBuilder(context.applicationContext,AppDatabase::class.java,"BantuinDB")
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return appInstance!!
        }

    }
    fun destroyDatabase(context : Context){
        appInstance?.clearAllTables()
        appInstance = null
        getInstance(context)
    }
}