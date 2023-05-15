package com.example.electricitysaver

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
//import com.example.myapp.pamitha_payment
import org.robolectric.Robolectric
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class pamitha_paymentTest {

    @Test
    fun testAddToFavorites() {
        // Launch the activity
        val intent = Intent(ApplicationProvider.getApplicationContext(), pamitha_payment::class.java)
        val activity = Robolectric.buildActivity(pamitha_payment::class.java, intent).create().get()

        // Find the views and perform actions
//        val payeeCat = activity.findViewById(R.id.payeeCat)
//        payeeCat.performClick()
//        val ceb = activity.findViewById<TextView>(R.id.CEB)
//        ceb.performClick()
        val ptAccountName = activity.findViewById<EditText>(R.id.ptAccountName)
        ptAccountName.setText("John Doe")
        val actNumber = activity.findViewById<EditText>(R.id.ActNumber)
        actNumber.setText("1234567890")
        val etAmount = activity.findViewById<EditText>(R.id.etAmount)
        etAmount.setText("500")
        val addFav = activity.findViewById<Button>(R.id.AddFav)
        addFav.performClick()

        // Check if the record was added to the database
        val db = paymentHelper(ApplicationProvider.getApplicationContext()).readableDatabase
        val rs = db.rawQuery("SELECT * FROM PAYMENT WHERE NAME='John Doe'", null)
        assert(rs.moveToFirst())

        // Check if the success message was displayed
        val successMessage = "Record added to Favourite List successfully!"
        val toast = ShadowToast.getLatestToast()
        assertNotNull(toast)
        assertEquals(successMessage, ShadowToast.getTextOfLatestToast())

        // Close the activity and the database
        rs.close()
        db.close()
        activity.finish()
    }
}