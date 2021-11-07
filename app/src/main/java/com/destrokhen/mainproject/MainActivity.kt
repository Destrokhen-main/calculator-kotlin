package com.destrokhen.mainproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.*
import com.fathzer.soft.javaluator.DoubleEvaluator




class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val INPUTV = "INPUTV"
        val ANSWERV = "ANSWERV"
    }

    lateinit var vibrator : Vibrator
    var canVibrate: Boolean = false
    var milliseconds : Long = 0

    var current : String = ""
    var inputTotal : String = ""

    lateinit var mainInput : TextView;
    lateinit var mainAnswer : TextView;

    var InsertSqrtValue : Boolean = false;


    fun calculate(Input : String) :String {
        var answer = ""
        try {
            var x = DoubleEvaluator().evaluate(Input)
            x = String.format("%."+SettingsValue.SettingsPresition+"f", x).toDouble()
            if (x % 1.0 == 0.0) {
                answer = x.toInt().toString()
            } else {
                answer = x.toString()
            }
        } catch (ERROR:IllegalArgumentException) {
            answer = "none"
        }
        return answer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()
        milliseconds = (SettingsValue.VibroType*100).toLong()

        val button : ImageView = findViewById<ImageView>(R.id.button_settings)
        var buttonHistory : ImageView = findViewById<ImageView>(R.id.history_button)

        mainInput = findViewById<TextView>(R.id.input)
        mainAnswer = findViewById<TextView>(R.id.answer)

        findViewById<TextView>(R.id.pow).setOnClickListener(this)           // ^
        findViewById<TextView>(R.id.div).setOnClickListener(this)           // /
        findViewById<TextView>(R.id.scoupEnd).setOnClickListener(this)      // )
        findViewById<TextView>(R.id.plus).setOnClickListener(this)          // +
        findViewById<TextView>(R.id.minus).setOnClickListener(this)         // -
        findViewById<TextView>(R.id.sqrt).setOnClickListener(this)          // sqrt
        findViewById<TextView>(R.id.clear).setOnClickListener(this)         // c
        findViewById<TextView>(R.id.scoupStart).setOnClickListener(this)    // (
        findViewById<TextView>(R.id.x).setOnClickListener(this)             // x
        findViewById<TextView>(R.id.b0).setOnClickListener(this)            // 0
        findViewById<TextView>(R.id.b1).setOnClickListener(this)            // 1
        findViewById<TextView>(R.id.b2).setOnClickListener(this)            // 2
        findViewById<TextView>(R.id.b3).setOnClickListener(this)            // 3
        findViewById<TextView>(R.id.b4).setOnClickListener(this)            // 4
        findViewById<TextView>(R.id.b5).setOnClickListener(this)            // 5
        findViewById<TextView>(R.id.b6).setOnClickListener(this)            // 6
        findViewById<TextView>(R.id.b7).setOnClickListener(this)            // 7
        findViewById<TextView>(R.id.b8).setOnClickListener(this)            // 8
        findViewById<TextView>(R.id.b9).setOnClickListener(this)            // 9
        findViewById<TextView>(R.id.back).setOnClickListener(this)          // <-
        findViewById<TextView>(R.id.equalAnswer).setOnClickListener(this)   // =
        findViewById<TextView>(R.id.dot).setOnClickListener(this)           // .

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


    override fun onResume() {
        super.onResume()
        if (SettingsValue.InputV.isNotEmpty()) {
            mainInput.setText(SettingsValue.InputV)
            mainAnswer.setText(SettingsValue.AnswerV)

            SettingsValue.AnswerV = ""
            SettingsValue.InputV = ""
        }

    }
    private fun openHistory() {
        //Toast.makeText(this, "История", Toast.LENGTH_SHORT).show()
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
        //Toast.makeText(this, "Нажал на настройки", Toast.LENGTH_SHORT).show()
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

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.pow -> {
                if(current.isNotEmpty() && (current[current.length-1] in '1'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "^"
                    mainInput.setText(inputTotal + current)
                } else if (inputTotal.isNotEmpty() && (current[current.length-1] in '1'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    current = "^"
                    mainInput.setText(inputTotal+current)
                }

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
            }
            R.id.clear -> {
                current = ""
                inputTotal = ""
                mainInput.setText("")
                mainAnswer.setText("")

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
            }
            R.id.equalAnswer -> {
                if(inputTotal.isNotEmpty() || current.isNotEmpty()) {
                    var total = inputTotal + current
                    var result = calculate(total)
                    if (result == "none") {
                        Toast.makeText(this, "Ошибка в вычислениях", Toast.LENGTH_SHORT).show()
                    } else {
                        HistoryObj.ListHistory.add(ObjectHistory(total,result))
                        mainAnswer.setText(result)
                        mainInput.setText("")
                        inputTotal = ""
                        current = ""
                    }
                }

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
            }
            R.id.sqrt -> {
                if (current.isNotEmpty() && inputTotal.isEmpty() && current[current.length - 1] != '.') {
                    current = "(" + current + ")^0.5"
                    mainInput.setText(inputTotal + current)
                } else if (current.isNotEmpty() && inputTotal.isNotEmpty()) {
                    val prop = current.substring(0, 1)
                    current = current.substring(1, current.length)
                    if (current.isEmpty()) {
                        current = prop+"("
                        InsertSqrtValue = true;
                        Toast.makeText(this, "Когда закончите вбивать выражение поставиьте )", Toast.LENGTH_SHORT).show()
                    } else {
                        current = prop + "("+current+")^0.5"
                    }
                    mainInput.setText(inputTotal + current)
                }else if (current.isEmpty()) {
                    current += "("
                    InsertSqrtValue = true;
                    Toast.makeText(this, "Когда закончите вбивать выражение поставиьте )", Toast.LENGTH_SHORT).show()
                    mainInput.setText(inputTotal+current)
                }

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
            }
            R.id.minus -> {
                if (current.isNotEmpty() && (current[current.length - 1] in '1'..'9' || current[current.length - 1] == ')') && current[current.length - 1] != '.') {
                    inputTotal += current
                    current = "-"
                    mainInput.setText(inputTotal + current)
                } else if (current.isEmpty() || current.length == 1 && current[current.length - 1] !in '1'..'9') {
                    current += "-"
                    mainInput.setText(inputTotal + current)
                } else if (current.isNotEmpty() && current[current.length - 1] == '(') {
                    current += "-"
                    mainInput.setText(inputTotal + current)
                }else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '1'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "-"
                    mainInput.setText(inputTotal+current)
                }

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
            }
            R.id.plus -> {
                if(current.isNotEmpty() && (current[current.length-1] in '1'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "+"
                    mainInput.setText(inputTotal + current)
                } else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '1'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "+"
                    mainInput.setText(inputTotal+current)
                }

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
            }
            R.id.div -> {
                if(current.isNotEmpty() && (current[current.length-1] in '1'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "/"
                    mainInput.setText(inputTotal + current)
                } else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '1'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "/"
                    mainInput.setText(inputTotal+current)
                }

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
            }
            R.id.x -> {
                if(current.isNotEmpty() && (current[current.length-1] in '1'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "*"
                    mainInput.setText(inputTotal + current)
                } else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '1'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "*"
                    mainInput.setText(inputTotal+current)
                }

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
            }
            R.id.back -> {
                if(current.isNotEmpty()) {
                    current = current.substring(0, current.length - 1)
                    mainInput.setText(inputTotal + current)
                } else if (inputTotal.isNotEmpty()){
                    inputTotal = inputTotal.substring(0, inputTotal.length - 1)
                    mainInput.setText(inputTotal + current)
                }

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
            }
            R.id.scoupStart -> {
                // TODO поставить проверку что предыдущее символ знак какой-то
                current += "("
                mainInput.setText(inputTotal + current)

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
            }
            R.id.scoupEnd -> {
                // TODO поставить проверку что предыдущее символ знак какой-то

                if(InsertSqrtValue) {
                    current += ")^0.5"
                    InsertSqrtValue = false;
                    mainInput.setText(inputTotal + current)
                } else {
                    current += ")"
                    mainInput.setText(inputTotal + current)
                }

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
            }
            R.id.dot -> {
                if (current.isNotEmpty() && current[current.length-1] in '1'..'9' && current !in ".") {
                    current += "."
                    mainInput.setText(inputTotal + current)
                }

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
            }

            R.id.b0 -> {
                if (current.isNotEmpty() && (current[current.length-1] in '1'..'9' || current[current.length-1] == '.')) {
                    current += "0"
                    mainInput.setText(inputTotal + current)
                } else {
                    current += "0."
                    mainInput.setText(inputTotal + current)
                }
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b1 -> {
                current += "1"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b2 -> {
                current += "2"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b3 -> {
                current += "3"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b4 -> {
                current += "4"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b5 -> {
                current += "5"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b6 -> {
                current += "6"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b7 -> {
                current += "7"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b8 -> {
                current += "8"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
            R.id.b9 -> {
                current += "9"
                mainInput.setText(inputTotal + current)
                if (mainAnswer.text.length > 0) {
                    mainAnswer.setText("")
                }

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
            }
        }
    }
}
