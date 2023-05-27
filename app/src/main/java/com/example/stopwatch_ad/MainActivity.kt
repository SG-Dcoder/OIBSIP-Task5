package com.example.stopwatch_ad


import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button

    private var handler: Handler = Handler()
    private var isTimerRunning = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener { startTimer() }
        stopButton.setOnClickListener { stopTimer() }
        resetButton.setOnClickListener { resetTimer() }
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            val currentTime = SystemClock.elapsedRealtime()
            elapsedTime = currentTime - startTime
            val minutes = (elapsedTime / 60000).toInt()
            val seconds = ((elapsedTime / 1000) % 60).toInt()
            val milliseconds = (elapsedTime % 1000).toInt()

            val timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
            timerTextView.text = timeString

            handler.postDelayed(this, 10)
        }
    }

    private fun startTimer() {
        if (!isTimerRunning) {
            startTime = SystemClock.elapsedRealtime()
            handler.postDelayed(timerRunnable, 0)
            isTimerRunning = true

            startButton.isEnabled = false
            stopButton.isEnabled = true
            resetButton.isEnabled = true
        }
    }

    private fun stopTimer() {
        if (isTimerRunning) {
            handler.removeCallbacks(timerRunnable)
            isTimerRunning = false

            startButton.isEnabled = true
            stopButton.isEnabled = false
        }
    }

    private fun resetTimer() {
        stopTimer()
        elapsedTime = 0
        timerTextView.text = "00:00:00"

        resetButton.isEnabled = false
    }
}
