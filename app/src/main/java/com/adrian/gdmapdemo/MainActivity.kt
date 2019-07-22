package com.adrian.gdmapdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
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

        mapUtils.myLocationType = MyLocationStyle.LOCATION_TYPE_SHOW
        mapUtils.zoomLevel = 15f
        addTestCircle(LatLng(113.937752, 22.545747))
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
            mapUtils.addCircle(LatLng(center.latitude + random, center.longitude + random), 5.0)
        }
    }

}
