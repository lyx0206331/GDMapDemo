package com.adrian.gdmapdemo

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
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
        mapUtils.showMyLocation = true
        mapUtils.myLocationType = MyLocationStyle.LOCATION_TYPE_LOCATE
        mapUtils.myLocationInterver = 2000L
        mapUtils.isMyLocationEnable = true
        mapUtils.myLocationIcon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.route_walk_select))
//        mapUtils.setLocationStyle(MyLocationStyle.LOCATION_TYPE_SHOW, 2000L, BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.gps_point)))

        mapUtils.mapType = AMap.MAP_TYPE_NORMAL

        mapUtils.zoomLevel = 17f
        mapUtils.myLocationRadiusFillColor = ContextCompat.getColor(this, R.color.translucent)
        mapUtils.myLocationStrokeColor = Color.GREEN

        mapUtils.onMyLocationChangeListener = AMap.OnMyLocationChangeListener {
            logE("${it.latitude},${it.longitude}")
            val center = LatLng(22.548803, 113.936189)
            addMarkerTest(center)
            addCircleTest(center)
        }

    }

    private fun getViewRect(view: View): Rect {
        val loc = IntArray(2)
        view.getLocationOnScreen(loc)
        return Rect(loc[0], loc[1], loc[0] + view.width, loc[1] + view.height)
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun logE(msg: String) {
        Log.e("MAP_LOG", msg)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val rect = getViewRect(testView)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("TOUCH_LOG", "ACTION_DOWN, -- ${rect.contains(event.rawX.toInt(), event.rawY.toInt())}")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("TOUCH_LOG", "ACTION_MOVE, -- ${rect.contains(event.rawX.toInt(), event.rawY.toInt())}")
            }
            MotionEvent.ACTION_UP -> {
                Log.e("TOUCH_LOG", "ACTION_UP, -- ${rect.contains(event.rawX.toInt(), event.rawY.toInt())}")
            }
            MotionEvent.ACTION_CANCEL -> Log.e("TOUCH_LOG", "ACTION_CANCEL")
            else -> Log.e("TOUCH_LOG", "---")
        }
        return super.onTouchEvent(event)
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
                    10.0,
                    ContextCompat.getColor(this, R.color.colorAccent),
                    ContextCompat.getColor(this, R.color.colorPrimary), 2f
            )
        }
    }

}
