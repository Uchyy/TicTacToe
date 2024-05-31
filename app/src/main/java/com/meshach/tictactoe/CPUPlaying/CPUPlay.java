package com.meshach.tictactoe.CPUPlaying;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.GamePlay.Player;
import com.meshach.tictactoe.GamePlay.UserPlay;
import com.meshach.tictactoe.GameViewModel;
import com.meshach.tictactoe.MainActivity;
import com.meshach.tictactoe.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CPUPlay extends AppCompatActivity {

    private List<TableRow> rowsList;
    private Context context;
    private Player currentPlayer;
    private boolean gameOver = false;
    private String mode;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private List <Pair<Integer, Integer>> emptyCells = new ArrayList<>();
    private GameViewModel viewModel;

    private String [] boardStrings;

    public CPUPlay(Context context, ViewModelStoreOwner owner ) {

        this.context = context;
        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);

        rowsList = viewModel.getRowsList().getValue();
        Log.d("CPUPlay ROWSLIST: ", rowsList != null ? rowsList.toString() : "RowsList is null");

        editTextPositions = viewModel.getEditTextPositions().getValue();
        Log.d("CPUPlay Map: ", editTextPositions != null ? editTextPositions.toString() : "EditTextPositions is null");

        currentPlayer = viewModel.getCurrentPlayer().getValue();
        Log.d("CPUPlay CurPlayer: ", currentPlayer != null ? currentPlayer.toString() : "Current player is null");

    }

    public void isCPUPlayer() {

        mode = String.valueOf(viewModel.getMode().getValue());
        Log.d("CPUPlay Mode: ", mode != null ? mode : "Mode is null");

        switch(mode) {
            case "EASY":
                easyMode();
                break;
            case "HARD":
                CPUHard hard = new CPUHard(rowsList, editTextPositions, currentPlayer, context);
                hard.hardMove();
                break;
            default:
                mediumMode();
        }

    }

    //public void setMode (String mode) {this.mode = mode;}

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

    private void mediumMode() {
            EditText editText = null;
            String editTextText = null;

            int i = rowsList.size() / 2;
            if (rowsList.get(i).getChildAt(i) != null) {
                editText = (EditText) rowsList.get(i).getChildAt(i);
                editTextText = editText.getText().toString();
                if (editTextText.isEmpty()) {
                    editTextText = currentPlayer.getPlayerSymbol();
                    setEditTextProperties(editText, editTextText);
                    return;
                }
            }

            editText = getRandomEditText();
            if (editText != null) {
                editTextText = editText.getText().toString();
                if (editTextText.isEmpty()) {
                    editTextText = currentPlayer.getPlayerSymbol();
                    setEditTextProperties(editText, editTextText);
                    return;
                }
            }

            mediumMode();
    }

    private EditText getRandomEditText() {
        if (rowsList == null || rowsList.isEmpty()) {
            return null; // Or handle the case appropriately
        }

        Random random = new Random();

        int rowIndex = random.nextInt(rowsList.size());
        TableRow selectedRow = rowsList.get(rowIndex);

        if (selectedRow.getChildCount() == 0) {
            return null; // Or handle the case appropriately
        }

        int editTextIndex = random.nextInt(selectedRow.getChildCount());
        return (EditText) selectedRow.getChildAt(editTextIndex);
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
