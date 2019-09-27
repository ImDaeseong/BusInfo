package com.daeseong.businfo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daeseong.businfo.BusData.getBusRouteListData;

import java.util.ArrayList;

public class Tab4Adapter extends RecyclerView.Adapter<Tab4Adapter.Tab4ViewHolder> {

    private static final String TAG = Tab4Adapter.class.getSimpleName();


    private ArrayList<getBusRouteListData> getBusRouteListDataList;

    public Tab4Adapter(ArrayList<getBusRouteListData> getBusRouteListDataList){
        this.getBusRouteListDataList = getBusRouteListDataList;
    }

    @NonNull
    @Override
    public Tab4ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item4_cardview, parent, false);
        return new Tab4ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tab4ViewHolder holder, final int position) {

        holder.tv2.setText(getBusRouteListDataList.get(position).getBusRouteId());
        holder.tv4.setText(getBusRouteListDataList.get(position).getBusRouteNm());
        holder.tv6.setText(getBusRouteListDataList.get(position).getEdStationNm());
        holder.tv8.setText(getBusRouteListDataList.get(position).getStStationNm());
        holder.tv10.setText(getBusRouteListDataList.get(position).getTerm());

        holder.save1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try{

                    //BusApplication.getInstance().Toast( "즐겨찾기 항목이 추가 되었습니다.", false);

                }catch (Exception ex){
                    Log.e(TAG, ex.getMessage());
                }

            }
        });

        /*
        holder.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    BusApplication.getInstance().SetClipboardText(getBusRouteListDataList.get(position).getBusRouteId());

                }catch (Exception ex){
                }

            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return (getBusRouteListDataList != null) ? getBusRouteListDataList.size() : 0;
    }

    public class Tab4ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private ImageView save1;
        private TextView tv2;
        private TextView tv4;
        private TextView tv6;
        private TextView tv8;
        private TextView tv10;

        public Tab4ViewHolder(@NonNull View item){
            super(item);

            try {

                item.setOnClickListener(this);

                save1 = item.findViewById(R.id.save1);
                tv2 = item.findViewById(R.id.tv2);
                tv4 = item.findViewById(R.id.tv4);
                tv6 = item.findViewById(R.id.tv6);
                tv8 = item.findViewById(R.id.tv8);
                tv10 = item.findViewById(R.id.tv10);

            }catch (Exception ex){
                Log.e(TAG, ex.getMessage());
            }

        }

        @Override
        public void onClick(View v) {

            try {
                BusApplication.getInstance().SetClipboardText(tv2.getText().toString());
            }catch (Exception ex){
                Log.e(TAG, ex.getMessage());
            }

        }
    }
}
