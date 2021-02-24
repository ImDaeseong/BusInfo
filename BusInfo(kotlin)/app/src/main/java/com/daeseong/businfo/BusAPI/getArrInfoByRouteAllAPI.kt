package com.daeseong.businfo.BusAPI

import android.util.Log
import com.daeseong.businfo.BusApplication
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData
import com.daeseong.businfo.HttpUtil.GetBusDataResult
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import kotlin.collections.ArrayList


class getArrInfoByRouteAllAPI {

    private val tag = getArrInfoByRouteAllAPI::class.java.simpleName

    val allList = ArrayList<getArrInfoByRouteAllData>()

    fun getData(sbusRouteId: String): Boolean {

        try {

            val defaulturl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?"
            val busRouteId = String.format("busRouteId=%s", sbusRouteId)
            val ServiceKey = java.lang.String.format("&ServiceKey=%s", BusApplication.getInstance().API_Key)
            val sUrlParams = String.format("%s%s%s", defaulturl, busRouteId, ServiceKey)
            val sResult = GetBusDataResult(sUrlParams)
            //Log.e(tag, sResult)

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware= true
            val xpp = factory.newPullParser()
            xpp.setInput(sResult.reader())
            var eventType = xpp.eventType

            var bStart =false
            var textValue = ""

            var currentItem = getArrInfoByRouteAllData()

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

                            "arrmsg1" -> currentItem.arrmsg1 = textValue
                            "arrmsg2" -> currentItem.arrmsg2 = textValue
                            "arsId"-> currentItem.arsId= textValue
                            "rtNm" -> currentItem.rtNm= textValue
                            "stId" -> currentItem.stId = textValue
                            "stNm" -> currentItem.stId = textValue
                        }
                    }
                }

                eventType= xpp.next()
            }

        } catch (ex: java.lang.Exception) {
            Log.e(tag, ex.message)
            return false
        }

        return true
    }
}