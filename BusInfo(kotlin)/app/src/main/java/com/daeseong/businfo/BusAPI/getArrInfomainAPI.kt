package com.daeseong.businfo.BusAPI

import android.util.Log
import com.daeseong.businfo.BusApplication
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData
import com.daeseong.businfo.HttpUtil
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader

class getArrInfomainAPI {

    private val tag = getArrInfomainAPI::class.java.simpleName

    private val allList = ArrayList<getArrInfoByRouteAllData>()

    fun getData(sbusRouteId: String): ArrayList<getArrInfoByRouteAllData> {

        try {
            val defaulturl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?"
            val busRouteId = "busRouteId=$sbusRouteId"
            val serviceKey = "&ServiceKey=${BusApplication.getInstance().API_Key}"
            val sUrlParams = "$defaulturl$busRouteId$serviceKey"
            val sResult = HttpUtil.getBusDataResult(sUrlParams)

            val inputStream: InputStream = ByteArrayInputStream(sResult.toByteArray())

            val xmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(InputStreamReader(inputStream, "UTF-8"))

            var item: getArrInfoByRouteAllData? = null

            var nType = xmlPullParser.eventType
            while (nType != XmlPullParser.END_DOCUMENT) {
                when (nType) {
                    XmlPullParser.START_TAG -> {
                        val sTag = xmlPullParser.name.trim()
                        if (sTag == "itemList") {
                            item = getArrInfoByRouteAllData()
                        }

                        if (sTag == "arrmsg1") {
                            if (item != null) item.arrmsg1 = xmlPullParser.nextText()
                        }

                        if (sTag == "arrmsg2") {
                            if (item != null) item.arrmsg2 = xmlPullParser.nextText()
                        }

                        if (sTag == "arsId") {
                            if (item != null) item.arsId = xmlPullParser.nextText()
                        }

                        if (sTag == "rtNm") {
                            if (item != null) item.rtNm = xmlPullParser.nextText()
                        }

                        if (sTag == "stId") {
                            if (item != null) item.stId = xmlPullParser.nextText()
                        }

                        if (sTag == "stNm") {
                            if (item != null) item.stNm = xmlPullParser.nextText()
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        val eTag = xmlPullParser.name.trim()
                        if (eTag == "itemList") {
                            if (item != null && (item.stNm.contains("가산디지털단지역") || item.stNm.contains("두산어린이공원") ||
                                        item.stNm.contains("영등포소방서") || item.stNm.contains("영등포시장") ||
                                        item.stNm.contains("일산동구청") || item.stNm.contains("마두역"))) {
                                allList.add(item)
                            }
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
