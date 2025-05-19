package za.co.varsitycollege.imadproject2_st10479656

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity: AppCompatActivity() {

    //list of questions withe correct answer (true/false)
    private val questions = listOf(
        Question("The Earth is flat.", false),
        Question("There are 20 hours in a day.", false),
        Question("Nelson Mandela was the first black president in South Africa.", true),
        Question("Varsity College is a private institution.", true),
        Question("5 + 10 + 5 = 15.", true)
    )
    private val allCorrectAnswers: List<String> = questions.filter { it.answer }.map { it.text }
    private val correctAnswersList = mutableListOf<String>()

    // Data class for a question
    data class Question(val text: String, val answer: Boolean)


    private var currentQuestionIndex = 0
    private val totalQuestions = questions.size

    private var score = 0


    private lateinit var questionText: TextView
    private lateinit var questionCounter: TextView
    private lateinit var answerTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // remove if not used
        setContentView(R.layout.activity_second)

        supportActionBar?.title = "Second Page"


        // initialize views properly
        questionText = findViewById<TextView>(R.id.questionText)
        questionCounter = findViewById<TextView>(R.id.questionCounter)
        answerTextView = findViewById<TextView>(R.id.answerTextView)
        trueButton = findViewById<Button>(R.id.truebutton)
        falseButton = findViewById<Button>(R.id.falsebutton)
        nextButton = findViewById<Button>(R.id.nextbutton)


        // display the first question
        displayQuestion(currentQuestionIndex)

        // Button logic
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        // Next Question
        nextButton.setOnClickListener {
            if (currentQuestionIndex < totalQuestions - 1) {
                currentQuestionIndex++
                displayQuestion(currentQuestionIndex)
            } else {
                Toast.makeText(this, "Quiz Finished!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SCORE",score) // Send correct score
                intent.putExtra("TOTAL",totalQuestions) //Should be 5
                intent.putStringArrayListExtra("CORRECT_ANSWERS",ArrayList(allCorrectAnswers)) // All Answers list
                startActivity(intent)
                finish() //closes quiz screen
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayQuestion(index: Int) {
        val currentQuestion = questions[index]
        questionText.text = currentQuestion.text
        questionCounter.text = "${index + 1} / $totalQuestions"
        answerTextView.text = "" // clear previous answer result
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questions[currentQuestionIndex].answer
        if (userAnswer == correctAnswer) {
            score++ // Count all correct answers
            Toast.makeText(this, "Score: $score", Toast.LENGTH_SHORT).show()
            correctAnswersList.add(questions[currentQuestionIndex].text) // add this
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            answerTextView.text = "Correct!"
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            answerTextView.text = "Incorrect!"
        }











            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}



