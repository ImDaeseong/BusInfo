package com.daeseong.businfo.BusAPI;

import android.util.Log;

import com.daeseong.businfo.BusApplication;
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData;
import com.daeseong.businfo.HttpUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class getArrInfomainAPI {

    private static final String TAG = getArrInfomainAPI.class.getSimpleName();

    private ArrayList<getArrInfoByRouteAllData> allList = new ArrayList<getArrInfoByRouteAllData>();

    public ArrayList<getArrInfoByRouteAllData> getData(String sbusRouteId){

        try {
            String defaulturl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?";
            String busRouteId = String.format("busRouteId=%s", sbusRouteId);
            String ServiceKey = String.format("&ServiceKey=%s", BusApplication.getInstance().API_Key);
            String sUrlParams = String.format("%s%s%s", defaulturl, busRouteId, ServiceKey);
            String sResult = HttpUtil.GetBusDataResult(sUrlParams);

            InputStream inputStream = new ByteArrayInputStream(sResult.getBytes());

            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            getArrInfoByRouteAllData item = null;

            int nType = xmlPullParser.getEventType();
            while (nType != xmlPullParser.END_DOCUMENT){
                switch (nType){
                    case XmlPullParser.START_TAG:

                        String sTag = xmlPullParser.getName().trim();
                        if(sTag.equals("itemList")) {
                            item = new getArrInfoByRouteAllData();
                        }

                        if(sTag.equals("arrmsg1")) {
                            if(item != null) {item.setArrmsg1(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("arrmsg2")) {
                            if(item != null) {item.setArrmsg2(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("arsId")) {
                            if(item != null) {item.setArsId(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("rtNm")) {
                            if(item != null) {item.setRtNm(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("stId")) {
                            if(item != null) {item.setStId(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("stNm")) {
                            if(item != null) {item.setStNm(xmlPullParser.nextText());}
                        }

                        break;

                    case XmlPullParser.END_TAG:

                        String eTag = xmlPullParser.getName().trim();
                        if(eTag.equals("itemList")) {

                            if( item.getStNm().toString().contains("가산디지털단지역") || item.getStNm().toString().contains("두산어린이공원") ) {
                                allList.add(item);
                            } else if( item.getStNm().toString().contains("영등포소방서") || item.getStNm().toString().contains("영등포시장") || item.getStNm().toString().contains("일산동구청") || item.getStNm().toString().contains("마두역")) {
                                allList.add(item);
                            }
                        }

                        break;
                }
                nType = xmlPullParser.next();
            }

        } catch (Exception ex) {
            Log.i(TAG, "ERR: " + ex.getMessage());
        }
        return allList;
    }
}
