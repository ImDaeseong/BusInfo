package com.daeseong.businfo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.os.Looper;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        View viewGroup =  inflater.inflate(R.layout.fragment_main_tab4, container, false);

        edtBusNum = viewGroup.findViewById(R.id.edtBusNum);
        btnSearch = viewGroup.findViewById(R.id.btnSearch);
        rv1 = viewGroup.findViewById(R.id.rv1);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchButtonClick();
            }
        });

        return viewGroup;
    }

    private void handleSearchButtonClick() {
        try {
            if (!BusApplication.getInstance().isNetworkAvailable(mContext)) {
                return;
            }

            String sBusNum = edtBusNum.getText().toString().trim();
            if (TextUtils.isEmpty(sBusNum)) {
                return;
            }

            //AsyncTask
            //new GetBusRouteListTask().execute(sBusNum);

            //Callable  백그라운드
            executeAsyncTask(sBusNum);

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


    //Callable  백그라운드
    private void executeAsyncTask(String sBusNum) {

        Callable<ArrayList<getBusRouteListData>> callable = new Callable<ArrayList<getBusRouteListData>>() {
            @Override
            public ArrayList<getBusRouteListData> call() throws Exception {
                getBusRouteListAPI getBusRouteListAPI = new getBusRouteListAPI();
                return getBusRouteListAPI.getData(sBusNum);
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<ArrayList<getBusRouteListData>> future = executorService.submit(callable);

        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<getBusRouteListData> result = future.get();
                    updateUI(result);
                } catch (Exception e) {
                } finally {
                    executorService.shutdown();
                }
            }
        });
    }

    private void updateUI(ArrayList<getBusRouteListData> arrayList) {
        try {
            edtBusNum.setText("");
            ((MainActivity) getActivity()).hideKeyboard();

            tab4Adapter = new Tab4Adapter(arrayList, new Tab4Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(String busRouteId) {
                    Log.e(TAG, busRouteId);
                }
            });
            rv1.setLayoutManager(new LinearLayoutManager(mContext));
            rv1.setAdapter(tab4Adapter);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    //AsyncTask
    private class GetBusRouteListTask extends AsyncTask<String, Void, ArrayList<getBusRouteListData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<getBusRouteListData> doInBackground(String... strings) {

            ArrayList<getBusRouteListData> arrayList = null;
            String sbusNum = strings[0];

            try {
                getBusRouteListAPI getBusRouteListAPI = new getBusRouteListAPI();
                arrayList = getBusRouteListAPI.getData(sbusNum);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<getBusRouteListData> arrayList) {
            super.onPostExecute(arrayList);

            try {
                edtBusNum.setText("");
                ((MainActivity) getActivity()).hideKeyboard();

                tab4Adapter = new Tab4Adapter(arrayList, new Tab4Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String busRouteId) {
                        Log.e(TAG, busRouteId);
                    }
                });
                rv1.setLayoutManager(new LinearLayoutManager(mContext));
                rv1.setAdapter(tab4Adapter);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.e(TAG, "onProgressUpdate" + values[0]);
        }
    }

}
