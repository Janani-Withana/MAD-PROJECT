package com.example.electricitysaver.databaseHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import  android.database.sqlite.SQLiteOpenHelper
import java.util.*

class CostCalculationDbHelper (context: Context) : SQLiteOpenHelper(context, "COST_DB", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE COST_CALCULATION(_id integer primary key autoincrement, DATE TEXT, DAYS REAL, UNITS REAL, TOTAL_COST REAL, FIXED_CHARGE REAL, IMPORT_CHARGE REAL)")
        // Insert the current date into the database
        db?.execSQL("INSERT INTO COST_CALCULATION(DATE, DAYS, UNITS, TOTAL_COST, FIXED_CHARGE, IMPORT_CHARGE) VALUES('2023-04-19', 30, 150, 8280, 1500, 6780)")
        db?.execSQL("INSERT INTO COST_CALCULATION(DATE, DAYS, UNITS, TOTAL_COST, FIXED_CHARGE, IMPORT_CHARGE) VALUES('2023-03-30', 65, 200, 11690, 3250, 8440)")
        db?.execSQL("INSERT INTO COST_CALCULATION(DATE, DAYS, UNITS, TOTAL_COST, FIXED_CHARGE, IMPORT_CHARGE) VALUES('2023-01-05', 54, 190, 10904, 2700, 8204)")
        db?.execSQL("INSERT INTO COST_CALCULATION(DATE, DAYS, UNITS, TOTAL_COST, FIXED_CHARGE, IMPORT_CHARGE) VALUES('2023-02-12', 49, 250, 12824, 1500, 11324)")
    }



    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}