package com.daeseong.businfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daeseong.businfo.BusData.getArrInfoByRouteAllData;

import java.util.ArrayList;

public class Tab2Adapter extends RecyclerView.Adapter<Tab2Adapter.Tab2ViewHolder> {

    private ArrayList<getArrInfoByRouteAllData> getArrInfoByRouteAllDataList;

    public Tab2Adapter(ArrayList<getArrInfoByRouteAllData> getArrInfoByRouteAllDataList){
        this.getArrInfoByRouteAllDataList = getArrInfoByRouteAllDataList;
    }

    @NonNull
    @Override
    public Tab2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item2_cardview, parent, false);
        return new Tab2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tab2ViewHolder holder, int position) {

        holder.tv2.setText(getArrInfoByRouteAllDataList.get(position).getArrmsg1());
        holder.tv4.setText(getArrInfoByRouteAllDataList.get(position).getArrmsg2());
        holder.tv6.setText(getArrInfoByRouteAllDataList.get(position).getArsId());
        holder.tv8.setText(getArrInfoByRouteAllDataList.get(position).getRtNm());
        holder.tv10.setText(getArrInfoByRouteAllDataList.get(position).getStId());
        holder.tv12.setText(getArrInfoByRouteAllDataList.get(position).getStNm());
    }

    @Override
    public int getItemCount() {
        return (getArrInfoByRouteAllDataList != null) ? getArrInfoByRouteAllDataList.size() : 0;
    }

    public class Tab2ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv2;
        private TextView tv4;
        private TextView tv6;
        private TextView tv8;
        private TextView tv10;
        private TextView tv12;

        public Tab2ViewHolder(@NonNull View item){
            super(item);

            tv2 = item.findViewById(R.id.tv2);
            tv4 = item.findViewById(R.id.tv4);
            tv6 = item.findViewById(R.id.tv6);
            tv8 = item.findViewById(R.id.tv8);
            tv10 = item.findViewById(R.id.tv10);
            tv12 = item.findViewById(R.id.tv12);
        }
    }
}

