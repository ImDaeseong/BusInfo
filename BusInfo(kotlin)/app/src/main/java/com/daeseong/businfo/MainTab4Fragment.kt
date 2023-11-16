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
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusAPI.getBusRouteListAPI
import com.daeseong.businfo.BusData.getBusRouteListData


class MainTab4Fragment : Fragment() {

    companion object {
        private val tag = MainTab4Fragment::class.java.simpleName
    }

    private var mContext: Context? = null
    private var edtBusNum: EditText? = null
    private var btnSearch: Button? = null
    private var rv1: RecyclerView? = null
    private var tab4Adapter: Tab4Adapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mContext = container!!.context

        val viewGroup = inflater!!.inflate(R.layout.fragment_main_tab4, container, false) as ViewGroup
        edtBusNum = viewGroup.findViewById<EditText>(R.id.edtBusNum)
        btnSearch = viewGroup.findViewById<Button>(R.id.btnSearch)
        rv1 = viewGroup.findViewById<RecyclerView>(R.id.rv1)

        btnSearch!!.setOnClickListener(View.OnClickListener {

            try {

                if (!BusApplication.getInstance().isNetworkAvailable(mContext!!)) {
                    Log.e(tag, "미접속")
                    return@OnClickListener
                }

                val sBusNum = edtBusNum!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(sBusNum)) {
                    return@OnClickListener
                }

                getBusRouteListTask().execute(sBusNum)

            } catch (ex: Exception) {
            }
        })

        return viewGroup
    }

    inner class getBusRouteListTask : AsyncTask<String?, Void?, ArrayList<getBusRouteListData>?>() {

        override fun onPreExecute() {
            super.onPreExecute()

            Log.e(tag, "onPreExecute")
        }

        override fun doInBackground(vararg params: String?): ArrayList<getBusRouteListData>? {

            Log.e(tag, "doInBackground")

            var arrayList: ArrayList<getBusRouteListData>? = null
            val sbusRouteId = params[0]

            val API = getBusRouteListAPI()
            if(API.getData(sbusRouteId!!)){
                arrayList = API.allList
            }
            return arrayList
        }

        override fun onPostExecute(arrayList: ArrayList<getBusRouteListData>?) {
            super.onPostExecute(arrayList)

            Log.e(tag, "onPostExecute")

            try {

                edtBusNum!!.setText("")
                (activity as MainActivity?)!!.hideKeyboard()

                tab4Adapter = Tab4Adapter(arrayList)
                rv1!!.layoutManager = LinearLayoutManager(mContext)
                rv1!!.adapter = tab4Adapter

            } catch (ex: java.lang.Exception) {
            }
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)

            Log.e(tag, "onProgressUpdate" + values[0])
        }
    }

}