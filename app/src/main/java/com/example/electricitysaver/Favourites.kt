package com.example.electricitysaver

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleCursorAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.UserItemDbHelper
import kotlinx.android.synthetic.main.fav_list.*

class Favourites : AppCompatActivity() {

    lateinit var adapter: RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {

        var helper = paymentHelper(applicationContext)
        var db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM PAYMENT", null)



        super.onCreate(savedInstanceState)
        setContentView(R.layout.fav_list)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }


        layoutManager = LinearLayoutManager(this)
        pRecycler.layoutManager = layoutManager

        adapter = PaymentRecyclerAdapter(ArrayList())
        pRecycler.adapter = adapter




        val items = ArrayList<Payment>()

        while (rs.moveToNext()) {
            var name = rs.getString(2)
            var account = rs.getString(3)

Log.d("Pamitha",name)
            // Add the item to the list
            items.add(Payment(name, account))


        }

        (adapter as PaymentRecyclerAdapter).setItems(items)
    }
}