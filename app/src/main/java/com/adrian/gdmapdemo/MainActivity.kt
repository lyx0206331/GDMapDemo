package com.adrian.gdmapdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mapUtils: GDMapUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapUtils = GDMapUtils(mapView)
        mapUtils.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapUtils.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapUtils.onResume()

//        addTestCircle(LatLng(113.944648, 22.549398))
    }

    override fun onPause() {
        super.onPause()
        mapUtils.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapUtils.onDestroy()
    }

    private fun addTestCircle(center: LatLng) {
        for (i in 1 .. 200) {
            val random = Math.random()/1000
            mapUtils.addCircle(LatLng(center.latitude, center.longitude), 5.0)
        }
    }

}
