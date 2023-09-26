package com.example.project3quizmaker

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
class QuestionPage : AppCompatActivity() {
    /*
    initializes imageview to add picture
     */
    lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_page)
        var mediaPlayer = MediaPlayer()

        imageView=findViewById(R.id.imageview2)
        imageView.setImageResource(R.drawable.hated_math_1200x627)
        /*
        initializes variables that can be carried from fragment to fragment via intent
         */
        var difficulty: String? = ""
        var operation: String? = ""
        var totalProblems: Int? = 0
        var correctAnswers: Int? = 0
        var wrongAnswers: Int? = 0

        val extras = intent.extras
        if (extras != null) {
            difficulty = extras.getString("difficulty")
            operation = extras.getString("operation")
            totalProblems = extras.getInt("totalProblems")
            correctAnswers = extras.getInt("correctAnswers")
            wrongAnswers = extras.getInt("wrongAnswers")
        }

        val leftText: TextView =findViewById(R.id.leftOperand)
        val centerText: TextView =findViewById(R.id.centerOperator)
        val rightText: TextView =findViewById(R.id.rightOperand)

        displayOperation(operation, centerText)

        val leftNumber = generateNumber(difficulty)
        val rightNumber = generateNumber(difficulty)
        val correctAnswer = solveProblem(operation, leftNumber, rightNumber)

        leftText.text = leftNumber.toString()
        rightText.text = rightNumber.toString()

        /*
            gives access to textbox from the xml folder
             */
        val editText: EditText =findViewById(R.id.editText)
        val clickDone: Button =findViewById(R.id.buttonDone)
        clickDone.setOnClickListener {
            /*
            keeps track of number of wrong and right answers
     */
            val yourAnswer = Integer.parseInt(editText.text.trim().toString())
            if (yourAnswer == correctAnswer) {
                correctAnswers = correctAnswers?.inc()
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.positive))
                mediaPlayer.prepare()
                mediaPlayer.start()
                val toast = Toast.makeText(this, "Correct. Good work!", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                wrongAnswers = wrongAnswers?.inc()
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://"+this.packageName+"/"+R.raw.negative))
                mediaPlayer.prepare()
                mediaPlayer.start()
                val toast = Toast.makeText(this, "Wrong.", Toast.LENGTH_SHORT)
                toast.show()
            }
            /*
            adds to intent folder so we have relevant information stored
     */
            val totalAnswers = correctAnswers!! + wrongAnswers!!
            if (totalAnswers >= totalProblems!!) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("totalProblems", totalProblems)
                intent.putExtra("correctAnswers", correctAnswers)
                intent.putExtra("operation", operation);
                startActivity(intent)
            } else {
                val intent = Intent(this, QuestionPage::class.java)
                intent.putExtra("operation", operation);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("totalProblems", totalProblems)
                intent.putExtra("correctAnswers", correctAnswers)
                intent.putExtra("wrongAnswers", wrongAnswers)
                startActivity(intent)
            }
        }
    }

    /*
    generates a number depending on the difficulty level
     */
    private fun generateNumber(difficulty: String?): Int {
        var cap = 10
        if (difficulty == "Medium") {
            cap = 25
        } else if (difficulty == "Hard") {
            cap = 50
        }

        return Random.nextInt(1 until cap)

    }
    /*
        for generating random numbers
         */
    private fun Random.nextInt(range: IntRange): Int {
        return range.first + nextInt(range.last - range.first + 1)
    }

    /*
    converts operation into relevant symbol
     */
    private fun displayOperation(operation: String?, text: TextView) {
        if (operation == "Addition") {
            text.text = "+"
        }
        if (operation == "Subtraction") {
            text.text = "-"
        }
        if (operation == "Multiplication") {
            text.text = "x"
        }
        if (operation == "Division") {
            text.text = "÷"
        }
    }

    /*
    computes the correct answer
     */

    private fun solveProblem(operation: String?, leftNumber: Int, rightNumber: Int): Int {
        var answer = leftNumber + rightNumber
        if (operation == "Subtraction") {
            answer = leftNumber - rightNumber
        }
        if (operation == "Multiplication") {
            answer = leftNumber * rightNumber
        }
        if (operation == "Division") {
            answer = leftNumber / rightNumber
        }
        return answer
    }

}