package com.wcd.farm.data

import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class LatLngConverter {
    companion object {
        val TO_GRID: Int = 0
        val TO_GPS: Int = 1

        fun convertGRID_GPS(mode: Int, lat_X: Double, lng_Y: Double): LatXLngY {
            val RE = 6371.00877 // 지구 반경(km)
            val GRID = 5.0 // 격자 간격(km)
            val SLAT1 = 30.0 // 투영 위도1(degree)
            val SLAT2 = 60.0 // 투영 위도2(degree)
            val OLON = 126.0 // 기준점 경도(degree)
            val OLAT = 38.0 // 기준점 위도(degree)
            val XO = 43.0 // 기준점 X좌표(GRID)
            val YO = 136.0 // 기1준점 Y좌표(GRID)


            //
            // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
            //
            val DEGRAD = Math.PI / 180.0
            val RADDEG = 180.0 / Math.PI

            val re = RE / GRID
            val slat1 = SLAT1 * DEGRAD
            val slat2 = SLAT2 * DEGRAD
            val olon = OLON * DEGRAD
            val olat = OLAT * DEGRAD

            var sn = tan(Math.PI * 0.25 + slat2 * 0.5) / tan(Math.PI * 0.25 + slat1 * 0.5)
            sn = ln(cos(slat1) / cos(slat2)) / ln(sn)
            var sf = tan(Math.PI * 0.25 + slat1 * 0.5)
            sf = sf.pow(sn) * cos(slat1) / sn
            var ro = tan(Math.PI * 0.25 + olat * 0.5)
            ro = re * sf / ro.pow(sn)
            val rs: LatXLngY = LatXLngY()

            if (mode == TO_GRID) {
                rs.lat = lat_X
                rs.lng = lng_Y
                var ra = tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5)
                ra = re * sf / ra.pow(sn)
                var theta = lng_Y * DEGRAD - olon
                if (theta > Math.PI) theta -= 2.0 * Math.PI
                if (theta < -Math.PI) theta += 2.0 * Math.PI
                theta *= sn
                rs.x = floor(ra * sin(theta) + XO + 0.5)
                rs.y = floor(ro - ra * cos(theta) + YO + 0.5)
            } else {
                rs.x = lat_X
                rs.y = lng_Y
                val xn = lat_X - XO
                val yn = ro - lng_Y + YO
                var ra = sqrt(xn * xn + yn * yn)
                if (sn < 0.0) {
                    ra = -ra
                }
                var alat: Double = (re * sf / ra).pow((1.0 / sn))
                alat = 2.0 * atan(alat) - Math.PI * 0.5

                var theta = 0.0
                if (abs(xn) <= 0.0) {
                    theta = 0.0
                } else {
                    if (abs(yn) <= 0.0) {
                        theta = Math.PI * 0.5
                        if (xn < 0.0) {
                            theta = -theta
                        }
                    } else theta = atan2(xn, yn)
                }
                val alon = theta / sn + olon
                rs.lat = alat * RADDEG
                rs.lng = alon * RADDEG
            }
            return rs
        }
    }
}

class LatXLngY {
    var lat: Double = 0.0
    var lng: Double = 0.0

    var x: Double = 0.0
    var y: Double = 0.0
}