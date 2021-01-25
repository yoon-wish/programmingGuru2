package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import java.util.*  //timer 사용하기 위해 추가됨
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null    //타이머 Null(0)값
    private var isRunning = false
    private var lap = 1 //1부터 시작

    lateinit var fab: FloatingActionButton
    lateinit var secTextView: TextView
    lateinit var milliTextView: TextView
    lateinit var labLayout: LinearLayout
    lateinit var labButton: Button
    lateinit var resetFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab=findViewById<FloatingActionButton>(R.id.fab)
        secTextView = findViewById<TextView>(R.id.secTextView)
        milliTextView = findViewById<TextView>(R.id.milliTextView)
        labLayout = findViewById<LinearLayout>(R.id.labLayout)
        labButton = findViewById<Button>(R.id.labButton)
        resetFab = findViewById<FloatingActionButton>(R.id.resetFab)

        fab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {//isRunning이 true일 때
                start()
            } else
                pause()
        }

        labButton.setOnClickListener {
            recordLapTime()
        }

        resetFab.setOnClickListener {
            reset()
        }
    }

    private fun start(){ //스타트 함수
        fab.setImageResource(R.drawable.ic_baseline_pause_24)   //setImageResource: 이미지 바꿔주는 속성

        timerTask = timer(period=10) {
            time++  
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"

            }
        }
    }

    private fun pause(){ //정지 함수
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel() //동작 정지
    }

    private fun recordLapTime(){    //기록 함수
        val lapTime = this.time
        val textView = TextView(this) //텍스트뷰 생성자 선언, 어디에? 여기!(this)
        textView.text ="$lap LAB: ${lapTime/100}.${lapTime%100}"

        labLayout.addView(textView, 0) //앞에서 선언한 textView 붙여줌, 인덱스 값: 0(위로 쌓임), 인덱스 값: lab값(아래로 쌓임)
        lap++
    }

    private fun reset(){ //초기화 함수
        timerTask?.cancel()

        time = 0
        isRunning=false
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secTextView.text = "0"
        milliTextView.text = "00"

        labLayout.removeAllViews()
        lap = 1
    }
}
