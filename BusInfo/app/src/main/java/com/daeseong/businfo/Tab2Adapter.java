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

    public Tab2Adapter(ArrayList<getArrInfoByRouteAllData> getArrInfoByRouteAllDataList) {
        this.getArrInfoByRouteAllDataList = getArrInfoByRouteAllDataList;
    }

    @NonNull
    @Override
    public Tab2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2_cardview, parent, false);
        return new Tab2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tab2ViewHolder holder, int position) {
        holder.bind(getArrInfoByRouteAllDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (getArrInfoByRouteAllDataList != null) ? getArrInfoByRouteAllDataList.size() : 0;
    }

    public class Tab2ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv2;
        private TextView tv4;
        private TextView tv6;
        private TextView tv8;
        private TextView tv10;
        private TextView tv12;

        public Tab2ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv2 = itemView.findViewById(R.id.tv2);
            tv4 = itemView.findViewById(R.id.tv4);
            tv6 = itemView.findViewById(R.id.tv6);
            tv8 = itemView.findViewById(R.id.tv8);
            tv10 = itemView.findViewById(R.id.tv10);
            tv12 = itemView.findViewById(R.id.tv12);
        }

        public void bind(getArrInfoByRouteAllData data) {
            tv2.setText(data.getArrmsg1());
            tv4.setText(data.getArrmsg2());
            tv6.setText(data.getArsId());
            tv8.setText(data.getRtNm());
            tv10.setText(data.getStId());
            tv12.setText(data.getStNm());
        }
    }
}
