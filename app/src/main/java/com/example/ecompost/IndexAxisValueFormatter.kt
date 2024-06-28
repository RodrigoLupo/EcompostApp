package com.example.ecompost

import com.github.mikephil.charting.formatter.ValueFormatter

class IndexAxisValueFormatter(private val labels: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return labels.getOrNull(value.toInt()) ?: value.toString()
    }
}
