package com.example.xylophone

import android.content.pm.ActivityInfo
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    
    private val soundPool = SoundPool.Builder().setMaxStreams(8).build() // 총 사용할 음원이 8개
    private val sounds = listOf(
        Pair(R.id.do1, R.raw.do1), // id가 do1인 객체를 resorce>raw의 do1과 연결
        Pair(R.id.re, R.raw.re),
        Pair(R.id.mi, R.raw.mi),
        Pair(R.id.fa, R.raw.fa),
        Pair(R.id.sol, R.raw.sol),
        Pair(R.id.la, R.raw.la),
        Pair(R.id.si, R.raw.si),
        Pair(R.id.do2, R.raw.do2)
    // Pair(first, second)
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 화면이 가로 모드로 고정되게 하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        
        // sounds 객체를 플레이 (turn)
        sounds.forEach {turn(it)} 
        // forEach: sound 객체를 순서대로 넣어주는 역할 
        // (sounds는 pair로 이루어진 객체이기 때문에 forEach를 사용하지 않으면 코드 8줄을 써줘야함)
        // 잘 모르기 때문에 it을 써준 것, pair라고 써줘도 상관 없음


    }
    
    private fun turn(pair: Pair<Int, Int>){
        val soundId = soundPool.load(this, pair.second, 1) // soundPool 객체는 플레이 전, load를 먼저 해주어야함
        findViewById<TextView>(pair.first).setOnClickListener { // R.id.do1 ~ do2 -> pair의 first 부분 (이 textview 클릭 했을 때)
            soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1.0f) // loop: 반복 횟수, rate: 재생속도
        }
    }
    
    override fun onDestroy(){ // soundPool 객체 소멸
        super.onDestroy()
        soundPool.release()
    }
}