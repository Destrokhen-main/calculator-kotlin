package com.destrokhen.mainproject

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat


class HistoryItemAdapter(private val context: Context,
                         private val dataSource: MutableList<ObjectHistory>) : BaseAdapter() {

    private val objectList: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "SimpleDateFormat")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = objectList.inflate(R.layout.item_history, parent, false)

        val titleTextView = rowView.findViewById<TextView>(R.id.titleH)

        val subtitleTextView = rowView.findViewById<TextView>(R.id.answerH)

        val dataTextview = rowView.findViewById<TextView>(R.id.dataH)

        val recipe = getItem(position) as ObjectHistory

        val pattern = "dd-MM-yyyy HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(recipe.Data)

        titleTextView.text = recipe.Input
        subtitleTextView.text = recipe.answer
        dataTextview.text = date

        rowView.setOnClickListener {
            SettingsValue.InputV = recipe.Input
            SettingsValue.AnswerV = recipe.answer
            Toast.makeText(context, "Сохранил, можете вернуться", Toast.LENGTH_SHORT).show()

        }
        return rowView
    }

}

class HistoryActivity : AppCompatActivity() {

    lateinit var vibrator : Vibrator
    var canVibrate: Boolean = false
    var milliseconds : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        vibrator = this.getSystemService(VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()
        milliseconds = (SettingsValue.VibroType*100).toLong()

        val listView: ListView = findViewById<ListView>(R.id.list_history)

        val catNames = HistoryObj.ListHistory

        val adapter = HistoryItemAdapter(this,catNames)

        listView.setAdapter(adapter)

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
            setResult(RESULT_OK)
            finish()
        }
    }
}