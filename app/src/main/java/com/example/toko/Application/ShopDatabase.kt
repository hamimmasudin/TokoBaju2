package com.example.toko.Application

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.toko.Dao.ShopDao
import com.example.toko.model.Shop


@Database(entities = [Shop::class], version = 1, exportSchema = false)
@Entity
abstract class ShopDatabase: RoomDatabase(){
    abstract fun shopDao():ShopDao

    companion object{
        private var INSTANCE : ShopDatabase?=null

        fun getDatabase(context: Context): ShopDatabase{
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(
                 context.applicationContext,
                 ShopDatabase::class.java,
                    "Shop-Database_1"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}