package com.example.electricitysaver

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog

class pamitha_payment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pamitha_payment2)

        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }
        val favourites= findViewById<Button>(R.id.viewFavourites)
        favourites.setOnClickListener {
            // on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(this)

            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.favourite_list, null)

            // below line is use to set cancelable to avoid closing of dialog box when clicking on the screen.
            dialog.setCancelable(true)

            // on below line we are setting content view to our view.
            dialog.setContentView(view)

            // on below line we are calling a show method to display a dialog.
            dialog.show()
        }
    }
}