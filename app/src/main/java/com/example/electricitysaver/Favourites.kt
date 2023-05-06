package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
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

class Favourites : AppCompatActivity() , PaymentRecyclerAdapter.UpdateListner,PaymentRecyclerAdapter.DeleteListener{

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

        adapter = PaymentRecyclerAdapter(ArrayList(),this,this)
        pRecycler.adapter = adapter

        val items = ArrayList<Payment>()

        while (rs.moveToNext()) {
            var id = rs.getLong(0)
           // var type = rs.getString(1)
            var name = rs.getString(2)
            var account = rs.getString(3)
            //Log.d("Pamitha",name)
            // Add the item to the list
            items.add(Payment(id,name, account))

        }

        (adapter as PaymentRecyclerAdapter).setItems(items)
    }

    override fun onEditClick(payment: Payment) {


        val intent = Intent(this,PaymentUpdateDelete::class.java).apply {
            putExtra("NAME", payment.name)
            putExtra("ACCOUNT", payment.account)
            //putExtra("PAYEE",payment.type)
            putExtra("ID", payment.id)
        }
        startActivity(intent)


        }

    override fun onDeleteClick(payment: Payment) {

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to delete this record?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
// User confirmed, proceed with deletion
            db.delete("PAYMENT", "_id = ?", arrayOf(payment.id.toString()))
            rs.requery()
            adapter.notifyDataSetChanged()

// Show a success message
            val ad = AlertDialog.Builder(this)
            ad.setTitle("Delete Record")
            ad.setMessage("Record Deleted Successfully....")
            ad.setPositiveButton("OK", DialogInterface.OnClickListener{ dialogInterface, i ->

// Redirect the user to the AdminListView page
                val intent = Intent(this, Favourites::class.java)
                startActivity(intent)
                finish()
            })
            ad.show()
        }
        alertDialogBuilder.setNegativeButton("No", null)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}
