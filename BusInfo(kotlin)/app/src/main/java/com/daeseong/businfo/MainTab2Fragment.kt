package com.daeseong.businfo

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusAPI.getArrInfoByRouteAllAPI
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainTab2Fragment : Fragment() {

    private val TAG = MainTab2Fragment::class.java.simpleName

    private lateinit var mContext: Context
    private lateinit var edtBusNum: EditText
    private lateinit var btnSearch: Button
    private lateinit var rv1: RecyclerView
    private lateinit var tab2Adapter: Tab2Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mContext = container!!.context
        val viewGroup = inflater.inflate(R.layout.fragment_main_tab2, container, false)

        edtBusNum = viewGroup.findViewById(R.id.edtBusNum)
        btnSearch = viewGroup.findViewById(R.id.btnSearch)
        rv1 = viewGroup.findViewById(R.id.rv1)

        edtBusNum.setOnClickListener {
            try {
                edtBusNum.setText(BusApplication.getInstance().getClipboardText().toString().trim())
                BusApplication.getInstance().clearClipboardText()
            } catch (ex: Exception) {
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

            val sbusRouteId = edtBusNum.text.toString().trim()
            if (TextUtils.isEmpty(sbusRouteId)) {
                return
            }

            // Callable 백그라운드
            executeAsyncTask(sbusRouteId)

        } catch (ex: Exception) {
        }
    }

    // Callable 백그라운드
    private fun executeAsyncTask(sbusRouteId: String) {
        try {
            val callable = Callable<ArrayList<getArrInfoByRouteAllData>> {
                val getArrInfoByRouteAllAPI = getArrInfoByRouteAllAPI()
                getArrInfoByRouteAllAPI.getData(sbusRouteId)
            }

            val executorService: ExecutorService = Executors.newSingleThreadExecutor()
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
            edtBusNum.setText("")
            (activity as MainActivity).hideKeyboard()

            tab2Adapter = Tab2Adapter(arrayList)
            rv1.layoutManager = LinearLayoutManager(mContext)
            rv1.adapter = tab2Adapter
        } catch (ex: Exception) {
        }
    }

}
