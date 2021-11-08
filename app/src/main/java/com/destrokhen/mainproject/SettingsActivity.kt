package com.destrokhen.mainproject

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class SettingsActivity : AppCompatActivity() , SeekBar.OnSeekBarChangeListener {
    private lateinit var vibrator : Vibrator
    private var canVibrate: Boolean = false
    var milliseconds : Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()
        milliseconds = (SettingsValue.VibratoType*100).toLong()

        val buttonBack : ImageView = findViewById(R.id.back_to_main)

        findViewById<EditText>(R.id.Number_Point_Precision).setText(SettingsValue.SettingsPrecision.toString())
        findViewById<TextView>(R.id.seekBarText).text = SettingsValue.VibratoType.toString()

        findViewById<SeekBar>(R.id.seekbar_vibro).progress = SettingsValue.VibratoType*10
        findViewById<SeekBar>(R.id.seekbar_vibro).setOnSeekBarChangeListener(this)

        findViewById<EditText>(R.id.Number_Point_Precision).addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (findViewById<EditText>(R.id.Number_Point_Precision).text.toString().isNotEmpty()) {
                    SettingsValue.SettingsPrecision = findViewById<EditText>(R.id.Number_Point_Precision).text.toString().toInt()
                }
            }

        })

        buttonBack.setOnClickListener {
            if (canVibrate && SettingsValue.VibratoType > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            (SettingsValue.VibratoType*100).toLong(),
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
        findViewById<TextView>(R.id.seekBarText).text = SettingsValue.VibratoType.toString()
        SettingsValue.VibratoType = (p1 / 10.0).roundToInt()
        findViewById<SeekBar>(R.id.seekbar_vibro).progress = SettingsValue.VibratoType*10
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        if (canVibrate && SettingsValue.VibratoType > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        (SettingsValue.VibratoType*100).toLong(),
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(milliseconds)
            }
        }

        findViewById<TextView>(R.id.seekBarText).text = SettingsValue.VibratoType.toString()
    }
}