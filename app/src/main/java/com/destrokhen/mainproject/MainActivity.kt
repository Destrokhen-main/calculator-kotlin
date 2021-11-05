package com.destrokhen.mainproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

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
        var buttonHistory : ImageView = findViewById<ImageView>(R.id.history)

        val button_c : TextView = findViewById<TextView>(R.id.clear)
        val button_pow : TextView = findViewById<TextView>(R.id.pow)
        val button_sqrt : TextView = findViewById<TextView>(R.id.sqrt)
        val button_del : TextView = findViewById<TextView>(R.id.del)
        val button_plus : TextView = findViewById<TextView>(R.id.plus)
        val button_qual : TextView = findViewById<TextView>(R.id.equal)

        val button_one : TextView = findViewById<TextView>(R.id.but_one)
        val button_two : TextView = findViewById<TextView>(R.id.two)
        val button_thee : TextView = findViewById<TextView>(R.id.three)
        val button_four : TextView = findViewById<TextView>(R.id.four)
        val button_five : TextView = findViewById<TextView>(R.id.five)
        val button_six : TextView = findViewById<TextView>(R.id.six)
        val button_seven : TextView = findViewById<TextView>(R.id.seven)
        val button_eig : TextView = findViewById<TextView>(R.id.eight)
        val button_nine : TextView = findViewById<TextView>(R.id.nine)
        val button_zero : TextView = findViewById<TextView>(R.id.zero)

        val input: TextView = findViewById<TextView>(R.id.input)
        val answer : TextView = findViewById<TextView>(R.id.answer)
        //input.setText("sss")

        buttonHistory.setOnClickListener {
            openHistory()
        }

        button.setOnClickListener {
            openSettings()
        }
    }

    private fun openHistory() {
        Toast.makeText(this, "История", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
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