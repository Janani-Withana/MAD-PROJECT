package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.UserItemDbHelper
import kotlinx.android.synthetic.main.fav_card_view.*
import kotlinx.android.synthetic.main.fav_list.*

class Favourites : AppCompatActivity() , PaymentRecyclerAdapter.UpdateListner{

    lateinit var adapter: RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var db: SQLiteDatabase
    lateinit var rs: Cursor


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.fav_list)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }



        var helper = paymentHelper(applicationContext)
         db = helper.readableDatabase
         rs = db.rawQuery("SELECT * FROM PAYMENT", null)

        layoutManager = LinearLayoutManager(this)
        pRecycler.layoutManager = layoutManager

        adapter = PaymentRecyclerAdapter(ArrayList(),this)
        pRecycler.adapter = adapter

        val items = ArrayList<Payment>()

        while (rs.moveToNext()) {
            var name = rs.getString(2)
            var account = rs.getString(3)
            //Log.d("Pamitha",name)
            // Add the item to the list
            items.add(Payment(name, account))

        }

        (adapter as PaymentRecyclerAdapter).setItems(items)
    }

    override fun onEditClick(payment: Payment) {

        val update = findViewById<ImageView>(R.id.editPayment)

        update.setOnClickListener{

            var cv = ContentValues().apply {
                put("NAME",edtCardName.text.toString())
                put("ACCOUNT",edtCardNo.text.toString())
            }

            val selection = "_id=?"
            val count = db.update("PAYMENT",cv,selection, arrayOf(rs.getString(0)))
            if (count>0){
                var ad = AlertDialog.Builder(this)
                ad.setTitle("Update Record")
                ad.setMessage("Record Updated Successflly ....!")
                ad.setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialog: DialogInterface?, i ->

                    })
                ad.show()
            }



        }
    }
}