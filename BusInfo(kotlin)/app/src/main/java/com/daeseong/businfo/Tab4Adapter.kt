package com.daeseong.businfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusData.getBusRouteListData
import java.util.ArrayList

class Tab4Adapter(private val getBusRouteListDataList: ArrayList<getBusRouteListData>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<Tab4Adapter.Tab4ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(busRouteId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Tab4ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item4_cardview, parent, false)
        return Tab4ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Tab4ViewHolder, position: Int) {
        holder.bind(getBusRouteListDataList[position])
    }

    override fun getItemCount(): Int {
        return getBusRouteListDataList.size
    }

    inner class Tab4ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val save1: ImageView = itemView.findViewById(R.id.save1)
        private val tv2: TextView = itemView.findViewById(R.id.tv2)
        private val tv4: TextView = itemView.findViewById(R.id.tv4)
        private val tv6: TextView = itemView.findViewById(R.id.tv6)
        private val tv8: TextView = itemView.findViewById(R.id.tv8)
        private val tv10: TextView = itemView.findViewById(R.id.tv10)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(getBusRouteListDataList[position].busRouteId)
                }
            }

            save1.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    try {
                        val busRouteId = tv2.text.toString()
                        BusApplication.getInstance().setClipboardText(busRouteId)
                        BusApplication.getInstance().showToast("즐겨찾기 항목이 추가 되었습니다.", false)
                    } catch (ex: Exception) {
                    }
                }
            })
        }

        fun bind(data: getBusRouteListData) {
            tv2.text = data.busRouteId
            tv4.text = data.busRouteNm
            tv6.text = data.edStationNm
            tv8.text = data.stStationNm
            tv10.text = data.term
        }
    }
}
