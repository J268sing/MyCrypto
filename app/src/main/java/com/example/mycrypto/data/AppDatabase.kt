package com.example.mycrypto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mycrypto.ui.utilities.ioThread

/**
 * The Room database for this app.
 */
@Database(entities = [CryptocurrencyItemHome::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptocurrencyDao(): CryptocurrencyDao

    // The AppDatabase a singleton to prevent having multiple instances of the database opened at the same time.
    companion object {
        // Marks the JVM backing field of the annotated property as volatile, meaning that writes to this field are immediately made visible to other threads.
        @Volatile
        private var instance: AppDatabase? = null

        // For Singleton instantiation.
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Creates and pre-populates the database.
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "cryptocurrency_database")
                    // Prepopulate the database after onCreate was called.
                    /*.addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Insert the data on the IO Thread.
                            ioThread {
                                //getInstance(context).cryptocurrencyDao().reloadCryptocurrencyList(PREPOPULATE_DATA)
                                //getInstance(context).cryptocurrencyDao().delete(PREPOPULATE_DATA)
                             //   getInstance(context).cryptocurrencyDao().insertDataToAllCryptocurrencyList(PREPOPULATE_DATA)
                            }
                        }
                    })*/
                    .fallbackToDestructiveMigration()
                    .build()
        }
        // Sample data.
        val btc: CryptocurrencyItemHome = CryptocurrencyItemHome("Bitcoin", 1, 0.62248, "BTC",92.0,3962.16, 0.225, -134.05, -0.455, -421.79)
        val eth: CryptocurrencyItemHome = CryptocurrencyItemHome("Etherium", 2, 6.0, "ETH", 407.45, 24414.70, 0.131, -130.96, 0.143, 34.17)
        //val xrp: CryptocurrencyItemHome = CryptocurrencyItemHome("XRP", 3, 0.0, "XRP", 0.423225, 0.10, -0.012, -35.30, -1.438, 0.0)
        //val bch: CryptocurrencyItemHome = CryptocurrencyItemHome("Bitcoin Cash", 4, 0.0, "BCH", 693.52, 01.0, 0.130, -144.40, -0.446, 40.0)
        //val btc1: CryptocurrencyItemHome = CryptocurrencyItemHome("Bitcoin", 1, 0.56822348, "BT1C",6972.90,3962.16, 0.225, -134.05, -0.455, -421.79)
        val PREPOPULATE_DATA = listOf(btc, eth)/*,xrp, bch,btc1)*/

    }
}