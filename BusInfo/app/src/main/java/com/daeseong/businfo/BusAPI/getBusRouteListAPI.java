package com.daeseong.businfo.BusAPI;

import android.util.Log;

import com.daeseong.businfo.BusApplication;
import com.daeseong.businfo.BusData.getBusRouteListData;
import com.daeseong.businfo.HttpUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class getBusRouteListAPI {

    private static final String TAG = getBusRouteListAPI.class.getSimpleName();

    private ArrayList<getBusRouteListData> allList = new ArrayList<getBusRouteListData>();

    public ArrayList<getBusRouteListData> getData(String sbusNum){

        try {
            String defaulturl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?";
            String strSrch = String.format("strSrch=%s", sbusNum);
            String ServiceKey = String.format("&ServiceKey=%s", BusApplication.getInstance().API_Key);
            String sUrlParams = String.format("%s%s%s", defaulturl, strSrch, ServiceKey);
            String sResult = HttpUtil.GetBusDataResult(sUrlParams);

            InputStream inputStream = new ByteArrayInputStream(sResult.getBytes());

            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            getBusRouteListData item = null;

            int nType = xmlPullParser.getEventType();
            while (nType != xmlPullParser.END_DOCUMENT){
                switch (nType){
                    case XmlPullParser.START_TAG:

                        String sTag = xmlPullParser.getName().trim();
                        if(sTag.equals("itemList")) {
                            item = new getBusRouteListData();
                        }

                        if(sTag.equals("busRouteId")) {
                            if(item != null) {item.setBusRouteId(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("busRouteNm")) {
                            if(item != null) {item.setBusRouteNm(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("edStationNm")) {
                            if(item != null) {item.setEdStationNm(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("stStationNm")) {
                            if(item != null) {item.setStStationNm(xmlPullParser.nextText());}
                        }

                        if(sTag.equals("term")) {
                            if(item != null) {item.setTerm(xmlPullParser.nextText());}
                        }

                        break;

                    case XmlPullParser.END_TAG:

                        String eTag = xmlPullParser.getName().trim();
                        if(eTag.equals("itemList")) {
                            allList.add(item);
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
