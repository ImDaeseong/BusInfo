package com.daeseong.businfo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusAPI.getBusRouteListAPI
import com.daeseong.businfo.BusData.getBusRouteListData
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainTab4Fragment : Fragment() {

    private val TAG = MainTab4Fragment::class.java.simpleName

    private lateinit var mContext: Context
    private lateinit var edtBusNum: EditText
    private lateinit var btnSearch: Button
    private lateinit var rv1: RecyclerView
    private lateinit var tab4Adapter: Tab4Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mContext = container!!.context
        val viewGroup = inflater.inflate(R.layout.fragment_main_tab4, container, false)

        edtBusNum = viewGroup.findViewById(R.id.edtBusNum)
        btnSearch = viewGroup.findViewById(R.id.btnSearch)
        rv1 = viewGroup.findViewById(R.id.rv1)

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

            val sBusNum = edtBusNum.text.toString().trim()
            if (TextUtils.isEmpty(sBusNum)) {
                return
            }

            // Callable 백그라운드
            executeAsyncTask(sBusNum)

        } catch (ex: Exception) {
        }
    }

    // Callable 백그라운드
    private fun executeAsyncTask(sBusNum: String) {
        try {
            val callable = Callable<ArrayList<getBusRouteListData>> {
                val getBusRouteListAPI = getBusRouteListAPI()
                getBusRouteListAPI.getData(sBusNum)
            }

            val executorService: ExecutorService = Executors.newSingleThreadExecutor()
            val future: Future<ArrayList<getBusRouteListData>> = executorService.submit(callable)

            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                try {
                    val result: ArrayList<getBusRouteListData> = future.get()
                    updateUI(result)
                } catch (e: Exception) {
                } finally {
                    executorService.shutdown()
                }
            }
        } catch (ex: Exception) {
        }
    }

    private fun updateUI(arrayList: ArrayList<getBusRouteListData>) {
        try {
            edtBusNum.text.clear()
            (activity as MainActivity).hideKeyboard()

            tab4Adapter = Tab4Adapter(arrayList, object : Tab4Adapter.OnItemClickListener {
                override fun onItemClick(busRouteId: String) {
                    Log.e(TAG, busRouteId)
                }
            })
            rv1.layoutManager = LinearLayoutManager(mContext)
            rv1.adapter = tab4Adapter

        } catch (e: Exception) {
        }
    }

}
