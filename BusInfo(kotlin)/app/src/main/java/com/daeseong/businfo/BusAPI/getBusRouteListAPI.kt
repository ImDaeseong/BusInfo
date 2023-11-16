package com.daeseong.businfo.BusAPI

import android.util.Log
import com.daeseong.businfo.BusApplication
import com.daeseong.businfo.BusData.getBusRouteListData
import com.daeseong.businfo.HttpUtil.GetBusDataResult
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory


class getBusRouteListAPI {

    private val tag = getBusRouteListAPI::class.java.simpleName

    val allList = ArrayList<getBusRouteListData>()

    fun getData(sbusNum: String): Boolean {

        try {
            val defaulturl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?"
            val strSrch = String.format("strSrch=%s", sbusNum)
            val ServiceKey = java.lang.String.format("&ServiceKey=%s", BusApplication.getInstance().API_Key)
            val sUrlParams = String.format("%s%s%s", defaulturl, strSrch, ServiceKey)
            val sResult = GetBusDataResult(sUrlParams)

            //Log.e(tag, sResult)

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware= true
            val xpp = factory.newPullParser()
            xpp.setInput(sResult.reader())
            var eventType = xpp.eventType

            var bStart =false
            var textValue = ""

            var currentItem = getBusRouteListData()

            while(eventType!= XmlPullParser.END_DOCUMENT ){

                val tagName= xpp.name
                //Log.e(tag, "tagName:$tagName")

                when(eventType){

                    XmlPullParser.START_TAG -> {

                        if( tagName== "itemList" ){
                            bStart = true
                        }
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG ->{

                        when(tagName){

                            "itemList" -> {

                                if(bStart){

                                    allList.add(currentItem)
                                    bStart = false
                                }
                            }

                            "busRouteId" -> currentItem.busRouteId = textValue
                            "busRouteNm" -> currentItem.busRouteNm = textValue
                            "edStationNm"-> currentItem.edStationNm= textValue
                            "stStationNm" -> currentItem.stStationNm= textValue
                            "term" -> currentItem.term = textValue
                        }
                    }
                }

                eventType= xpp.next()
            }

        } catch (ex: Exception) {
            return false
        }

        return true
    }

}