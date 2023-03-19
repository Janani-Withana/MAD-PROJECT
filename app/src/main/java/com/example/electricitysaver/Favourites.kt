package com.example.electricitysaver

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog

class Favourites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pamitha_payment2)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor =
                Color.parseColor("#133B5C") // Replace with your desired color
        }


    }
}