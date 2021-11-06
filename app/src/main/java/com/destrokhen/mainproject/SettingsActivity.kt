package com.destrokhen.mainproject

import android.content.Context
import android.content.Intent
import android.media.audiofx.Equalizer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() , SeekBar.OnSeekBarChangeListener {
//    companion object {
//        const val PRECISION_SETTINGS_INP = 2
//    }


    lateinit var vibrator : Vibrator
    var canVibrate: Boolean = false
    var milliseconds : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()
        milliseconds = (SettingsValue.VibroType*100).toLong()

        //var data = intent.getIntExtra(PRECISION_SETTINGS, 2)
        //Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()
        var buttonBack : ImageView = findViewById<ImageView>(R.id.back_to_main)

        findViewById<EditText>(R.id.Number_Point_Precision).setText(SettingsValue.SettingsPresition.toString())
        findViewById<TextView>(R.id.seekBarText).setText(SettingsValue.VibroType.toString())

        findViewById<SeekBar>(R.id.seekbar_vibro).setProgress(SettingsValue.VibroType.toInt()*10)
        findViewById<SeekBar>(R.id.seekbar_vibro).setOnSeekBarChangeListener(this)


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

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        findViewById<TextView>(R.id.seekBarText).setText(SettingsValue.VibroType.toString())
        SettingsValue.VibroType = Math.round(p1 / 10.0).toInt()
        findViewById<SeekBar>(R.id.seekbar_vibro).setProgress(SettingsValue.VibroType*10)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
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
    }
}