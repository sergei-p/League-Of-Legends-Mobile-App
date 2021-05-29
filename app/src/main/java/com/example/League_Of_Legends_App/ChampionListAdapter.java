package com.example.League_Of_Legends_App;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.League_Of_Legends_App.data.championListData;

import java.util.List;

public class ChampionListAdapter extends RecyclerView.Adapter<ChampionListAdapter.MyViewHolder> {
    List<championListData> championListDataList;
    public Activity Context;
    private OnChampionListItemClickListener onChampionListClick;

    interface OnChampionListItemClickListener {
        void onChampionListClick(championListData data);
    }

    public ChampionListAdapter(List <championListData> championListDataList, Activity context) {
        this.championListDataList = championListDataList;
        this.Context = context;
    }

    @NonNull
    @Override
    public ChampionListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.champion_intent_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionListAdapter.MyViewHolder holder, int position) {
        championListData data = championListDataList.get(position);
        Glide.with(Context)
                .load(data.getImg_url())
                .into(holder.champ_button);
        //holder.champ_name.setText(data.getName());
        holder.bind(this.championListDataList.get(position));
    }

    @Override
    public int getItemCount() {
        if(championListDataList != null) {
            return championListDataList.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView champ_name;
        ImageView champ_button;
        LinearLayout parent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            champ_name = itemView.findViewById(R.id.champion_list_nameTV);
            champ_button = itemView.findViewById(R.id.champion_imageIB);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChampionListClick.onChampionListClick(
                            championListDataList.get(getAdapterPosition())
                    );
                }
            });
        }

        public void bind(championListData championListData) {
            this.champ_name.setText(championListData.getName());
        }
    }

    public void setOnChampionListClick(OnChampionListItemClickListener onChampionListClick){
        this.onChampionListClick = onChampionListClick;
    }
}
