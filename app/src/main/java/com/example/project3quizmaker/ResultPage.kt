package com.example.project3quizmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class ResultPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_page)


        var totalProblems: Int? = 0
        var correctAnswers: Int? = 0

        val extras = intent.extras
        if (extras != null) {
            totalProblems = extras.getInt("totalProblems")
            correctAnswers = extras.getInt("correctAnswers")
        }


        val textView: TextView = findViewById(R.id.textResult)
        textView.text = "Your score: $correctAnswers / $totalProblems"


        val clickAgain: Button =findViewById(R.id.buttonAgain)
        clickAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}