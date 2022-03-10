package com.example.rollcount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DiceActivity extends AppCompatActivity {
    TextView titleName;
    TextView rollNumber;
    TextView sideNumber;

    Button addNumber, undoButton;
    private EditText validNumber;

    TextView average;
    TextView minimum;
    TextView maximum;
    TextView histoID;

    int GLOBAL_MIN;
    int GLOBAL_MAX;
    double GLOBAL_AVG;
    int GLOBAL_ROLLS;
    int GLOBAL_SIDES;


    ArrayList<Integer> values = new ArrayList<>();
    ArrayList<Integer> outcomes = new ArrayList<>();

    private int latestNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        addNumber = findViewById(R.id.rollDice);
        titleName = findViewById(R.id.sessionName);
        rollNumber = findViewById(R.id.rollNumber);
        sideNumber = findViewById(R.id.sideNumber);
        validNumber = findViewById(R.id.rolledNumber);
        average = findViewById(R.id.calculated_avg);
        minimum = findViewById(R.id.calculated_min);
        maximum = findViewById(R.id.calculated_max);
        undoButton = findViewById(R.id.undo);
        histoID = findViewById(R.id.histogram);

        GameSessions gameSessions = getIntent().getParcelableExtra("values");

        titleName.setText(gameSessions.getSession());
        rollNumber.setText(Integer.toString(gameSessions.getRoll()));
        sideNumber.setText(Integer.toString(gameSessions.getSide()));

        addNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rolled = rollNumber.getText().toString();
                String sides = sideNumber.getText().toString();
                String inputted = validNumber.getText().toString();

                int rolledInt = Integer.parseInt(rolled);
                int sidesInt = Integer.parseInt(sides);
                int inputtedInt = Integer.parseInt(inputted);
                int upperLimit = rolledInt*sidesInt;
                GLOBAL_ROLLS = rolledInt;
                GLOBAL_SIDES = sidesInt;

                // To input the numbers from the user
                if ((inputtedInt < rolledInt)) {
                    Toast.makeText(getApplicationContext(), inputted+" is less than the range", Toast.LENGTH_LONG).show();
                    validNumber.setText("");
                }
                else if ((inputtedInt > upperLimit)) {
                    Toast.makeText(getApplicationContext(), inputted+" is more than the range", Toast.LENGTH_LONG).show();
                    validNumber.setText("");
                }
                else {
                    values.add(inputtedInt);
                    Toast.makeText(getApplicationContext(), inputted+" has been stored", Toast.LENGTH_LONG).show();
                    validNumber.setText("");
                }

                statCalculation(values);

                minimum.setText(Integer.toString(GLOBAL_MIN));
                maximum.setText(Integer.toString(GLOBAL_MAX));
                average.setText(Double.toString(GLOBAL_AVG));

                // To draw out the histogram
                String histo = histogramCreation(values);
                histoID.setText(histo);
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (values.size() > 1) {
                    latestNumber = values.get(values.size()-1);
                    values.remove(values.size()-1);
                    Toast.makeText(getApplicationContext(), Integer.toString(latestNumber)+" deleted", Toast.LENGTH_LONG).show();
                }
                else if (values.size() == 1) {
                    latestNumber = values.get(0);
                    values.remove(0);
                    minimum.setText("");
                    maximum.setText("");
                    average.setText("");

                    Toast.makeText(getApplicationContext(), "Last element ("+Integer.toString(latestNumber)+") removed", Toast.LENGTH_SHORT).show();
                }
                else {
                    minimum.setText("");
                    maximum.setText("");
                    average.setText("");
                    Toast.makeText(getApplicationContext(), "No rolls entered. Cannot delete", Toast.LENGTH_SHORT).show();
                }

                statCalculation(values);

                minimum.setText(Integer.toString(GLOBAL_MIN));
                maximum.setText(Integer.toString(GLOBAL_MAX));
                average.setText(Double.toString(GLOBAL_AVG));

                // To draw out the histogram
                String histo = histogramCreation(values);
                histoID.setText(histo);
            }
        });

        // Go back to the main activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outState.putIntegerArrayList(arrayName, values);
//        outState.putInt(minimumValue, GLOBAL_MIN);
//        outState.putInt(maximumValue, GLOBAL_MAX);
//        outState.putDouble(avgValue, GLOBAL_AVG);
//    }
//
//    @Override
//    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onRestoreInstanceState(savedInstanceState, persistentState);
//        savedInstanceState = state;
//    }


    //Method to calculate Histogram
    public String histogramCreation(ArrayList<Integer> arrayList) {
        ArrayList<Integer> frequencies = new ArrayList<>();
        // outcomes arraylist
        outcomes = new ArrayList<>();
        for (int i = GLOBAL_ROLLS; i <= GLOBAL_ROLLS*GLOBAL_SIDES; i++) {
            outcomes.add(i);
            frequencies.add(0);
        }
        // Toast.makeText(getApplicationContext(), outcomes.toString(), Toast.LENGTH_SHORT).show();
        // updating frequencies arrayList
        for (int i = 0; i < outcomes.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                //Toast.makeText(getApplicationContext(), Integer.toString(arrayList.size()), Toast.LENGTH_SHORT).show();
                if (outcomes.get(i).equals(arrayList.get(j))) {
                    // Toast.makeText(getApplicationContext(), "String", Toast.LENGTH_SHORT).show();
                    frequencies.set(i, frequencies.get(i)+1);
                }
            }
        }
        // making histogram
        String str = "";
        for (int i = 0; i < frequencies.size(); i++) {
            for (int j = 0; j < frequencies.get(i); j++) {
                str += "*";
            }
            str += "\n";
        }
        //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        return str;
    }

    //Method to calculate stats
    public void statCalculation(ArrayList<Integer> array) {
        if (array.size()>0) {
            int min = Collections.min(values);
            int max = Collections.max(values);
            int sum = 0;
            int length = values.size();
            for (int i = 0; i < length; i++) {
                sum += values.get(i);
            }
            double sumDouble = sum;
            double avg = sumDouble/length;

            GLOBAL_MIN = min;
            GLOBAL_MAX = max;
            GLOBAL_AVG = avg;
        }
        else {
            GLOBAL_AVG = 0.0;
            GLOBAL_MIN = 0;
            GLOBAL_MAX = 0;
        }
    }

}