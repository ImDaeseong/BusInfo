package com.daeseong.businfo

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusAPI.getArrInfoByRouteAllAPI
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainTab3Fragment : Fragment() {

    private val TAG = MainTab3Fragment::class.java.simpleName

    private lateinit var mContext: Context
    private lateinit var spinner: Spinner
    private lateinit var btnSearch: Button
    private lateinit var rv1: RecyclerView
    private lateinit var tab2Adapter: Tab2Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mContext = container!!.context
        val viewGroup = inflater.inflate(R.layout.fragment_main_tab3, container, false)

        spinner = viewGroup.findViewById(R.id.spinner)
        btnSearch = viewGroup.findViewById(R.id.btnSearch)
        rv1 = viewGroup.findViewById(R.id.rv1)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.e(TAG, parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        btnSearch.setOnClickListener {
            handleSearchButtonClick()
        }

        return viewGroup
    }

    private fun handleSearchButtonClick() {
        try {
            if (!BusApplication.getInstance().isNetworkAvailable(mContext)) {
                return
            }

            val sbusRouteId = getSelectedRouteId()
            if (TextUtils.isEmpty(sbusRouteId)) {
                return
            }

            // Callable 백그라운드
            executeAsyncTask(sbusRouteId)

        } catch (ex: Exception) {
        }
    }

    private fun getSelectedRouteId(): String {
        return when (spinner.selectedItem.toString()) {
            "금천03" -> "117900003"
            "1500고양" -> "218000010"
            "9707" -> "100100400"
            "830파주" -> "229000017"
            else -> ""
        }
    }

    // Callable 백그라운드
    private fun executeAsyncTask(sbusRouteId: String) {
        try {
            val callable = Callable<ArrayList<getArrInfoByRouteAllData>> {
                val getArrInfoByRouteAllAPI = getArrInfoByRouteAllAPI()
                getArrInfoByRouteAllAPI.getData(sbusRouteId)
            }

            val executorService = Executors.newSingleThreadExecutor()
            val future: Future<ArrayList<getArrInfoByRouteAllData>> = executorService.submit(callable)

            try {
                val result: ArrayList<getArrInfoByRouteAllData> = future.get()
                updateUI(result)
            } catch (e: Exception) {
            } finally {
                executorService.shutdown()
            }
        } catch (ex: Exception) {
        }
    }

    private fun updateUI(arrayList: ArrayList<getArrInfoByRouteAllData>) {
        try {
            tab2Adapter = Tab2Adapter(arrayList)
            rv1.layoutManager = LinearLayoutManager(mContext)
            rv1.adapter = tab2Adapter
        } catch (ex: Exception) {
        }
    }

  }
