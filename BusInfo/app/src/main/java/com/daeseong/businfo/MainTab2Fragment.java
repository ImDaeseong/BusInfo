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
import com.daeseong.businfo.BusAPI.getArrInfomainAPI;
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        View viewGroup = inflater.inflate(R.layout.fragment_main_tab2, container, false);

        edtBusNum = viewGroup.findViewById(R.id.edtBusNum);
        btnSearch = viewGroup.findViewById(R.id.btnSearch);
        rv1 = viewGroup.findViewById(R.id.rv1);

        edtBusNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    edtBusNum.setText(BusApplication.getInstance().getClipboardText().toString().trim());
                    BusApplication.getInstance().clearClipboardText();
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }
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

            String sbusRouteId = edtBusNum.getText().toString().trim();
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

            edtBusNum.setText("");
            ((MainActivity) getActivity()).hideKeyboard();

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
                edtBusNum.setText("");
                ((MainActivity) getActivity()).hideKeyboard();

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
        }
    }

}
