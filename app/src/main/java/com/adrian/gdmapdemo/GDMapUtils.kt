package com.adrian.gdmapdemo

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorInt
import com.amap.api.maps.*
import com.amap.api.maps.model.*

/**
 * date:2019/7/20 15:33
 * author:RanQing
 * description:高德地图定位实现，仅针对5.0.0以上版本
 */
class GDMapUtils(val mapView: MapView) {

    private lateinit var map: AMap

    var myLocationStyle: MyLocationStyle? = null
        set(value) {
            field = value
            map.myLocationStyle = field
        }
        get() {
            if (field == null) {
                field = MyLocationStyle()
            }
            return field
        }

    /**
     * 设置蓝点展现模式:
     * @param locationType
     * MyLocationStyle.LOCATION_TYPE_SHOW   只定位一次
     * MyLocationStyle.LOCATION_TYPE_LOCATE 定位一次，且将视角移动到地图中心点
     * MyLocationStyle.LOCATION_TYPE_FOLLOW 连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
     * MyLocationStyle.LOCATION_TYPE_MAP_ROTATE 连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
     * 以下三种模式从5.1.0版本开始提供
     * MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER  连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
     * MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER   连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动
     * MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER   连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动
     */
    var myLocationType = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE
        set(value) {
            field = value
            myLocationStyle?.myLocationType(field)
        }

    /** 设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息 */
    var showMyLocation = true
        set(value) {
            field = value
            //方法自5.1.0版本后支持
            myLocationStyle?.showMyLocation(field)
        }

    /** 连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒 */
    var myLocationInterver = 2000L
        set(value) {
            field = value
            myLocationStyle?.interval(field)
        }

    /** 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false */
    var isMyLocationEnable = true
        set(value) {
            field = value
            map.isMyLocationEnabled = field
        }

    /** 自定义蓝点图标 */
    var myLocationIcon: BitmapDescriptor? = null
        set(value) {
            field = value
            myLocationStyle?.myLocationIcon(field)
        }

    /** 精度圈边框宽度 */
    var myLocationStrokeWidth: Float = 1.0f
        set(value) {
            field = value
            myLocationStyle?.strokeWidth(field)
        }

    /** 定位蓝点精度圆圈的边框颜色 */
    @ColorInt var myLocationStrokeColor = Color.BLACK
        set(value) {
            field = value
            myLocationStyle?.strokeColor(field)
        }

    /** 定位蓝点精度圆圈的填充颜色 */
    @ColorInt var myLocationRadiusFillColor = Color.TRANSPARENT
        set(value) {
            field = value
            myLocationStyle?.radiusFillColor(field)
        }

    /** 实现 AMap.OnMyLocationChangeListener 监听器，通过回调方法获取经纬度信息 */
    var onMyLocationChangeListener: AMap.OnMyLocationChangeListener? = null
        set(value) {
            field = value
            map.setOnMyLocationChangeListener(field)
        }

    /**
     * 开启室内地图后，如果可见区域内包含室内地图覆盖区域（如：凯德Mall等知名商场），且缩放达到一定级别，便可直接在地图上看到精细室内地图效果
     * 缩放级别≥17级时，地图上可以显示室内地图
     * 缩放级别≥18级时，不仅可以看到室内地图效果，还允许操作切换楼层，显示精细化室内地图
     * 3D 地图 SDK中默认会关闭室内地图显示
     * true：显示室内地图；false：不显示
     */
    var showIndoorMap = false
        set(value) {
            field = value
            map.showIndoorMap(field)
        }

    /**
     * 切换地图图层.Android 地图 SDK 提供了几种预置的地图图层，包括卫星图、白昼地图（即最常见的黄白色地图）、夜景地图、导航地图、路况图层
     * MAP_TYPE_NAVI    导航地图
     * MAP_TYPE_NIGHT   夜景地图
     * MAP_TYPE_NORMAL  白昼地图（即普通地图）
     * MAP_TYPE_SATELLITE   卫星图
     */
    var mapType = AMap.MAP_TYPE_NORMAL
        set(value) {
            field = value
            map.mapType = field
        }

    /** 显示实时路况图层 */
    var isTrafficEnabled = false
        set(value) {
            field = value
            map.isTrafficEnabled = field
        }

    /**
     * 地图底图语言，目前支持中文底图和英文底图
     * AMap.CHINESE 表示中文，即"zh_cn", AMap.ENGLISH 表示英文，即"en"
     * @since 5.5.0
     */
    var mapLanguage = AMap.CHINESE
        set(value) {
            field = value
            map.setMapLanguage(field)
        }

    /** 自定义地图样式 */
    var customMapStyleOptions: CustomMapStyleOptions? = null
        set(value) {
            field = value
            map.setCustomMapStyle(field)
        }
        get() {
            if (field == null) {
                field = CustomMapStyleOptions()
            }
            return field
        }

    /** 是否开启自定义地图.默认关闭 */
    var customMapEnable: Boolean = false
        set(value) {
            field = value
            customMapStyleOptions?.isEnable = field
        }

    /**
     * 设定离线自定义样式文件
     * 地图样式文件的路径,比如”/sdcard/custom_config/style.data”
     * 注意：自V5.2.0起，既支持style.json文件，也支持.data文件
     */
    var offlineStylePath: String? = null
        set(value) {
            field = value
            customMapStyleOptions?.styleDataPath = field
        }

    /**
     * 设定在线自定义样式文件ID
     * 自定义平台发布之后会有一份样式ID
     */
    var onlineStyleID: String? = null
        set(value) {
            field = value
            customMapStyleOptions?.styleId = field
        }

    /** 是否允许显示缩放按钮 */
    var isZoomCtrlEnable = true
        set(value) {
            field = value
            map.uiSettings.isZoomControlsEnabled = field
        }

    /**
     *  缩放按钮位置
     *  ZOOM_POSITION_RIGHT_CENTER  右中间
     *  ZOOM_POSITION_RIGHT_BUTTOM  右下角
     */
    var zoomCtrlPosition: Int = AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM
        set(value) {
            field = value
            map.uiSettings.zoomPosition = field
        }

    /** 是否显示指南针 */
    var isCompassEnable = false
        set(value) {
            field = value
            map.uiSettings.isCompassEnabled = field
        }

    /** 是否显示定位按钮 */
    var isShowMyLocationBtn = false
        set(value) {
            field = value
            map.uiSettings.isMyLocationButtonEnabled = field
//            map.setLocationSource(object : LocationSource {
//                override fun deactivate() {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun activate(p0: LocationSource.OnLocationChangedListener?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//            })
        }

    /** 比例尺控件（最大比例是1：10m,最小比例是1：1000Km），位于地图右下角，可控制其显示与隐藏 */
    var isScaleCtrlEnable = false
        set(value) {
            field = value
            map.uiSettings.isScaleControlsEnabled = field
        }

    /**
     * 高德地图的 logo 默认在左下角显示，不可以移除，但支持调整到固定位置
     * AMapOptions.LOGO_POSITION_BOTTOM_LEFT    Logo位置（地图左下角），同AMapOptions.LOGO_MARGIN_LEFT
     * AMapOptions.LOGO_POSITION_BOTTOM_CENTER  Logo位置（地图底部居中），同AMapOptions.LOGO_MARGIN_BOTTOM
     * AMapOptions.LOGO_POSITION_BOTTOM_RIGHT   Logo位置（地图右下角），同AMapOptions.LOGO_MARGIN_RIGHT
     */
    var logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_LEFT
        set(value) {
            field = value
            map.uiSettings.logoPosition = field
        }

    /** 获取缩放比例 */
    var scalePerPixel: Float = map.scalePerPixel

    /** marker点击监听 */
    var onMarkerClickListener: AMap.OnMarkerClickListener? = null
        set(value) {
            field = value
            map.setOnMarkerClickListener(field)
        }

    /** marker拖拽监听 */
    var onMarkerDragListener: AMap.OnMarkerDragListener? = null
        set(value) {
            field = value
            map.setOnMarkerDragListener(field)
        }

    fun onCreate(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        map = mapView.map

        setLocationStyle()
    }

    fun onSaveInstanceState(outState: Bundle) {
        mapView.onSaveInstanceState(outState)
    }

    fun onResume() {
        mapView.onResume()
    }

    fun onPause() {
        mapView.onPause()
    }

    fun onDestroy() {
        mapView.onDestroy()
    }

    /**
     * 设置蓝点展现模式:
     * @param locationType
     * MyLocationStyle.LOCATION_TYPE_SHOW   只定位一次
     * MyLocationStyle.LOCATION_TYPE_LOCATE 定位一次，且将视角移动到地图中心点
     * MyLocationStyle.LOCATION_TYPE_FOLLOW 连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
     * MyLocationStyle.LOCATION_TYPE_MAP_ROTATE 连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
     * 以下三种模式从5.1.0版本开始提供
     * MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER  连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
     * MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER   连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动
     * MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER   连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动
     */
    fun setLocationStyle(locationType: Int = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE) {
        myLocationType = locationType
        myLocationInterver = 2000L
        showMyLocation = true
        isMyLocationEnable = true
    }

    /**
     * 自定义定位蓝点图标的锚点
     * 锚点是指定位蓝点图标像素与定位蓝点坐标的关联点，例如需要将图标的左下方像素点与定位蓝点的经纬度关联在一起，方法传入（0.0,1.0）。图标左上点为像素原点
     */
    fun setMyLocationAnchor(u: Float, v: Float) {
        myLocationStyle?.anchor(u, v)
    }

    /**
     * 设定自定义样式文件
     * @param styleFilePath 样式文件路径,比如”/sdcard/custom_config/style.data”
     * @param styleID   样式文件ID
     * 注：如果同时设置了在线样式和离线样式，会优先进行在线拉取，如果拉取失败了会再次读取离线样式
     */
    fun setCustomMapStyle(styleDataPath: String, styleID: String) {
        customMapStyleOptions?.setStyleId(styleID)?.setStyleDataPath(styleDataPath)
    }

    /**
     * 自定义纹理
     * @param styleDataPath 样式文件路径,比如”/sdcard/custom_config/style.data”
     * @param textureResPath    纹理资源路径,比如”/sdcard/custom_config/textures.zip”
     * 注：纹理暂不支持在线拉取
     */
    fun setCustomTexture(styleDataPath: String, textureResPath: String) {
        customMapStyleOptions?.setStyleDataPath(styleDataPath)?.setStyleTexturePath(textureResPath)
    }

    /**
     * 添加标记点
     * @param   position  在地图上标记位置的经纬度值。必填参数
     * @param   icon    点标记的图标
     * @param   title   点标记的标题
     * @param   snippet 点标记的内容
     * @param   anchorU 锚点横坐标
     * @param   anchorV 锚点纵坐标
     * @param   alpha   点的透明度
     * @param   draggable   点标记是否可拖拽
     * @param   visible 点标记是否可见
     */
    @JvmOverloads fun addMarker(position: LatLng, icon: BitmapDescriptor, title: String? = null, snippet: String? = null, anchorU: Float = 0f, anchorV: Float = 0f, alpha: Float = 0f, draggable: Boolean = false, visible: Boolean = true): Marker {
        val marker = MarkerOptions().position(position).icon(icon).title(title).snippet(snippet).alpha(alpha).draggable(draggable).visible(visible).anchor(anchorU, anchorV)
        return map.addMarker(marker)
    }

    /**
     * 添加线
     * @param   latLngs
     * @param   width   线宽
     * @param   color   线颜色
     * @param   customTexture   线段的纹理，建议纹理资源长宽均为2的n次方
     * @param   isDottedLine    是否画虚线，默认为false，画实线。
     * @param   visible 线段的可见性
     */
    @JvmOverloads fun addPolyline(latLngs: List<LatLng>, width: Float = 1f, @ColorInt color: Int = Color.BLACK, customTexture: BitmapDescriptor? = null, isDottedLine: Boolean = false, visible: Boolean = true): Polyline {
        val polyline = PolylineOptions().addAll(latLngs).width(width).color(color).setCustomTexture(customTexture).setDottedLine(isDottedLine).visible(visible)
        return map.addPolyline(polyline)
    }

    /**
     * 添加圆
     * @param   center  圆心坐标
     * @param   radius  圆半径
     * @param   fillColor   圆填充色
     * @param   strokeColor 圆边颜色
     * @param   strokeWidth 圆边宽
     * @param   zIndex  圆在z轴的值
     */
    @JvmOverloads fun addCircle(center: LatLng, radius: Double, @ColorInt fillColor: Int = Color.TRANSPARENT, @ColorInt strokeColor: Int = Color.BLACK, strokeWidth: Float = 1f, zIndex: Float = 0f): Circle {
        val options = CircleOptions().zIndex(zIndex).center(center).radius(radius).fillColor(fillColor).strokeColor(strokeColor).strokeWidth(strokeWidth)
        return map.addCircle(options)
    }
}