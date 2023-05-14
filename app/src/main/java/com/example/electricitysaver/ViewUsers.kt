package com.example.electricitysaver

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.electricitysaver.databaseHelper.UserItemDbHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ViewUsers : BottomSheetDialogFragment(){
    private lateinit var db: SQLiteDatabase
    private lateinit var userCount: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.users_bottomsheet_layout, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db = UserItemDbHelper(requireContext()).readableDatabase
        userCount = view.findViewById(R.id.usersCount)

        super.onViewCreated(view, savedInstanceState)
        val countQuery = "SELECT COUNT(*) FROM USERS"
        val cursor = db.rawQuery(countQuery, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        userCount.text = count.toString()
    }
}