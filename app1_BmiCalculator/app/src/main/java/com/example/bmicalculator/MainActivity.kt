package com.example.bmicalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    /* 1. 선언 */
    lateinit var resultButton: Button   //lateinit var: 추후에 초기화하기 위한 변수 타입
    lateinit var heightEditText: EditText
    lateinit var weightEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* 2. 연결 */
        resultButton = findViewById<Button>(R.id.resultButton)
        heightEditText = findViewById<EditText>(R.id.heightEditText)
        weightEditText = findViewById<EditText>(R.id.weightEditText)

        loadData()

        resultButton.setOnClickListener {

            saveData(heightEditText.text.toString().toInt(),
                     weightEditText.text.toString().toInt())
            var intent = Intent(this, ResultActivity::class.java)
            /* 3. 사용 */
            intent.putExtra("height", heightEditText.text.toString())
            intent.putExtra("weight", weightEditText.text.toString())
            startActivity(intent) //intent를 파라메터로 받아 실행을 함
        }

    }

    /*
    키와 몸무게 값을 저장하는 함수
    버튼이 클릭되자마자 저장되게 하기 위해 resultButton.setOnClickListener안에 선언됨
     */
    private fun saveData(height: Int, weight: Int){
        var pref = this.getPreferences(0) //기본값으로 씀
        var editor = pref.edit()

        editor.putInt("KEY_HEIGHT", heightEditText.text.toString().toInt()).apply() //editer 객체 안에 key값이 선언, apply통해 heightEditText에 입력한 값을 Int형으로 저장
        editor.putInt("KEY_WEIGHT", weightEditText.text.toString().toInt()).apply()
    }

    /*
    저장한 데이터를 불러오는 함수
    프로그램이 실행되자마자 수행되어야하기 때문에 앞부분에 선언됨
     */
    private fun loadData() {
        var pref=this.getPreferences(0)
        var height = pref.getInt("KEY_HEIGHT", 0)
        var weight = pref.getInt("KEY_WEIGHT", 0)

        if(height != 0 && weight != 0){
            heightEditText.setText(height.toString())
            weightEditText.setText(weight.toString())
        }
    }
}