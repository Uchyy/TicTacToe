package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.meshach.tictactoe.R;

import java.util.List;

public class CPUPlay extends AppCompatActivity {

    private List<TableRow> rowsList;
    private Context context;
    private Player currentPlayer;
    private EditText editText;

    public CPUPlay(Context context, List<TableRow> rowsList, Player currentPlayer ) {
        this.context = context;
        this.rowsList = rowsList;
        this.currentPlayer = currentPlayer;
    }

     public void isCPUPlayer() {
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 easyMode();
             }
         }, 300);


    }

    private void easyMode() {
        for (TableRow tableRow : rowsList) {
            EditText editText = null;
            String editTextText = null;
            Log.d("CPUPlaying", "before Loop");

            for (int i = 0; i < tableRow.getChildCount(); i++) {
                if (tableRow.getChildAt(i) instanceof EditText) {
                    editText = (EditText) tableRow.getChildAt(i);
                    editTextText = editText.getText().toString();
                    if (editTextText.isEmpty()) {
                        //this.editText = editText;
                        //editText.performClick();
                        editTextText = currentPlayer.getPlayerSymbol();
                        setEditTextProperties (editText, editTextText);
                        return;
                    }
                }
            }

        }
    }

    public EditText getEditText () {
        Log.d("CPUPlaying", "Fetting EditText");
        return editText;//returns null
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
