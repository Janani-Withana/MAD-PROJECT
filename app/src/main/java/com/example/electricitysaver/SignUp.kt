package com.example.electricitysaver

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.electricitysaver.databaseHelper.UserItemDbHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUp :  BottomSheetDialogFragment()  {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPwd: EditText
    private lateinit var reEnterPwd: EditText
    private lateinit var signUpButton: Button
    private lateinit var db: SQLiteDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_bottomsheet_layout, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = UserItemDbHelper(requireContext()).writableDatabase
        edtName = view.findViewById(R.id.edtName)
        edtEmail = view.findViewById(R.id.edtEmail)
        edtPwd = view.findViewById(R.id.edtPwd)
        reEnterPwd = view.findViewById(R.id.re_enter_pwd)
        signUpButton = view.findViewById(R.id.btnSignUp)


        val signUpButton = view.findViewById<Button>(R.id.btnSignUp)
        signUpButton.setOnClickListener {
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            when {
                edtName.text.toString().isEmpty() -> {
                    edtName.error = "Name cannot be empty"
                    Toast.makeText(requireContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
                !edtEmail.text.toString().matches(emailPattern.toRegex()) -> {
                    edtEmail.error = "Invalid email address"
                    Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show()
                    edtEmail.setText("")
                }
                edtPwd.text.toString().length < 8 -> {
                    edtPwd.error = "Password must be at least 8 characters long"
                    Toast.makeText(requireContext(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                }
                reEnterPwd.text.toString() != edtPwd.text.toString() -> {
                    reEnterPwd.error = "Passwords do not match"
                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val cv = ContentValues().apply {
                        put("USERNAME", edtName.text.toString())
                        put("EMAIL", edtEmail.text.toString())
                        put("PASSWORD", edtPwd.text.toString())
                    }

                    val rowsAffected = db.insert("USERS", null, cv)
                    if (rowsAffected > 0) {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Add record")
                            setMessage("Record Inserted Successfully....!")
                            setPositiveButton("OK") { _, _ ->
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                            }
                            show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Insert Not successful", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        db.close()
    }

}