package com.example.qrgameteam15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/**
 * This class creates an ArrayList of players to be displayed as a
 * custom view.
 */
public class CustomList extends ArrayAdapter<Player> {

    private ArrayList<Player> players;
    private Context context;
    /**
     * Constructor for the CustomList.
     * @param context
     * Expects object from Context class.
     * @param players
     * Expects a Player ArrayList object with list of players.
     */
    public CustomList(Context context, ArrayList<Player> players){
        super(context,0,players);
        this.players = players;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Player player = players.get(position);

        TextView userName = view.findViewById(R.id.user_text);
        TextView score = view.findViewById(R.id.score_text);

        userName.setText(player.getUsername());
        score.setText(player.getTotalScore());

        return view;
    }
}
