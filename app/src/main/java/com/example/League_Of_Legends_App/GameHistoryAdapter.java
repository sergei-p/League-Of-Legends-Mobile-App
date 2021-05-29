package com.example.League_Of_Legends_App;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.League_Of_Legends_App.data.GameHistory;
import com.example.League_Of_Legends_App.data.GameHistoryList;

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.GameHistoryItemViewHolder> {
    private static final String TAG = GameHistoryAdapter.class.getSimpleName();

    private GameHistoryList gameHistoryList;
    private OnGameHistoryItemClickListener onGameHistoryItemClickListener;

    public interface OnGameHistoryItemClickListener {
        void onGameHistoryItemClick(GameHistory gameHistory);
    }

    public GameHistoryAdapter(OnGameHistoryItemClickListener onGameHistoryItemClickListener) {
        this.onGameHistoryItemClickListener = onGameHistoryItemClickListener;
    }

    @NonNull
    @Override
    public GameHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.match_history_item, parent, false);
        return new GameHistoryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHistoryItemViewHolder holder, int position) {
        holder.bind(this.gameHistoryList.getGameHistoryItems().get(position));
    }

    public void updateAdapter(GameHistoryList gameHistoryList) {
        this.gameHistoryList = gameHistoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.gameHistoryList == null || this.gameHistoryList.getGameHistoryItems() == null) {
            return 0;
        } else {
            return this.gameHistoryList.getGameHistoryItems().size();
        }
    }

    class GameHistoryItemViewHolder extends RecyclerView.ViewHolder {
        final private TextView championTV;
        final private TextView laneTV;
        final private TextView timestampTV;
        final private ImageView championImageIV;

        public GameHistoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            championTV = itemView.findViewById(R.id.champion_name_tv);
            laneTV = itemView.findViewById(R.id.summoner_lane_tv);
            timestampTV = itemView.findViewById(R.id.match_date_tv);
            championImageIV = itemView.findViewById(R.id.champion_image_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGameHistoryItemClickListener.onGameHistoryItemClick(
                        gameHistoryList.getGameHistoryItems().get(getAdapterPosition())
                    );
                }
            });
        }

        public void bind(GameHistory gameHistory) {
            Context ctx = this.itemView.getContext();

            championTV.setText(gameHistory.getChampionName());
            laneTV.setText(gameHistory.getLane());
            timestampTV.setText(gameHistory.getFormattedTimeStamp());

            Glide.with(ctx).load(gameHistory.getChampionIonUrl()).into(championImageIV);

        }
    }
}
