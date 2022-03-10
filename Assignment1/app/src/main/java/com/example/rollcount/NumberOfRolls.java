package com.example.rollcount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NumberOfRolls#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberOfRolls extends DialogFragment {

    private EditText sessionInput;
    private EditText rollsInput;
    private EditText sidesInput;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(GameSessions newSession);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "exception");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//      return super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_rolls_layout, null);

        sessionInput = view.findViewById(R.id.SessionName);
        rollsInput = view.findViewById(R.id.NumberOfRolls);
        sidesInput = view.findViewById(R.id.NumberOfSides);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add new Session")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String session = sessionInput.getText().toString();
                        String roll = rollsInput.getText().toString();
                        int rolls = Integer.parseInt(roll);
                        String side = sidesInput.getText().toString();
                        int sides = Integer.parseInt(side);
                        listener.onOkPressed(new GameSessions(session, rolls, sides));
                    }
                }).create();
    }
}