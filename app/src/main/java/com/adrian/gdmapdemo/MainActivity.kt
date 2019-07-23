package com.adrian.gdmapdemo

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.model.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mapUtils: GDMapUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapUtils = GDMapUtils(mapView)
        mapUtils.onCreate(savedInstanceState)
        mapUtils.isShowMyLocationBtn = true
//        mapUtils.showMyLocation = true
        mapUtils.myLocationType = MyLocationStyle.LOCATION_TYPE_LOCATE
//        mapUtils.myLocationInterver = 2000L
        mapUtils.isMyLocationEnable = true
//        mapUtils.setLocationStyle(MyLocationStyle.LOCATION_TYPE_SHOW, 2000L)

        mapUtils.mapType = AMap.MAP_TYPE_SATELLITE

        mapUtils.zoomLevel = 17f
        mapUtils.myLocationRadiusFillColor = ContextCompat.getColor(this, R.color.translucent)
        mapUtils.myLocationStrokeColor = Color.GREEN

        val latLng = LatLng(113.937752, 22.545747)
        addMarkerTest(latLng)
//        addTestCircle(LatLng(113.937752, 22.545747))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapUtils.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapUtils.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapUtils.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapUtils.onDestroy()
    }

    private fun addMarkerTest(center: LatLng) {
        mapUtils.addMarker(
            center,
            BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.gps_point))
        )
    }

    private fun addCircleTest(center: LatLng) {
        for (i in 1..2) {
            val random = Math.random()/1000
            mapUtils.addCircle(
                LatLng(center.latitude + random, center.longitude + random),
                1000.0,
                ContextCompat.getColor(this, R.color.translucent)
            )
        }
    }

}
