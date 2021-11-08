package com.destrokhen.mainproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.*
import com.fathzer.soft.javaluator.DoubleEvaluator

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var vibrator : Vibrator
    private var canVibrate: Boolean = false
    var milliseconds : Long = 0

    private var current : String = ""
    private var inputTotal : String = ""

    private lateinit var mainInput : TextView;
    private lateinit var mainAnswer : TextView;

    private var insertSqrtValue : Boolean = false;


    private fun calculate(Input : String) :String {
        var answer: String = ""
        try {
            var x = DoubleEvaluator().evaluate(Input)
            x = String.format("%."+SettingsValue.SettingsPrecision+"f", x).toDouble()
            if (x % 1.0 == 0.0) {
                answer = x.toLong().toString()
            } else {
                answer = x.toString()
            }
        } catch (ERROR:IllegalArgumentException) {
            answer = "none"
        }
        return answer
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()
        milliseconds = (SettingsValue.VibratoType*100).toLong()

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
            mainInput.text = SettingsValue.InputV
            mainAnswer.text = SettingsValue.AnswerV
            inputTotal = SettingsValue.InputV
            SettingsValue.AnswerV = ""
            SettingsValue.InputV = ""
        }

    }

    private fun openHistory() {
        val intent = Intent(this, HistoryActivity::class.java)

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
        startActivity(intent)
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)

        if (canVibrate && SettingsValue.VibratoType != 0) {
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

        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.pow -> {
                if(current.isNotEmpty() && (current[current.length-1] in '0'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "^"
                    mainInput.text = inputTotal + current
                } else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '0'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "^"
                    mainInput.text = inputTotal+current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.clear -> {
                current = ""
                inputTotal = ""
                mainInput.text = ""
                mainAnswer.text = ""
                insertSqrtValue = false

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.equalAnswer -> {
                if(inputTotal.isNotEmpty() || current.isNotEmpty()) {
                    val total = inputTotal + current
                    when (val result = calculate(total)) {
                        "none" -> {
                            Toast.makeText(this, "Ошибка, выражение записано неправильно", Toast.LENGTH_SHORT).show()
                        }
                        "Infinity" -> {
                            HistoryObj.ListHistory.add(ObjectHistory(total,"ထ"))
                            mainAnswer.text = "ထ"
                            //mainInput.text = ""
                            //inputTotal = ""
                            //current = ""
                        }
                        "NaN" -> {
                            HistoryObj.ListHistory.add(ObjectHistory(total,"Не число!"))
                            mainAnswer.text = "Не число!"
                            //mainInput.text = ""
                            //inputTotal = ""
                            //current = ""
                        }
                        else -> {
                            HistoryObj.ListHistory.add(ObjectHistory(total,result))
                            mainAnswer.text = result
                            //mainInput.text = ""
                            //inputTotal = ""
                            //current = ""
                        }
                    }
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.sqrt -> {
                if (current.isNotEmpty() && inputTotal.isEmpty() && current[current.length - 1] != '.') {
                    current = "($current)^0.5"
                    mainInput.text = inputTotal + current
                } else if (current.isNotEmpty() && inputTotal.isNotEmpty()) {
                    val prop = current.substring(0, 1)
                    current = current.substring(1, current.length)
                    if (current.isEmpty()) {
                        current = "$prop("
                        insertSqrtValue = true;
                        Toast.makeText(this, "Когда закончите вбивать выражение поставьте \")\"", Toast.LENGTH_SHORT).show()
                    } else {
                        current = "$prop($current)^0.5"
                    }
                    mainInput.text = inputTotal + current
                }else if (current.isEmpty()) {
                    current += "("
                    insertSqrtValue = true;
                    Toast.makeText(this, "Когда закончите вбивать выражение поставьте \")\"", Toast.LENGTH_SHORT).show()
                    mainInput.text = inputTotal+current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.minus -> {
                if (current.isNotEmpty() && (current[current.length - 1] in '0'..'9' || current[current.length - 1] == ')') && current[current.length - 1] != '.') {
                    inputTotal += current
                    current = "-"
                    mainInput.text = inputTotal + current
                } else if (current.isEmpty() || current.length == 1 && current[current.length - 1] !in '0'..'9') {
                    current += "-"
                    mainInput.text = inputTotal + current
                } else if (current.isNotEmpty() && current[current.length - 1] == '(') {
                    current += "-"
                    mainInput.text = inputTotal + current
                }else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '0'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "-"
                    mainInput.text = inputTotal+current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.plus -> {
                if(current.isNotEmpty() && (current[current.length-1] in '0'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "+"
                    mainInput.text = inputTotal + current
                } else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '0'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "+"
                    mainInput.text = inputTotal+current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.div -> {
                if(current.isNotEmpty() && (current[current.length-1] in '0'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "/"
                    mainInput.text = inputTotal + current
                } else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '0'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "/"
                    mainInput.text = inputTotal+current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.x -> {
                if(current.isNotEmpty() && (current[current.length-1] in '0'..'9' || current[current.length-1] == ')') && current[current.length-1] != '.') {
                    inputTotal += current
                    current = "*"
                    mainInput.text = inputTotal + current
                } else if (inputTotal.isNotEmpty() && (inputTotal[inputTotal.length-1] in '0'..'9' || inputTotal[inputTotal.length-1] == ')') && inputTotal[inputTotal.length-1] != '.') {
                    current = "*"
                    mainInput.text = inputTotal+current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.back -> {
                if(current.isNotEmpty()) {
                    val board = current.substring(0,1)
                    if (board == "(" && insertSqrtValue) {
                        insertSqrtValue = false
                    }

                    current = current.substring(0, current.length - 1)
                    mainInput.text = inputTotal + current


                } else if (inputTotal.isNotEmpty()){

                    if (current.isNotEmpty()) {
                        val board = current.substring(0, 1)
                        if (board == "(" && insertSqrtValue) {
                            insertSqrtValue = false
                        }
                    } else if (current.isEmpty() && insertSqrtValue) {
                        insertSqrtValue = false
                    }

                    inputTotal = inputTotal.substring(0, inputTotal.length - 1)
                    mainInput.text = inputTotal + current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.scoupStart -> {
                current += "("
                mainInput.text = inputTotal + current

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.scoupEnd -> {
                if (current.isNotEmpty()) {
                    if (insertSqrtValue && current[current.length - 1] in '0'..'9') {
                        current += ")^0.5"
                        insertSqrtValue = false;
                        mainInput.text = inputTotal + current
                    } else if (current[current.length - 1] in '0'..'9') {
                        current += ")"
                        mainInput.text = inputTotal + current
                    }
                } else if (inputTotal.isNotEmpty()) {
                    if (insertSqrtValue && inputTotal[inputTotal.length - 1] in '0'..'9') {
                        current += ")^0.5"
                        insertSqrtValue = false;
                        mainInput.text = inputTotal + current
                    } else if (inputTotal[inputTotal.length - 1] in '0'..'9') {
                        current += ")"
                        mainInput.text = inputTotal + current
                    }
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(
                            VibrationEffect.createOneShot(
                                (SettingsValue.VibratoType * 100).toLong(),
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                    } else {
                        vibrator.vibrate(milliseconds)
                    }
                }

            }
            R.id.dot -> {
                if (current.isNotEmpty() && current[current.length-1] in '0'..'9' && current !in ".") {
                    current += "."
                    mainInput.text = inputTotal + current
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }

            R.id.b0 -> {
                if (current.isNotEmpty() && (current[current.length-1] in '0'..'9' || current[current.length-1] == '.')) {
                    current += "0"
                    mainInput.text = inputTotal + current
                } else {
                    current += "0."
                    mainInput.text = inputTotal + current
                }
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b1 -> {
                current += "1"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b2 -> {
                current += "2"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b3 -> {
                current += "3"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b4 -> {
                current += "4"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b5 -> {
                current += "5"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b6 -> {
                current += "6"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b7 -> {
                current += "7"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b8 -> {
                current += "8"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
            R.id.b9 -> {
                current += "9"
                mainInput.text = inputTotal + current
                if (mainAnswer.text.isNotEmpty()) {
                    mainAnswer.text = ""
                }

                if (canVibrate && SettingsValue.VibratoType != 0) {
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
            }
        }
    }
}
