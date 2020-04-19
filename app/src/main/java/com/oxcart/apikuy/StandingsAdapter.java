package com.oxcart.apikuy;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.ViewHolder> {
    private static final String LOG_TAG = StandingsAdapter.class.getSimpleName();
    private Context context;
    private List<Standings> mStandings;
    private Activity act;

    public StandingsAdapter(Context context, List<Standings> standings) {
        this.context = context;
        this.mStandings = standings;

//        if (mStandings == null) {
//            Log.d(LOG_TAG, "Where art thy data?");
//        }
    }

    @NonNull
    @Override
    public StandingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_standings,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StandingsAdapter.ViewHolder holder, int position) {
        final Standings standings = mStandings.get(position);

        holder.textTablePosition.setText(Integer.toString(standings.getTablePosition()));
        holder.textClubName.setText(standings.getClubName());
        holder.textPlayedGames.setText(Integer.toString(standings.getPlayedGames()));
        holder.textGoalDifference.setText(Integer.toString(standings.getGoalDifference()));
        holder.textPoints.setText(Integer.toString(standings.getPoints()));

        Picasso.get()
                .load(standings.getLogoClub())
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .into(holder.imageLogo, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        //INI BELUM FIX
//                        if (!act.isFinishing()){
//                            GlideToVectorYou.init()
//                                    .with(act)
//                                    .load(Uri.parse(standings.getLogoClub()), holder.imageLogo);
//                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mStandings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTablePosition;
        public TextView textClubName;
        public TextView textPlayedGames;
        public TextView textGoalDifference;
        public TextView textPoints;
        public ImageView imageLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTablePosition = itemView.findViewById(R.id.text_table_position);
            textClubName = itemView.findViewById(R.id.text_team_name);
            textPlayedGames = itemView.findViewById(R.id.text_team_played);
            textGoalDifference = itemView.findViewById(R.id.text_team_difference);
            textPoints = itemView.findViewById(R.id.text_team_points);
            imageLogo = itemView.findViewById(R.id.image_team_logo);
        }
    }

    public void setFilter(List<Standings> newListStanding){
        mStandings = new ArrayList<>();
        mStandings.addAll(newListStanding);
        notifyDataSetChanged();
    }
}
