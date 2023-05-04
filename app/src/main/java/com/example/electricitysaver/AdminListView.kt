package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitysaver.databaseHelper.AdminItemDbHelper
import kotlinx.android.synthetic.main.admin_list_recycler_view.*

class AdminListView : AppCompatActivity(),adminListAdapter.EditListener, adminListAdapter.DeleteListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var helper: AdminItemDbHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var rs: Cursor
    private lateinit var adapter: adminListAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list_view)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        recyclerView = findViewById(R.id.adminitemreview)
        adapter = adminListAdapter(ArrayList(), this,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        helper = AdminItemDbHelper(applicationContext)
        db = helper.readableDatabase
        rs = db.rawQuery("SELECT * FROM Admin_Add_Item", null)

        val items = ArrayList<adminList>()
        while (rs.moveToNext()) {
            var itemId = rs.getLong(0)
            var category = rs.getString(1)
            var brand = rs.getString(2)
            var watts = rs.getString(3).toDouble()

            //Add Item to the list
            items.add(adminList(itemId,category,brand,watts))

        }

        adapter.setItems(items)
        adapter.notifyDataSetChanged()
    }

    override fun onEditClicked(item: adminList) {
        val intent = Intent(this, UpdateAdminItemList::class.java).apply {
            putExtra("CATEGORY", item.category)
            putExtra("BRAND", item.brand)
            putExtra("WATTS", item.watt)
            putExtra("ID", item.itemId)
        }
        startActivity(intent)
    }

    override fun onDeleteClicked(item: adminList) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure you want to delete this record?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            // User confirmed, proceed with deletion
            db.delete("Admin_Add_Item", "_id = ?", arrayOf(item.itemId.toString()))
            rs.requery()
            adapter.notifyDataSetChanged()

            // Show a success message
            val ad = AlertDialog.Builder(this)
            ad.setTitle("Delete Record")
            ad.setMessage("Record Deleted Successfully....")
            ad.setPositiveButton("OK", DialogInterface.OnClickListener{ dialogInterface, i ->

                // Redirect the user to the AdminListView page
                val intent = Intent(this, AdminListView::class.java)
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