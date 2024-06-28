package com.example.ecompost

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BarChartView : View {

    private val barEntries: MutableList<Float> = mutableListOf()
    private val barColors: MutableList<Int> = mutableListOf()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setBarData(entries: List<Float>, colors: List<Int>) {
        barEntries.clear()
        barEntries.addAll(entries)
        barColors.clear()
        barColors.addAll(colors)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (canvas == null) return

        val barWidth = width / (barEntries.size * 2).toFloat()
        val maxBarHeight = height.toFloat()

        val paint = Paint()
        paint.style = Paint.Style.FILL

        barEntries.forEachIndexed { index, value ->
            paint.color = barColors.getOrElse(index) { Color.BLACK }
            val left = barWidth * (2 * index + 1)
            val top = maxBarHeight * (1 - value / MAX_VALUE) // Ajusta la altura según los valores
            val right = left + barWidth
            val bottom = maxBarHeight
            canvas.drawRect(left, top, right, bottom, paint)
        }
    }

    companion object {
        const val MAX_VALUE = 10f // Valor máximo para ajustar la altura de las barras
    }
}
