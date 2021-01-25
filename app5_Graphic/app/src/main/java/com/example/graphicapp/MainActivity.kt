package com.example.graphicapp

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyGraphicView(this))
    }

    private class MyGraphicView(context: Context) : View(context){
        override fun onDraw(canvas: Canvas?){
            super.onDraw(canvas)

            var paint = Paint()
            //drawLine: 직선
            paint.color = Color.GREEN
            paint.isAntiAlias = true
            canvas?.drawLine(10f, 10f, 300f, 10f, paint)

            paint.color = Color.BLUE
            paint.strokeWidth = 5f  //두께
            canvas?.drawLine(10f, 30f, 300f, 30f, paint)

            //drawRect: 사각형
            paint.color = Color.RED
            paint.strokeWidth = 0f

            paint.style = Paint.Style.FILL  //채우기
            var rect1 = Rect(10, 50, 10+100, 50+100)
            canvas?.drawRect(rect1, paint)

            paint.style = Paint.Style.STROKE
            var rect2 = Rect(130, 50, 130+100, 50+100)
            canvas?.drawRect(rect2, paint)

            var rect3= RectF(250f, 50f, 250f+100f, 50f+100f)    //끝부분이 약간 둥근 사각형형
           canvas?.drawRoundRect(rect3, 20f, 20f, paint)


            //drawCircle: 원
            canvas?.drawCircle(60f, 220f, 50f, paint)

            //drawPath: 경로
            paint.strokeWidth=5f
            var path1 = Path()
            path1.moveTo(10f, 290f) //시작
            path1.lineTo(10f+50f, 290f+50f) //이어지는 선
            path1.lineTo(10f+100f, 290f)
            path1.lineTo(10f+150f, 290f+50f)
            path1.lineTo(10f+200f, 290f)
            canvas?.drawPath(path1, paint)

            paint.strokeWidth = 0f
            paint.textSize = 30f
            canvas?.drawText("안드로이드", 10f, 390f, paint)

        }
    }
}