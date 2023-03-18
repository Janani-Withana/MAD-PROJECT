package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialog

class AdminDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }

        val btnViewUsers = findViewById<ImageButton>(R.id.btnUser)
        btnViewUsers.setOnClickListener {
            // on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(this)

            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.users_bottomsheet_layout, null)

            // below line is use to set cancelable to avoid closing of dialog box when clicking on the screen.
            dialog.setCancelable(true)

            // on below line we are setting content view to our view.
            dialog.setContentView(view)

            // on below line we are calling a show method to display a dialog.
            dialog.show()
        }
    }
}