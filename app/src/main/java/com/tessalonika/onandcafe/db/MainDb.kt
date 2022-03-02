package com.tessalonika.onandcafe.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tessalonika.onandcafe.db.daos.StockDao
import com.tessalonika.onandcafe.db.daos.TableDao
import com.tessalonika.onandcafe.db.daos.UserDao
import com.tessalonika.onandcafe.model.Stock
import com.tessalonika.onandcafe.model.Table
import com.tessalonika.onandcafe.model.User

@Database(entities = [User::class, Table::class, Stock::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val tableDao: TableDao
    abstract val stockDao: StockDao

    companion object {
        @Volatile
        private var db: MainDb? = null

        fun getDb(application: Application): MainDb? {
            if (db == null) {
                synchronized(MainDb::class.java) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            application.applicationContext,
                            MainDb::class.java, "main_db"
                        ).build()
                    }
                }
            }

            return db
        }
    }
}