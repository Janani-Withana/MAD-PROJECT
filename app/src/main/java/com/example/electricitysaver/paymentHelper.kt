package com.example.electricitysaver

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class paymentHelper (context: Context) : SQLiteOpenHelper (context,"paymentDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PAYMENT(_id integer primary key autoincrement,PAYEE TEXT,NAME TEXT,ACCOUNT integer,AMOUNT integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}