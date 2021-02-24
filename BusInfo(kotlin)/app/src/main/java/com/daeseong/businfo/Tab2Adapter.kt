package com.daeseong.businfo



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daeseong.businfo.BusData.getArrInfoByRouteAllData


class Tab2Adapter(private val getArrInfoByRouteAllDataList: ArrayList<getArrInfoByRouteAllData>?) : RecyclerView.Adapter<Tab2Adapter.Tab2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Tab2ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item2_cardview, parent, false)
        return Tab2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Tab2ViewHolder, position: Int) {

        holder.tv2.text = getArrInfoByRouteAllDataList!![position].arrmsg1
        holder.tv4.text = getArrInfoByRouteAllDataList!![position].arrmsg2
        holder.tv6.text = getArrInfoByRouteAllDataList!![position].arsId
        holder.tv8.text = getArrInfoByRouteAllDataList!![position].rtNm
        holder.tv10.text = getArrInfoByRouteAllDataList!![position].stId
        holder.tv12.text = getArrInfoByRouteAllDataList!![position].stNm
    }

    override fun getItemCount(): Int {
        return getArrInfoByRouteAllDataList?.size ?: 0
    }

    class Tab2ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tv2: TextView = item.findViewById(R.id.tv2)
        val tv4: TextView = item.findViewById(R.id.tv4)
        val tv6: TextView = item.findViewById(R.id.tv6)
        val tv8: TextView = item.findViewById(R.id.tv8)
        val tv10: TextView = item.findViewById(R.id.tv10)
        val tv12: TextView = item.findViewById(R.id.tv12)
    }
}