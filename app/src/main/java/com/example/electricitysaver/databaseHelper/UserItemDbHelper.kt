package com.example.electricitysaver.databaseHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserItemDbHelper(context: Context):SQLiteOpenHelper(context,"UserItem",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USER_ADD_ITEM(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CATEGORY TEXT, BRAND TEXT, QTY INTEGER, DAYUSE INTEGER, MONUSE INTEGER, NOW INTEGER, USER_ID INTEGER)")

        db?.execSQL("CREATE TABLE USER_EXPECTED_ITEM(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CATEGORY TEXT, DAYLYUSE INTEGER, MONTHLYUSE INTEGER, USER_ID INTEGER)")

        db?.execSQL("CREATE TABLE USERS(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT, EMAIL INTEGER, PASSWORD TEXT)")







    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}