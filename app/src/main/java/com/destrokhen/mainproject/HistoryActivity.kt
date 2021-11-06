package com.destrokhen.mainproject

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    lateinit var vibrator : Vibrator
    var canVibrate: Boolean = false
    var milliseconds : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()
        milliseconds = (SettingsValue.VibroType*100).toLong()

        var buttonBack : ImageView = findViewById<ImageView>(R.id.back_to_main2)

        buttonBack.setOnClickListener {
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
            finish()
        }
    }
}