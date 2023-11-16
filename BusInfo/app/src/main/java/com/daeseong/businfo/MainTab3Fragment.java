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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        View viewGroup = inflater.inflate(R.layout.fragment_main_tab3, container, false);

        spinner = viewGroup.findViewById(R.id.spinner);
        btnSearch = viewGroup.findViewById(R.id.btnSearch);
        rv1 = viewGroup.findViewById(R.id.rv1);

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

            String sbusRouteId = getSelectedRouteId();
            if (TextUtils.isEmpty(sbusRouteId)) {
                return;
            }

            //AsyncTask
            //new GetArrInfoByRouteAllTask().execute(sbusRouteId);

            //Callable  백그라운드
            executeAsyncTask(sbusRouteId);

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private String getSelectedRouteId() {
        String selectedRouteName = spinner.getSelectedItem().toString();
        switch (selectedRouteName) {
            case "금천03":
                return "117900003";
            case "1500고양":
                return "218000010";
            case "9707":
                return "100100400";
            case "830파주":
                return "229000017";
            default:
                return "";
        }
    }

    //Callable  백그라운드
    private void executeAsyncTask(String sbusRouteId) {
        try {
            Callable<ArrayList<getArrInfoByRouteAllData>> callable = new Callable<ArrayList<getArrInfoByRouteAllData>>() {
                @Override
                public ArrayList<getArrInfoByRouteAllData> call() throws Exception {
                    getArrInfoByRouteAllAPI getArrInfoByRouteAllAPI = new getArrInfoByRouteAllAPI();
                    return getArrInfoByRouteAllAPI.getData(sbusRouteId);
                }
            };

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<ArrayList<getArrInfoByRouteAllData>> future = executorService.submit(callable);

            try {
                ArrayList<getArrInfoByRouteAllData> result = future.get();
                updateUI(result);
            } catch (Exception e) {
            } finally {
                executorService.shutdown();
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private void updateUI(ArrayList<getArrInfoByRouteAllData> arrayList) {
        try {
            tab2Adapter = new Tab2Adapter(arrayList);
            rv1.setLayoutManager(new LinearLayoutManager(mContext));
            rv1.setAdapter(tab2Adapter);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    //AsyncTask
    private class GetArrInfoByRouteAllTask extends AsyncTask<String, Void, ArrayList<getArrInfoByRouteAllData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<getArrInfoByRouteAllData> doInBackground(String... strings) {

            ArrayList<getArrInfoByRouteAllData> arrayList = null;
            String sbusRouteId = strings[0];

            try {
                getArrInfoByRouteAllAPI getArrInfoByRouteAllAPI = new getArrInfoByRouteAllAPI();
                arrayList = getArrInfoByRouteAllAPI.getData(sbusRouteId);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<getArrInfoByRouteAllData> arrayList) {
            super.onPostExecute(arrayList);

            try {
                tab2Adapter = new Tab2Adapter(arrayList);
                rv1.setLayoutManager(new LinearLayoutManager(mContext));
                rv1.setAdapter(tab2Adapter);

            } catch (Exception ex) {
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
