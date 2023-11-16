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
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String busRouteId);
    }

    public Tab4Adapter(ArrayList<getBusRouteListData> getBusRouteListDataList, OnItemClickListener onItemClickListener) {
        this.getBusRouteListDataList = getBusRouteListDataList;
        this.onItemClickListener = onItemClickListener;
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
        holder.bind(getBusRouteListDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (getBusRouteListDataList != null) ? getBusRouteListDataList.size() : 0;
    }

    public class Tab4ViewHolder extends RecyclerView.ViewHolder {

        private ImageView save1;
        private TextView tv2;
        private TextView tv4;
        private TextView tv6;
        private TextView tv8;
        private TextView tv10;

        public Tab4ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(getBusRouteListDataList.get(position).getBusRouteId());
                    }
                }
            });

            save1 = itemView.findViewById(R.id.save1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv4 = itemView.findViewById(R.id.tv4);
            tv6 = itemView.findViewById(R.id.tv6);
            tv8 = itemView.findViewById(R.id.tv8);
            tv10 = itemView.findViewById(R.id.tv10);

            save1.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    try {

                        String busRouteId = tv2.getText().toString();
                        BusApplication.getInstance().setClipboardText(busRouteId);

                        BusApplication.getInstance().showToast("즐겨찾기 항목이 추가 되었습니다.", false);

                    } catch (Exception ex) {
                        Log.e(TAG, ex.getMessage());
                    }
                }
            });
        }

        public void bind(getBusRouteListData data) {
            tv2.setText(data.getBusRouteId());
            tv4.setText(data.getBusRouteNm());
            tv6.setText(data.getEdStationNm());
            tv8.setText(data.getStStationNm());
            tv10.setText(data.getTerm());
        }
    }
}
