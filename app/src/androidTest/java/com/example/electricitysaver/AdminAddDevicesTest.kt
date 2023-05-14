
package com.example.electricitysaver

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.electricitysaver.databaseHelper.AdminItemDbHelper
import kotlinx.android.synthetic.main.admin_add_devices.*
import kotlinx.android.synthetic.main.admin_add_devices.brand
import kotlinx.android.synthetic.main.admin_list_recycler_view.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AdminAddDevicesTest {

    @Test
    fun testAddItem() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val activity = AdminAddDevices()

        // Set input values
        activity.cat.setText("Test Category")
        activity.brand.setText("Test Brand")
        activity.watts.setText(100)

        // Call add item function
        activity.btnAdminAdd.performClick()

        // Verify if the record is inserted successfully
        val db = AdminItemDbHelper(appContext).readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Admin_Add_Item WHERE CATEGORY = 'Test Category'", null)

        assertEquals(1, cursor.count)

        // Cleanup test data
        db.delete("Admin_Add_Item", "CATEGORY = ?", arrayOf("Test Category"))
        cursor.close()
        db.close()
    }
}
