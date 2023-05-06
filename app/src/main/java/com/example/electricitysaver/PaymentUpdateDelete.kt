import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.electricitysaver.R
import com.example.electricitysaver.paymentHelper

class PaymentUpdateDelete: AppCompatActivity() {

    private  lateinit var helper: paymentHelper
    private lateinit var edtname : EditText
    private lateinit var edtnumber : EditText
    private lateinit var btnUpdate : Button

    private lateinit var db: SQLiteDatabase
    private lateinit var rs: Cursor

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_update_delete)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }
        val getName = intent.getStringExtra("NAME")
        val getNumber = intent.getStringExtra("ACCOUNT")
        val id = intent.getLongExtra("ID", -1)

        Log.d("Pamitha", "Name: $getName, Account: $getNumber, ID: $id")
        edtname= findViewById(R.id.updName)
        edtnumber = findViewById(R.id.updActNo)
        btnUpdate = findViewById(R.id.btnPaymentUpdate)

        if(edtname!=null){ edtname.setText(getName) }
        if(edtnumber!=null){ edtnumber.setText(getNumber) }

        helper = paymentHelper(this)
        db = helper.writableDatabase
        // Get the record with the specified itemId
        rs = db.rawQuery("SELECT _id, NAME, ACCOUNT FROM PAYMENT WHERE _id = ?", arrayOf(id.toString()))

        btnUpdate.setOnClickListener{
            val acname = edtname.text.toString()
            val acno =  edtnumber.text.toString()


            // Update the record with the new data
            val values = ContentValues().apply {
                put("NAME", acname)
                put("ACCOUNT", acno)

            }
            val selection = "_id = ?"
            val selectionArgs = arrayOf(id.toString())

            val count = db.update("PAYMENT", values, selection, selectionArgs)

            if (count > 0) {
                // Show a success message and finish the activity
                var ad = AlertDialog.Builder(this)
                ad.setTitle("Update Record")
                ad.setMessage("Record Updated Successfully....")
                ad.setPositiveButton("OK", DialogInterface.OnClickListener{ dialogInterface, i ->
                    edtname.setText(rs.getString(2))
                    edtnumber.setText(rs.getString(3))
                })
                ad.show()
            }else {
                // Show an error message
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Failed to update record")
                    .setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }

        }

    }
}