package com.example.rollcount;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<GameSessions> {

    private Context context;
    private ArrayList<GameSessions> gameSession;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CustomList(Context context, ArrayList<GameSessions> gameSession) {
        super(context, 0, gameSession);
        this.context = context;
        this.gameSession =gameSession;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        GameSessions gameSessions = gameSession.get(position);

        TextView gameName =view.findViewById(R.id.game_session_text);
        TextView rollNumber = view.findViewById(R.id.game_roll);
        TextView sideNumber = view.findViewById(R.id.game_sides);

        gameName.setText(gameSessions.getSession());
        rollNumber.setText(Integer.toString(gameSessions.getRoll()));
        sideNumber.setText(Integer.toString(gameSessions.getSide()));

        return view;
    }


}
