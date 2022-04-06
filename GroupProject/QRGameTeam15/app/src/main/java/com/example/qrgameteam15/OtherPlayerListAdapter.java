package com.example.qrgameteam15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
/**
 * This class acts as an adapter for OtherPlayerList list.
 */
public class OtherPlayerListAdapter extends ArrayAdapter<Player> {
    private ArrayList<Player> players;
    private Context context;
    private int myRecource;
    /**
     * Constructor for OtherPlayerListAdapter.
     * @param context
     * Expects object from Context class.
     * @param resource
     * Expects object from Integer class.
     * @param players
     * Expects ArrayList of type Player.
     */
    public OtherPlayerListAdapter(Context context, int resource, ArrayList<Player> players){
        super(context,resource,players);
        this.players = players;
        this.myRecource = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(myRecource, parent, false);
        }

        Player player = players.get(position);

        TextView otherPlayerName = view.findViewById(R.id.otherPlayerName);
        TextView otherPlayerScore = view.findViewById(R.id.otherPlayerScore);
        TextView otherPlayerNScanned = view.findViewById(R.id.otherPlayerNumScanned);
        TextView otherPlayerRank = view.findViewById(R.id.otherPlayerrankID);

        otherPlayerName.setText(player.getUsername());
        otherPlayerScore.setText("Total Score: " + String.valueOf(player.getTotalScore()));
        otherPlayerNScanned.setText("Scanned: " + Integer.toString(player.numberOfCode()) + " codes");
        otherPlayerRank.setText("High Score: " + Integer.toString(player.getHighestScore()));  // TODO: cmput player rank

        return view;
    }

}
