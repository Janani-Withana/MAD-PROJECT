package com.example.electricitysaver

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.android.synthetic.main.activity_cost_history.*
import kotlinx.android.synthetic.main.activity_pamitha_payment2.*
import org.json.JSONException
import org.json.JSONObject


class pamitha_payment : AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    lateinit var rs: Cursor
    lateinit var adapter: SimpleCursorAdapter
    var isNightModeOn = false

    var paymentSheet: PaymentSheet? = null
    var paymentIntentClientSecret: String? = null
    var configuration: PaymentSheet.CustomerConfiguration? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pamitha_payment2)


        var helper = paymentHelper(applicationContext)
        db = helper.readableDatabase

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }
        val favourites = findViewById<Button>(R.id.viewFavourites)
        favourites.setOnClickListener {

            val mainIntent = Intent(this@pamitha_payment, Favourites::class.java)
            startActivity(mainIntent)
            //finish()

        }


        AddFav.setOnClickListener {
            val payee = payeeCat.selectedItem.toString()
            val name = ptAccountName.text.toString()
            val account = ActNumber.text.toString()
            val amount = etAmount.text.toString()

            if (payee != "CEYLON ELECTRICITY BOARD[CEB]" && payee != "LANKA ELECTRICITY COMPANY(PVT)LTD[LECO]") {
                Toast.makeText(this, "Please select a valid payee category", Toast.LENGTH_SHORT)
                    .show()
            } else if (name.isEmpty()) {
                Toast.makeText(this, "Please Enter The Account Holder Name", Toast.LENGTH_SHORT)
                    .show()
            } else if (account.length != 10) {
                // Display error message if account number does not have exactly 10 digits
                Toast.makeText(this, "Please enter a 10-digit account number", Toast.LENGTH_SHORT)
                    .show()
            } else if (payee.isEmpty() || name.isEmpty() || account.isEmpty() || amount.isEmpty()) {
                // Display error message if any field is empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Insert the record into the database
                val cv = ContentValues()
                cv.put("PAYEE", payee)
                cv.put("NAME", name)
                cv.put("ACCOUNT", account)
                cv.put("AMOUNT", amount)
                db.insert("PAYMENT", null, cv)

                // Display success message
                AlertDialog.Builder(this)
                    .setTitle("Add To Favourite")
                    .setMessage("Record added to Favourite List successfully!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }


        val button = findViewById<Button>(R.id.btnDark)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNightModeOn = false
            button.text = "Enable Dark Mode"
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNightModeOn = true
            button.text = "Disable Dark Mode"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        button.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                button.text = "Enable Dark Mode"
                isNightModeOn = false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isNightModeOn = true
                button.text = "Disable Dark Mode"
            }
        }

//


        payNow.setOnClickListener {

            val value = "1500.00"
            fetchApi(value)





            if (paymentIntentClientSecret != null) paymentSheet!!.presentWithPaymentIntent(
                paymentIntentClientSecret!!,
                PaymentSheet.Configuration("Safe Energy", configuration)
            )
//           Toast.makeText(this, paymentSheet.toString(), Toast.LENGTH_SHORT).show()
        }
        paymentSheet = PaymentSheet(
            this
        ) { paymentSheetResult: PaymentSheetResult ->
            onPaymentSheetResult(
                paymentSheetResult
            )
        }
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        if (paymentSheetResult is PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }
        if (paymentSheetResult is PaymentSheetResult.Failed) {
            Toast.makeText(
                this,
                (paymentSheetResult as PaymentSheetResult.Failed).error.message,
                Toast.LENGTH_SHORT
            )
                .show()
        }
        if (paymentSheetResult is PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()

        }
    }

      fun fetchApi(i: String) {
        Toast.makeText(this, "Call to Fetch API", Toast.LENGTH_SHORT).show()
        val queue = Volley.newRequestQueue(this)
        val url = "http://172.28.14.90:80/PHPStripe/stripe.php"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    print(jsonObject.getString("customer"))
                    configuration = PaymentSheet.CustomerConfiguration(
                        jsonObject.getString("customer"),
                        jsonObject.getString("ephemeralKey")
                    )
                    paymentIntentClientSecret = jsonObject.getString("paymentIntent")
                    PaymentConfiguration.init(
                        applicationContext,
                        jsonObject.getString("publishableKey")
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },

            Response.ErrorListener { error -> error.printStackTrace() }) {
            override fun getParams(): Map<String, String>? {
                val paramV: MutableMap<String, String> = HashMap()

                paramV["value"] = i.toString()
                return paramV
            }
        }
        queue.add(stringRequest)
    }


}

//    private fun onResults(PaymentSheet:paymentSheet ): PaymentSheetResultCallback {
//
//    }



