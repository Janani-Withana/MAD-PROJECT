package com.example.electricitysaver

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.AdminItemDbHelper
import com.example.electricitysaver.databaseHelper.UserItemDbHelper

class userAddCustomItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add_custom_item)

        var helper = AdminItemDbHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM Admin_Add_Item ", null)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        // Get categories from the database and store in a list
        val categories = mutableListOf<String>()
        while (rs.moveToNext()) {
            var category = rs.getString(1)
            categories.add(category)
        }

        //Catergory Spinner
        val catespinner= findViewById<Spinner>(R.id.catspinner)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_category_layout)
        catespinner.adapter = arrayAdapter

        var watts = ""
        val brand = mutableListOf<String>()
        val bradspinner= findViewById<Spinner>(R.id.bradspinner)

        catespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
                try {
                    val rs = db.rawQuery("SELECT * FROM Admin_Add_Item WHERE CATEGORY = '$selectedCategory'", null)
                    while (rs.moveToNext()) {
                        var b = rs.getString(2)
                        brand.add(b)
                        watts = rs.getString(3)
                        Log.e("WATT",watts)
                    }
                    rs.close()
                    //Brand Spinner

                    val arrayAdapter2 = ArrayAdapter<String>(this@userAddCustomItem, android.R.layout.simple_spinner_item , brand)
                    arrayAdapter2.setDropDownViewResource(R.layout.brand_spinner)
                    bradspinner.adapter = arrayAdapter2
                } catch (e: Exception) {
                    Log.e("UserUpdateEditItems", "Error querying database: ${e.message}")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val btnuserAdd = findViewById<Button>(R.id.btnuserAdd)
        var editqty = findViewById<EditText>(R.id.editqty)
        var editdu = findViewById<EditText>(R.id.editdu)
        var editmu = findViewById<EditText>(R.id.editmu)


        btnuserAdd.setOnClickListener {
            val selectedCategory = catespinner.selectedItem.toString()
            val selectedBrand = bradspinner.selectedItem.toString()

            if(editqty.text.toString().isEmpty() ){
                editqty.error = "Quantity cannot be empty"
                Toast.makeText(this, "Quantity cannot be empty", Toast.LENGTH_SHORT).show()
            }
            if (editdu.text.toString().isEmpty()) {
                editdu.error = "Day use cannot be empty"
                Toast.makeText(this, "Day use cannot be empty or grater than 24", Toast.LENGTH_SHORT).show()
            }
            if (editmu.text.toString().isEmpty()){
                editmu.error = "Month use cannot be empty"
                Toast.makeText(this, "Month use cannot be empty or grater than 720", Toast.LENGTH_SHORT).show()
            }else{

                var helper = UserItemDbHelper(applicationContext)
                var db = helper.readableDatabase
                Log.e("WATT function",watts )

                val cv = ContentValues().apply {
                    put("CATEGORY", selectedCategory)
                    put("BRAND", selectedBrand)
                    put("QTY", editqty.text.toString().toInt())
                    put("DAYUSE", editdu.text.toString().toInt())
                    put("MONUSE", editmu.text.toString().toInt())
                    put("NOW", watts.toInt())
                }
                val rowsAffected = db.insert("USER_ADD_ITEM",null,cv)

                if (rowsAffected > 0) {
                    val alertDialog = AlertDialog.Builder(this).apply {
                        setTitle("Insert record")
                        setMessage("Item Insert successfully")
                        setPositiveButton("OK") { _, _ ->
                            // do something after the user clicks the OK button
                            editqty.setText("")
                            editdu.setText("")
                            editmu.setText("")

                        }
                    }
                    alertDialog.show()
                }else{
                    Toast.makeText(this, "Insert Not successful", Toast.LENGTH_SHORT).show()
                }


            }

        }

        val customAdd = findViewById<Button>(R.id.btnadd)
        customAdd.setOnClickListener {

            val intent = Intent(this, User_addItems::class.java)
            startActivity(intent)
        }

        val buttonList = findViewById<Button>(R.id.btnlist)
        buttonList.setOnClickListener {
            val intent = Intent(this@userAddCustomItem, ListView::class.java)
            startActivity(intent)
        }
    }
}

