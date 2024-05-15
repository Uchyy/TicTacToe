package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.meshach.tictactoe.R;

import java.util.List;
import java.util.Map;

public class CPUPlay extends AppCompatActivity {

    private List<TableRow> rowsList;
    private Context context;
    private Player currentPlayer;
    private  boolean gameOver = false;
    private UserPlay user;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;

    public CPUPlay(Context context, List<TableRow> rowsList, Player currentPlayer ) {
        this.context = context;
        this.rowsList = rowsList;
        this.currentPlayer = currentPlayer;
    }

    public void setEditTextPositions(Map<EditText, Pair<Integer, Integer>> editTextPositions) {
        this.editTextPositions = editTextPositions;
    }

    public void isCPUPlayer() {

        user = new UserPlay(context, rowsList);

                 easyMode();


    }

    private void easyMode() {
        for (TableRow tableRow : rowsList) {
            EditText editText = null;
            String editTextText = null;

            for (int i = 0; i < tableRow.getChildCount(); i++) {
                if (tableRow.getChildAt(i) instanceof EditText) {
                    editText = (EditText) tableRow.getChildAt(i);
                    editTextText = editText.getText().toString();
                    if (editTextText.isEmpty()) {
                        editTextText = currentPlayer.getPlayerSymbol();
                        setEditTextProperties (editText, editTextText);
                        return;
                    }
                }
            }

        }
    }

    private void hardMode() {

    }

    private void setEditTextProperties(EditText editText, String text) {
        if (text.equals("X")) {
            editText.setTextColor(ContextCompat.getColor(context, R.color.blueX));
        } else {
            editText.setTextColor(ContextCompat.getColor(context, R.color.yellowO));
        }
        editText.setText(text);
    }



}
