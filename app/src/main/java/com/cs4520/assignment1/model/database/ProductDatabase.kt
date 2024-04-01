package com.cs4520.assignment1.model.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 4)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: ProductDatabase? = null
        @SuppressLint("StaticFieldLeak")
        private var context : Context? = null
        @Synchronized
        fun getInstance(): ProductDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context!!.applicationContext, ProductDatabase::class.java,
                    "product"
                ).allowMainThreadQueries().build()
            }

            return instance!!
        }
        fun setContext(ctx: Context){
            context = ctx
        }
    }
}
