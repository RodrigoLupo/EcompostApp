package com.example.ecompost

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstadisticasFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var token: String
    private val meses = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_estadisticas, container, false)
        lineChart = view.findViewById(R.id.lineChart)

        token = PreferenceHelper.getAccessToken(requireContext()).orEmpty()

        if (token.isNotEmpty()) {
            fetchKilosIntercambiados()
        } else {
            redirectToLogin()
        }

        return view
    }

    private fun fetchKilosIntercambiados() {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val call = apiService.getKilosIntercambiados("Bearer $token")

        call.enqueue(object : Callback<List<KilosIntercambiadosResponse>> {
            override fun onResponse(call: Call<List<KilosIntercambiadosResponse>>, response: Response<List<KilosIntercambiadosResponse>>) {
                if (response.isSuccessful) {
                    val kilosData = response.body() ?: emptyList()
                    val entries = kilosData.mapIndexed { index, data ->
                        Entry(index.toFloat(), data.total_kilos)
                    }
                    val lineDataSet = LineDataSet(entries, "Kilos Intercambiados")
                    lineDataSet.color = Color.BLUE
                    lineDataSet.valueTextColor = Color.BLACK
                    lineDataSet.setDrawCircles(true)
                    lineDataSet.circleRadius = 5f
                    lineDataSet.setDrawFilled(true)
                    lineDataSet.fillColor = Color.CYAN
                    lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                    lineDataSet.cubicIntensity = 0.2f

                    val lineData = LineData(lineDataSet)
                    lineChart.data = lineData

                    val xAxis: XAxis = lineChart.xAxis
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.granularity = 1f
                    xAxis.setDrawGridLines(false)
                    xAxis.labelRotationAngle = -45f
                    xAxis.valueFormatter = IndexAxisValueFormatter(meses)

                    val leftAxis: YAxis = lineChart.axisLeft
                    leftAxis.axisMinimum = 0f
                    val rightAxis: YAxis = lineChart.axisRight
                    rightAxis.isEnabled = false

                    val description = Description()
                    description.text = "Kilos Intercambiados por Mes"
                    lineChart.description = description

                    val legend: Legend = lineChart.legend
                    legend.isEnabled = true
                    legend.textSize = 12f

                    lineChart.animateX(1000)
                    lineChart.invalidate()
                } else {
                    redirectToLogin()
                }
            }

            override fun onFailure(call: Call<List<KilosIntercambiadosResponse>>, t: Throwable) {
                redirectToLogin()
            }
        })
    }

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
