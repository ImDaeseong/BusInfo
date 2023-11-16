package com.daeseong.businfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData

class Tab2Adapter(private val getArrInfoByRouteAllDataList: ArrayList<getArrInfoByRouteAllData>) :
    RecyclerView.Adapter<Tab2Adapter.Tab2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Tab2ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item2_cardview, parent, false)
        return Tab2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Tab2ViewHolder, position: Int) {
        holder.bind(getArrInfoByRouteAllDataList[position])
    }

    override fun getItemCount(): Int {
        return getArrInfoByRouteAllDataList.size
    }

    inner class Tab2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv2: TextView = itemView.findViewById(R.id.tv2)
        private val tv4: TextView = itemView.findViewById(R.id.tv4)
        private val tv6: TextView = itemView.findViewById(R.id.tv6)
        private val tv8: TextView = itemView.findViewById(R.id.tv8)
        private val tv10: TextView = itemView.findViewById(R.id.tv10)
        private val tv12: TextView = itemView.findViewById(R.id.tv12)

        fun bind(data: getArrInfoByRouteAllData) {
            tv2.text = data.arrmsg1
            tv4.text = data.arrmsg2
            tv6.text = data.arsId
            tv8.text = data.rtNm
            tv10.text = data.stId
            tv12.text = data.stNm
        }
    }
}
