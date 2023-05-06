package com.example.electricitysaver.databaseHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CalculationRatesDbHelper (context: Context) : SQLiteOpenHelper(context, "COST_RATE_DB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE RATE_TABLE(_id integer primary key autoincrement, CATEGORY TEXT, BLOCK1_RATE REAL, BLOCK2_RATE REAL, BLOCK3_RATE REAL, BLOCK4_RATE REAL, BLOCK5_RATE REAL)")

        // Insert the current date into the database
        val domesticValues = ContentValues().apply {
            put("CATEGORY", "domestic")
            put("BLOCK1_RATE", 42.0)
            put("BLOCK2_RATE", 42.0)
            put("BLOCK3_RATE", 50.0)
            put("BLOCK4_RATE", 50.0)
            put("BLOCK5_RATE", 75.0)
        }
        val industryValues = ContentValues().apply {
            put("CATEGORY", "industry")
            put("BLOCK1_RATE", 26.0)
        }
        val generalValues = ContentValues().apply {
            put("CATEGORY", "general")
            put("BLOCK1_RATE", 50.0)
            put("BLOCK2_RATE", 60.0)
        }

        db?.insert("RATE_TABLE", null, domesticValues)
        db?.insert("RATE_TABLE", null, industryValues)
        db?.insert("RATE_TABLE", null, generalValues)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}