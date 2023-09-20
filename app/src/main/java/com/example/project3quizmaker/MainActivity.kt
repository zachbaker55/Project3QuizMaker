package com.example.project3quizmaker

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    var display = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView=findViewById(R.id.imageview1)
        imageView.setImageResource(R.drawable.hated_math_1200x627)

        val write: TextView =findViewById(R.id.numQuestion)

        val clickDash: Button =findViewById(R.id.buttonDash)
        clickDash.setOnClickListener {
            if(display>1){
                display--
                write.text=display.toString();
            }
        }

        val rgDifficulty: RadioGroup =findViewById(R.id.groupDifficulty)
        val rgOperation: RadioGroup =findViewById(R.id.groupOperation)


        val clickPlus: Button =findViewById(R.id.buttonPlus)
        clickPlus.setOnClickListener {
            if(display<99){
                display++
                write.text=display.toString();
            }
        }

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