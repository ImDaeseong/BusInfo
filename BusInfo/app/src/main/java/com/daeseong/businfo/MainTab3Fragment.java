package com.daeseong.businfo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.daeseong.businfo.BusAPI.getArrInfoByRouteAllAPI;
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData;

import java.util.ArrayList;

public class MainTab3Fragment extends Fragment {

    private static final String TAG = MainTab3Fragment.class.getSimpleName();

    private Context mContext;

    private Spinner spinner;
    private Button btnSearch;
    private RecyclerView rv1;
    private Tab2Adapter tab2Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = container.getContext();

        ViewGroup viewGroup =  (ViewGroup)inflater.inflate(R.layout.fragment_main_tab3, container, false);

        spinner = (Spinner)viewGroup.findViewById(R.id.spinner);
        btnSearch = (Button)viewGroup.findViewById(R.id.btnSearch);
        rv1 = (RecyclerView)viewGroup.findViewById(R.id.rv1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG, parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!BusApplication.getInstance().isNetworkAvailable(mContext)) {
                        Log.e(TAG, "미접속");
                        return;
                    }

                    String sbusRouteId = "";
                    if (spinner.getSelectedItem().toString().equals("금천03")) {
                        sbusRouteId = "117900003";
                    } else if (spinner.getSelectedItem().toString().equals("1500고양")) {
                        sbusRouteId = "218000010";
                    } else if (spinner.getSelectedItem().toString().equals("9707")) {
                        sbusRouteId = "100100400";
                    } else if (spinner.getSelectedItem().toString().equals("830파주")) {
                        sbusRouteId = "229000017";
                    }

                    if (TextUtils.isEmpty(sbusRouteId)) {
                        return;
                    }
                    new getArrInfoByRouteAllTask().execute(sbusRouteId);

                }catch (Exception ex){
                    Log.e(TAG, ex.getMessage());
                }

            }
        });
        return viewGroup;
    }

    private class getArrInfoByRouteAllTask extends AsyncTask<String, Void, ArrayList<getArrInfoByRouteAllData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "onPreExecute");
        }

        @Override
        protected ArrayList<getArrInfoByRouteAllData> doInBackground(String... strings) {

            Log.e(TAG, "doInBackground");

            ArrayList<getArrInfoByRouteAllData> arrayList = null;
            String sbusRouteId = strings[0];

            try {
                getArrInfoByRouteAllAPI getArrInfoByRouteAllAPI = new getArrInfoByRouteAllAPI();
                arrayList = getArrInfoByRouteAllAPI.getData(sbusRouteId);
            }catch (Exception ex){
                Log.e(TAG, ex.getMessage());
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<getArrInfoByRouteAllData> arrayList) {
            super.onPostExecute(arrayList);

            Log.e(TAG, "onPostExecute");

            try {

                tab2Adapter = new Tab2Adapter(arrayList);
                rv1.setLayoutManager(new LinearLayoutManager(mContext));
                rv1.setAdapter(tab2Adapter);

            }catch(Exception ex){
                Log.e(TAG, ex.getMessage());
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            Log.e(TAG, "onProgressUpdate" + values[0]);
        }
    }

}
