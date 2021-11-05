package com.destrokhen.mainproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
//    companion object {
//        const val PRECISION_SETTINGS_INP = 2
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        //var data = intent.getIntExtra(PRECISION_SETTINGS, 2)
        //Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()
        var buttonBack : ImageView = findViewById<ImageView>(R.id.back_to_main)

        buttonBack.setOnClickListener {
            //setResult(RESULT_OK, Intent().putExtra(PRECISION_SETTINGS,10))
            finish()
        }
    }
}