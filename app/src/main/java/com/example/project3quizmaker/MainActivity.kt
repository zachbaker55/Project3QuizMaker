package com.example.project3quizmaker

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    /*
    initializes imageview to add picture
     */
    lateinit var imageView: ImageView
    var display = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        grabs imageview from xml file
     */
        imageView=findViewById(R.id.imageview1)
        imageView.setImageResource(R.drawable.hated_math_1200x627)

        val write: TextView =findViewById(R.id.numQuestion)
        /*
            click listeners for toggling problem amount
             */
        val clickDash: Button =findViewById(R.id.buttonDash)
        clickDash.setOnClickListener {
            if(display>1){
                display--
                write.text=display.toString();
            }
        }
        /*
            adds bubbles to toggle difficulty
             */
        val rgDifficulty: RadioGroup =findViewById(R.id.groupDifficulty)
        val rgOperation: RadioGroup =findViewById(R.id.groupOperation)


        var totalProblems: Int? = 0
        var correctAnswers: Int? = 0
        var previousOperation: String? = ""

        val extras = intent.extras
        if (extras != null) {
            totalProblems = extras.getInt("totalProblems")
            correctAnswers = extras.getInt("correctAnswers")
            previousOperation = extras.getString("operation")
        }
        if (totalProblems != 0) {
            val resultMessage: TextView =findViewById(R.id.resultMessage)
            val percentCorrect: Float? = correctAnswers?.toFloat()?.div(totalProblems?.toFloat() ?: return)
            if (percentCorrect!! >= 0.8f) {
                resultMessage.text = "You got $correctAnswers out of $totalProblems correct in $previousOperation. Good work!"
            } else {
                resultMessage.setTextColor(Color.RED);
                resultMessage.text = "You got $correctAnswers out of $totalProblems correct in $previousOperation. You need to practice more!"
            }
        }


        val clickPlus: Button =findViewById(R.id.buttonPlus)
        clickPlus.setOnClickListener {
            if(display<99){
                display++
                write.text=display.toString();
            }
        }
        /*
        switches page to start quiz
     */



        val clickStart: Button =findViewById(R.id.buttonStart)
        clickStart.setOnClickListener {
            val selectedDifficultyId: Int = rgDifficulty.checkedRadioButtonId
            val selectedOperationId: Int = rgOperation.checkedRadioButtonId
            if (selectedDifficultyId != -1 && selectedOperationId != -1) {
                val difficultyButton: RadioButton = findViewById(selectedDifficultyId)
                val operationButton: RadioButton = findViewById(selectedOperationId)
                val difficulty: String = difficultyButton.text.toString()
                val operation: String = operationButton.text.toString()
                val intent = Intent(this, QuestionPage::class.java)
                intent.putExtra("operation", operation);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("totalProblems", display)
                intent.putExtra("correctAnswers", 0)
                intent.putExtra("wrongAnswers", 0)
                startActivity(intent)
            }
        }






    }

}