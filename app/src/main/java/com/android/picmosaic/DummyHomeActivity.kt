package com.android.picmosaic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DummyHomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dummy_home_page)

        //go to setting page/screen
        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            Toast.makeText(this, "Loading Settings Page...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DummySettingsActivty::class.java)
            startActivity(intent)
        }
    }
}