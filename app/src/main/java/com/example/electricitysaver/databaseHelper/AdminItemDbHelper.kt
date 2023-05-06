package com.example.electricitysaver.databaseHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminItemDbHelper(context: Context): SQLiteOpenHelper(context, "AdminItems", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Admin_Add_Item(_id integer primary key autoincrement, CATEGORY TEXT, BRAND TEXT, WATTS INTEGER)")
        db?.execSQL("INSERT INTO Admin_Add_Item(CATEGORY,BRAND,WATTS)VALUES('TV','LG',120)")
        db?.execSQL("INSERT INTO Admin_Add_Item(CATEGORY,BRAND,WATTS)VALUES('Fridge','Singer',120)")
        db?.execSQL("INSERT INTO Admin_Add_Item(CATEGORY,BRAND,WATTS)VALUES('Rice Cooker','Panasonic',100)")
        db?.execSQL("INSERT INTO Admin_Add_Item(CATEGORY,BRAND,WATTS)VALUES('CFL','Orange',12)")
        db?.execSQL("INSERT INTO Admin_Add_Item(CATEGORY,BRAND,WATTS)VALUES('LED','Panasonic',9)")
        db?.execSQL("INSERT INTO Admin_Add_Item(CATEGORY,BRAND,WATTS)VALUES('Fan','Innovex',100)")
        db?.execSQL("INSERT INTO Admin_Add_Item(CATEGORY,BRAND,WATTS)VALUES('Iron','Singer',150)")

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}