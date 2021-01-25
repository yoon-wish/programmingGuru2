package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()

    private var cX: Float = 0f
    private var cY: Float = 0f
    private var xCoord: Float = 0f
    private var yCoord: Float = 0f
    private var valueX: Float = 0f
    private var valueY: Float = 0f

    init {
        greenPaint.color = Color.GREEN
        blackPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) { //폰 화면의 중심심
       cX = w/2f
        cY = h/2f
    }

    override fun onDraw(canvas: Canvas?){
        canvas?.drawCircle(cX, cY,100f,blackPaint) //중점 0,0 반지름 100
        canvas?.drawCircle(xCoord+cX, yCoord+cY,100f,greenPaint)

        canvas?.drawLine(cX-20, cY, cX+20,cY, blackPaint)
        canvas?.drawLine(cX, cY-20, cX,cY+20, blackPaint)

        canvas?.drawCircle(xCoord+cX, 50f,50f,greenPaint)
        canvas?.drawCircle(cX, 50f,50f,blackPaint)

        canvas?.drawCircle(50f, yCoord+cY,50f, greenPaint)
        canvas?.drawCircle(50f, cY,50f, blackPaint)

        blackPaint.textSize = 40f
        canvas?.drawText("X: " + valueX.toString() +",      Y: " + valueY.toString(), 1300f , 800f, blackPaint)
    }

    fun onSensorEvent(event: SensorEvent) {
        yCoord = event.values[0] * 20
        xCoord = event.values[1] * 20
        valueX = event.values[0]
        valueY = event.values[0]

        invalidate()
    }
}