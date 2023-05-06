package com.example.electricitysaver

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUp :  BottomSheetDialogFragment()  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_bottomsheet_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpButton = view.findViewById<Button>(R.id.btnSignUp)
        signUpButton.setOnClickListener {
            val mainIntent = Intent(requireContext(), HomePage::class.java)
            startActivity(mainIntent)
        }
    }
 /*
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.signup_bottomsheet_layout)
//
//        supportActionBar?.hide()
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.navigationBarColor = Color.parseColor("#133B5C") // Replace with your desired color
//        }
//    }*/
}