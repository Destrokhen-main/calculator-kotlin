package com.destrokhen.mainproject

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        var buttonBack : ImageView = findViewById<ImageView>(R.id.back_to_main2)

        buttonBack.setOnClickListener {
            finish()
        }
    }
}