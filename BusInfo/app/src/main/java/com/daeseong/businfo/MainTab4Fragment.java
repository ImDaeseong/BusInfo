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

import com.daeseong.businfo.BusAPI.getBusRouteListAPI;
import com.daeseong.businfo.BusData.getBusRouteListData;

import java.util.ArrayList;

public class MainTab4Fragment extends Fragment {

    private static final String TAG = MainTab4Fragment.class.getSimpleName();

    private Context mContext;

    private EditText edtBusNum;
    private Button btnSearch;
    private RecyclerView rv1;
    private Tab4Adapter tab4Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = container.getContext();

        ViewGroup viewGroup =  (ViewGroup)inflater.inflate(R.layout.fragment_main_tab4, container, false);

        edtBusNum = (EditText)viewGroup.findViewById(R.id.edtBusNum);
        btnSearch = (Button)viewGroup.findViewById(R.id.btnSearch);
        rv1 = (RecyclerView)viewGroup.findViewById(R.id.rv1);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (!BusApplication.getInstance().isNetworkAvailable(mContext)) {
                        Log.e(TAG, "미접속");
                        return;
                    }

                    String sBusNum = edtBusNum.getText().toString().trim();
                    if (TextUtils.isEmpty(sBusNum)) {
                        return;
                    }
                    new getBusRouteListTask().execute(sBusNum);

                }catch (Exception ex){
                    Log.e(TAG, ex.getMessage());
                }

            }
        });
        return viewGroup;
    }

    private class getBusRouteListTask extends AsyncTask<String, Void, ArrayList<getBusRouteListData>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e(TAG, "onPreExecute");
        }

        @Override
        protected ArrayList<getBusRouteListData> doInBackground(String... strings) {

            Log.e(TAG, "doInBackground");

            ArrayList<getBusRouteListData> arrayList = null;
            String sbusNum = strings[0];

            try {

                getBusRouteListAPI getBusRouteListAPI = new getBusRouteListAPI();
                arrayList = getBusRouteListAPI.getData(sbusNum);

            }catch (Exception ex){
                Log.e(TAG, ex.getMessage());
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<getBusRouteListData> arrayList) {
            super.onPostExecute(arrayList);

            Log.e(TAG, "onPostExecute");

            try {

                edtBusNum.setText("");
                ((MainActivity)getActivity()).hideKeyboard();

                tab4Adapter = new Tab4Adapter(arrayList);
                rv1.setLayoutManager(new LinearLayoutManager(mContext));
                rv1.setAdapter(tab4Adapter);

            }catch(Exception e){
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            Log.e(TAG, "onProgressUpdate" + values[0]);
        }
    }

}
