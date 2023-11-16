package com.daeseong.businfo


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusData.getBusRouteListData


class Tab4Adapter(private val getBusRouteListDataList: ArrayList<getBusRouteListData>?) : RecyclerView.Adapter<Tab4Adapter.Tab4ViewHolder>() {

    private val tag = Tab4Adapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Tab4ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item4_cardview, parent, false)
        return Tab4ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Tab4ViewHolder, position: Int) {

        holder.tv2!!.text = getBusRouteListDataList!![position].busRouteId
        holder.tv4!!.text = getBusRouteListDataList[position].busRouteNm
        holder.tv6!!.text = getBusRouteListDataList[position].edStationNm
        holder.tv8!!.text = getBusRouteListDataList[position].stStationNm
        holder.tv10!!.text = getBusRouteListDataList[position].term
        holder.save1!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    BusApplication.getInstance().SetClipboardText(holder.tv2!!.text.toString())

                    BusApplication.getInstance().Toast( "즐겨찾기 항목이 추가 되었습니다.", false)
                } catch (ex: java.lang.Exception) {
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return getBusRouteListDataList?.size ?: 0
    }

    inner class Tab4ViewHolder(item: View) : RecyclerView.ViewHolder(item), View.OnClickListener {

        var save1: ImageView? = null
        var tv2: TextView? = null
        var tv4: TextView? = null
        var tv6: TextView? = null
        var tv8: TextView? = null
        var tv10: TextView? = null

        init {

            try {
                item.setOnClickListener(this)
                save1 = item.findViewById(R.id.save1)
                tv2 = item.findViewById(R.id.tv2)
                tv4 = item.findViewById(R.id.tv4)
                tv6 = item.findViewById(R.id.tv6)
                tv8 = item.findViewById(R.id.tv8)
                tv10 = item.findViewById(R.id.tv10)
            } catch (ex: Exception) {
            }
        }

        override fun onClick(v: View?) {
        }
    }

}