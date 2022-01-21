package com.example.mycrypto.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "cryptocurrency_itemhome")
data class CryptocurrencyItemHome(@ColumnInfo(name = "name") val name: String,
                                  @ColumnInfo(name = "rank") val rank: Short,
                                  @ColumnInfo(name = "amount") val amount: Double,
                                  @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "symbol") val symbol: String,
                                  @ColumnInfo(name = "price") val price: Double,
                                  @ColumnInfo(name = "amountFiat") val amountFiat: Double,
                                  @ColumnInfo(name = "pricePercentChange1h") val pricePercentChange1h: Double,
                                  @ColumnInfo(name = "pricePercentChange7d") val pricePercentChange7d: Double,
                                  @ColumnInfo(name = "pricePercentChange24h") val pricePercentChange24h: Double,
                                  @ColumnInfo(name = "amountFiatChange24h") val amountFiatChange24h: Double)