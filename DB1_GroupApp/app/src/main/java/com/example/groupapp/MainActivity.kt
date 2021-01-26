package com.example.groupapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.ButtonBarLayout

class MainActivity : AppCompatActivity() {

    lateinit var myHelper: myDBHelper
    lateinit var edtName: EditText
    lateinit var edtNumber: EditText
    lateinit var edtNameResult: EditText
    lateinit var edtNumberResult: EditText
    lateinit var btnInit: Button
    lateinit var btnInsert: Button
    lateinit var btnSelect: Button
    lateinit var sqlDB: SQLiteDatabase
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtName = findViewById<EditText>(R.id.edtName)
        edtNumber = findViewById<EditText>(R.id.edtNumber)
        edtNameResult = findViewById<EditText>(R.id.edtNameResult)
        edtNumberResult = findViewById<EditText>(R.id.edtNumberResult)

        btnInit = findViewById<Button>(R.id.btnInit)
        btnInsert = findViewById<Button>(R.id.btnInsert)
        btnSelect = findViewById<Button>(R.id.btnSelect)
        btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnDelete = findViewById<Button>(R.id.btnDelete)


        // '초기화' 클릭 시 (초기화)
        myHelper = myDBHelper(this)  // 아까 만든 myDBHelper 클래스
        btnInit.setOnClickListener {
            sqlDB = myHelper.writableDatabase // 읽기만 하면 되는 건 아니기 때문에 writableDatabase 호출
            myHelper.onUpgrade(sqlDB, 1, 2) // onUpgrade: 기존에 있던 DB를 삭제하고 다시 만들기
            sqlDB.close()
        }


        // '입력' 클릭 시 (EditText의 값이 입력됨)
        btnInsert.setOnClickListener {
            sqlDB = myHelper.writableDatabase   // writableDatabase: 읽고 쓰는 것이 가능한 DB

            sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '" // 입력할 테이블 명을 명시
                + edtName.text.toString() + "' , "  // EditText에 있던 값을 가져와 실행
                + edtNumber.text.toString() + ");") // EditText에 있던 값을 가져와 실행
            sqlDB.close()
            Toast.makeText(applicationContext, "입력됨", Toast.LENGTH_SHORT).show()

            btnSelect.callOnClick()     // '조회'에 대한 이벤트 강제 발생
        }


        // '조회' 클릭 시 (입력 결과 출력)
        btnSelect.setOnClickListener {
            sqlDB = myHelper.readableDatabase
            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null)
            // sql문이 실행된 결과 값이 반환될 것이기 때문에 raqQuery 메소드가 사용됨
            // 반환된 결과의 값은 그대로 가져오는 것이 아니라 위치를 가져옴 -> cursor로 값을 받아옴
            // 첫번째 인자 - gruopTBL에서 가져옴, 전부 가져오기 때문에 * 사용
            // 두번째 인자 - sql문에서 인자가 필요할 때 맥의 값이 필요할 때 가져옴 -> 필요 없기 때문에 null

            var strNames = "그룹이름" + "\r\n" + "--------" + "\r\n"
            var strNumbers = "인원" + "\r\n" + "--------" + "\r\n"

            while (cursor.moveToNext()) {    // cursor.moveToNext(): cursor의 위치를 다음 행으로 이동 (첫번째 레코드로 이동)
                strNames += cursor.getString(0) + "\r\n"
                strNumbers += cursor.getString(1) + "\r\n"
            }

            // 결과값 출력
            edtNameResult.setText(strNames)
            edtNumberResult.setText(strNumbers)

            cursor.close()
            sqlDB.close()

        }


        // ' 수정' 클릭 시
        btnUpdate.setOnClickListener {
            sqlDB = myHelper.writableDatabase

            sqlDB.execSQL("UPDATE groupTBL SET gNumber = " + edtNumber.text + " WHERE gName = '"
                    + edtName.text.toString() + "';")

            sqlDB.close()

            Toast.makeText(applicationContext, "수정됨", Toast.LENGTH_SHORT).show()
            btnSelect.callOnClick()
            }


        // '삭제' 클릭 시
        btnDelete.setOnClickListener {
            sqlDB = myHelper.writableDatabase

            sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '" + edtName.text.toString() + "';")

            sqlDB.close()

            Toast.makeText(applicationContext, "삭제됨", Toast.LENGTH_SHORT).show()
            btnSelect.callOnClick()
        }
    }

    // 클래스의 내부 클래스: inner class
    // SQLiteOpenHelper 클래스를 상속받아 만든 myDBHelper 클래스
    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "groupDB", null, 1){
        override fun onCreate(db: SQLiteDatabase?){
            db!!.execSQL("CREATE TABLE groupTBL ( gName CHAR(20) PRIMARY KEY, gNumber Integer);")
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersin: Int, newVersion: Int){
            db!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}