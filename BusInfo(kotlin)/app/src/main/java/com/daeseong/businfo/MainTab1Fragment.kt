package com.daeseong.businfo


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusAPI.getArrInfoByRouteAllAPI
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData


class MainTab1Fragment : Fragment() {

    companion object {
        private val tag = MainTab1Fragment::class.java.simpleName
    }

    private var mContext: Context? = null
    private var spinner: Spinner? = null
    private var btnSearch: Button? = null
    private var rv1: RecyclerView? = null
    private var tab2Adapter: Tab2Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext = container!!.context

        val viewGroup = inflater!!.inflate(R.layout.fragment_main_tab1, container, false) as ViewGroup
        spinner = viewGroup.findViewById<Spinner>(R.id.spinner)
        btnSearch = viewGroup.findViewById<Button>(R.id.btnSearch)
        rv1 = viewGroup.findViewById<RecyclerView>(R.id.rv1)

        btnSearch!!.setOnClickListener(View.OnClickListener {

            try {

                if (!BusApplication.getInstance().isNetworkAvailable(mContext!!)) {
                    Log.e(tag, "미접속")
                    return@OnClickListener
                }

                var sbusRouteId = ""
                when {
                    spinner!!.selectedItem.toString() == "금천03" -> {
                        sbusRouteId = "117900003"
                    }
                    spinner!!.selectedItem.toString() == "1500고양" -> {
                        sbusRouteId = "218000010"
                    }
                    spinner!!.selectedItem.toString() == "9707" -> {
                        sbusRouteId = "100100400"
                    }
                    spinner!!.selectedItem.toString() == "830파주" -> {
                        sbusRouteId = "229000017"
                    }
                }

                if (TextUtils.isEmpty(sbusRouteId)) {
                    return@OnClickListener
                }

                getArrInfoByRouteAllTask().execute(sbusRouteId)

            } catch (ex: Exception) {
                Log.e(tag, ex.message)
            }
        })
        return viewGroup
    }

    inner class getArrInfoByRouteAllTask : AsyncTask<String?, Void?, ArrayList<getArrInfoByRouteAllData>?>() {

        override fun onPreExecute() {
            super.onPreExecute()

            Log.e(tag, "onPreExecute")
        }

        override fun doInBackground(vararg params: String?): ArrayList<getArrInfoByRouteAllData>? {

            Log.e(tag, "doInBackground")

            var arrayList: ArrayList<getArrInfoByRouteAllData>? = null
            val sbusRouteId = params[0]

            val API = getArrInfoByRouteAllAPI()
            if(API.getData(sbusRouteId!!)){
                arrayList = API.allList
            }
            return arrayList
        }

        override fun onPostExecute(arrayList: ArrayList<getArrInfoByRouteAllData>?) {
            super.onPostExecute(arrayList)

            Log.e(tag, "onPostExecute")

            try {

                tab2Adapter = Tab2Adapter(arrayList)
                rv1!!.layoutManager = LinearLayoutManager(mContext)
                rv1!!.adapter = tab2Adapter
            } catch (ex: java.lang.Exception) {
                Log.e(tag, ex.message)
            }
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)

            Log.e(tag, "onProgressUpdate" + values[0])
        }
    }

}