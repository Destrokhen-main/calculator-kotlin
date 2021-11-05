package com.destrokhen.mainproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

//    companion object {
//        const val PRECISION_SETTINGS = 2
//    }

//    private val getResult = registerForActivityResult(Result()) { result ->
//        Toast.makeText(this, "Что-то", Toast.LENGTH_SHORT).show()
//    }
//
//    class Result : ActivityResultContract<Int, String?>() {
//
//        override fun createIntent(context: Context,input: Int?): Intent {
//            Toast.makeText(context, "Настройки", Toast.LENGTH_SHORT).show()
//            val intent = Intent (context, SettingsActivity::class.java)
//            //intent.putExtra(SettingsActivity.SETTINGS_RESULT_KEY, 10)
//            return intent
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): String? {
//            return intent?.getStringExtra(SettingsActivity.SETTINGS_RESULT_KEY)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : ImageView = findViewById<ImageView>(R.id.button_settings)

        button.setOnClickListener {
            openSettings()
        }
    }

    private fun openSettings() {
        Toast.makeText(this, "Нажал на настройки", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SettingsActivity::class.java)
        //intent.putExtra(SettingsActivity.PRECISION_SETTINGS_INP, PRECISION_SETTINGS)
        //startActivityForResult(intent, SettingsActivity.PRECISION_SETTINGS_REQ)
        startActivity(intent)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        data?.getIntExtra(SettingsActivity.PRECISION_SETTINGS , -1)?.let {
//            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
//        }
//    }
}