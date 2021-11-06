package com.destrokhen.mainproject

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var vibrator : Vibrator
    var canVibrate: Boolean = false
    var milliseconds : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()
        milliseconds = (SettingsValue.VibroType*100).toLong()

        val button : ImageView = findViewById<ImageView>(R.id.button_settings)
        var buttonHistory : ImageView = findViewById<ImageView>(R.id.history_button)

        findViewById<TextView>(R.id.pow).setOnClickListener(this)

//        val button_c : TextView = findViewById<TextView>(R.id.clear)
//        val button_pow : TextView = findViewById<TextView>(R.id.pow)
//        val button_sqrt : TextView = findViewById<TextView>(R.id.sqrt)
//        val button_del : TextView = findViewById<TextView>(R.id.del)
//        val button_plus : TextView = findViewById<TextView>(R.id.plus)
//        val button_qual : TextView = findViewById<TextView>(R.id.equal)
//
//        val button_one : TextView = findViewById<TextView>(R.id.but_one)
//        val button_two : TextView = findViewById<TextView>(R.id.two)
//        val button_thee : TextView = findViewById<TextView>(R.id.three)
//        val button_four : TextView = findViewById<TextView>(R.id.four)
//        val button_five : TextView = findViewById<TextView>(R.id.five)
//        val button_six : TextView = findViewById<TextView>(R.id.six)
//        val button_seven : TextView = findViewById<TextView>(R.id.seven)
//        val button_eig : TextView = findViewById<TextView>(R.id.eight)
//        val button_nine : TextView = findViewById<TextView>(R.id.nine)
//        val button_zero : TextView = findViewById<TextView>(R.id.zero)

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

        if (canVibrate && SettingsValue.VibroType > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        (SettingsValue.VibroType*100).toLong(),
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(milliseconds)
            }
        }

        startActivity(intent)
    }

    private fun openSettings() {
        Toast.makeText(this, "Нажал на настройки", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SettingsActivity::class.java)
        //intent.putExtra(SettingsActivity.PRECISION_SETTINGS_INP, PRECISION_SETTINGS)
        //startActivityForResult(intent, SettingsActivity.PRECISION_SETTINGS_REQ)

        if (canVibrate && SettingsValue.VibroType != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        (SettingsValue.VibroType*100).toLong(),
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(milliseconds)
            }
        }

        startActivity(intent)
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.pow -> {

            }
            R.id.clear -> {

            }
            R.id.equal -> {

            }
            R.id.sqrt -> {

            }
            R.id.minus -> {

            }
            R.id.plus -> {

            }
            R.id.div -> {

            }
            R.id.x -> {

            }
            R.id.b0 -> {

            }
            R.id.b1 -> {

            }
            R.id.b2 -> {

            }
            R.id.b3 -> {

            }
            R.id.b4 -> {

            }
            R.id.b5 -> {

            }
            R.id.b6 -> {

            }
            R.id.b7 -> {

            }
            R.id.b8 -> {

            }
            R.id.b9 -> {

            }
        }
    }
}
