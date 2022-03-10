/*
Resources used were as follows
* Lab 2 instructions
* Lab 3 instructions
* https://www.youtube.com/watch?v=uMqgf17mx_U&ab_channel=ProGrammer
* https://www.youtube.com/watch?v=jcliHGR3CHo
 */
package com.example.rollcount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NumberOfRolls.OnFragmentInteractionListener{

    public static final String values = null;
    ListView gameList;

    ArrayAdapter<GameSessions> sessionAdapter;
    ArrayList<GameSessions> sessionDataAdapter;
    Button saveButton, delButton;
    boolean isDelSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameList =findViewById(R.id.game_sessions);
        saveButton = findViewById(R.id.saveButton);
        delButton = findViewById(R.id.delButton);
        isDelSelected = false;

        String []games = {};
        int []rolls = {};
        int []sides = {};
        loadData();
        //sessionDataAdapter = new ArrayList<>();

        for (int i = 0; i < games.length; i++) {
            sessionDataAdapter.add(new GameSessions(games[i], rolls[i], sides[i]));
        }

        sessionAdapter = new CustomList(this, sessionDataAdapter);

        gameList.setAdapter(sessionAdapter);

        final FloatingActionButton button = findViewById(R.id.Input);
        button.setOnClickListener(view -> {
            new NumberOfRolls().show(getSupportFragmentManager(),"ADD_SESSION");
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isDelSelected) {
                    sessionDataAdapter.remove(i);
                    isDelSelected = false;
                    sessionAdapter.notifyDataSetChanged();
                }
                else {
                    TextView v = (TextView) view.findViewById(R.id.game_session_text);
                    TextView rollUser = (TextView) view.findViewById(R.id.game_roll);
                    TextView sideUser = (TextView) view.findViewById(R.id.game_sides);
                    String name = (String) v.getText();
                    String rollNumber = (String) rollUser.getText();
                    String sideNumber = (String) sideUser.getText();
                    Toast.makeText(getApplicationContext(), name+" is selected", Toast.LENGTH_LONG).show();

                    //CustomList data = (CustomList) adapterView.getItemAtPosition(i);
                    GameSessions gameSessions = new GameSessions(name, Integer.parseInt(rollNumber), Integer.parseInt(sideNumber));

                    Intent intent = new Intent(MainActivity.this, DiceActivity.class);
                    intent.putExtra("values", gameSessions);
                    startActivity(intent);
                }
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDelSelected == false) {
                    isDelSelected = true;
                    Toast.makeText(getApplicationContext(), "Delete button is activated", Toast.LENGTH_LONG).show();
                    //isDelSelected = false;
                }
            }
        });

//        ListView listview = (ListView) findViewById(R.id.game_sessions);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView v = (TextView) view.findViewById(R.id.game_session_text);
//                TextView rollUser = (TextView) view.findViewById(R.id.game_roll);
//                TextView sideUser = (TextView) view.findViewById(R.id.game_sides);
//                String name = (String) v.getText();
//                String rollNumber = (String) rollUser.getText();
//                String sideNumber = (String) sideUser.getText();
//                Toast.makeText(getApplicationContext(), name+" is selected", Toast.LENGTH_LONG).show();
//
//                //CustomList data = (CustomList) adapterView.getItemAtPosition(i);
//                GameSessions gameSessions = new GameSessions(name, Integer.parseInt(rollNumber), Integer.parseInt(sideNumber));
//
//                Intent intent = new Intent(MainActivity.this, DiceActivity.class);
//                intent.putExtra("values", gameSessions);
//                startActivity(intent);
//            }
//        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sessionDataAdapter);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<GameSessions>>() {}.getType();
        sessionDataAdapter = gson.fromJson(json, type);

        if (sessionDataAdapter == null) {
            sessionDataAdapter = new ArrayList<>();
        }
    }

    @Override
    public void onOkPressed(GameSessions newSession) {
        sessionAdapter.add(newSession);
        saveData();
    };

}