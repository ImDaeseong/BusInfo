package com.daeseong.businfo.BusAPI

import android.util.Log
import com.daeseong.businfo.BusApplication
import com.daeseong.businfo.BusData.getBusRouteListData
import com.daeseong.businfo.HttpUtil
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader

class getBusRouteListAPI {

    private val tag = getBusRouteListAPI::class.java.simpleName

    private val allList = ArrayList<getBusRouteListData>()

    fun getData(sbusNum: String): ArrayList<getBusRouteListData> {

        try {
            val defaulturl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?"
            val strSrch = "strSrch=$sbusNum"
            val serviceKey = "&ServiceKey=${BusApplication.getInstance().API_Key}"
            val sUrlParams = "$defaulturl$strSrch$serviceKey"
            val sResult = HttpUtil.getBusDataResult(sUrlParams)

            val inputStream: InputStream = ByteArrayInputStream(sResult.toByteArray())

            val xmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(InputStreamReader(inputStream, "UTF-8"))

            var item: getBusRouteListData? = null

            var nType = xmlPullParser.eventType
            while (nType != XmlPullParser.END_DOCUMENT) {
                when (nType) {
                    XmlPullParser.START_TAG -> {
                        val sTag = xmlPullParser.name.trim()
                        if (sTag == "itemList") {
                            item = getBusRouteListData()
                        }

                        if (sTag == "busRouteId") {
                            if (item != null) item.busRouteId = xmlPullParser.nextText()
                        }

                        if (sTag == "busRouteNm") {
                            if (item != null) item.busRouteNm = xmlPullParser.nextText()
                        }

                        if (sTag == "edStationNm") {
                            if (item != null) item.edStationNm = xmlPullParser.nextText()
                        }

                        if (sTag == "stStationNm") {
                            if (item != null) item.stStationNm = xmlPullParser.nextText()
                        }

                        if (sTag == "term") {
                            if (item != null) item.term = xmlPullParser.nextText()
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        val eTag = xmlPullParser.name.trim()
                        if (eTag == "itemList" && item != null) {
                            allList.add(item)
                        }
                    }
                }
                nType = xmlPullParser.next()
            }

        } catch (ex: Exception) {
            Log.i(tag, "ERR: ${ex.message}")
        }
        return allList
    }
}
