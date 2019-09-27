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
import android.widget.Button;
import android.widget.EditText;

import com.daeseong.businfo.BusAPI.getArrInfoByRouteAllAPI;
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData;

import java.util.ArrayList;

public class MainTab2Fragment extends Fragment {

    private static final String TAG = MainTab2Fragment.class.getSimpleName();

    private Context mContext;

    private EditText edtBusNum;
    private Button btnSearch;
    private RecyclerView rv1;
    private Tab2Adapter tab2Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = container.getContext();

        ViewGroup viewGroup =  (ViewGroup)inflater.inflate(R.layout.fragment_main_tab2, container, false);

        edtBusNum = (EditText)viewGroup.findViewById(R.id.edtBusNum);
        btnSearch = (Button)viewGroup.findViewById(R.id.btnSearch);
        rv1 = (RecyclerView)viewGroup.findViewById(R.id.rv1);

        edtBusNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    edtBusNum.setText(BusApplication.getInstance().GetClipboardText().toString().trim());
                    BusApplication.getInstance().ClearClipboardText();
                }catch (Exception ex){
                    Log.e(TAG, ex.getMessage());
                }

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if(!BusApplication.getInstance().isNetworkAvailable(mContext)){
                        Log.e(TAG,  "미접속");
                        return;
                    }

                    String sbusRouteId = edtBusNum.getText().toString().trim();
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

                edtBusNum.setText("");
                ((MainActivity)getActivity()).hideKeyboard();

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
