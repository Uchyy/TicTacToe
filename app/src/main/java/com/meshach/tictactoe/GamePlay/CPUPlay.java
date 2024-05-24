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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CPUPlay extends AppCompatActivity {

    private List<TableRow> rowsList;
    private Context context;
    private Player currentPlayer;
    private  boolean gameOver = false;
    private UserPlay user;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;

    private String [] boardStrings;

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

                 mediumMode();


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

    private void hardMode() {

        TableRow[] rowsArray = rowsList.toArray(new TableRow[0]);
        List <Pair<Integer, Integer>> emptyCells = getEmptyCells ();

        for (Pair<Integer, Integer> pair: emptyCells ) {

        }


    }
    private int evaluate(TableRow [] board) {
        // Check rows, columns, and diagonals for a win
        // Return 10 if "X" wins, -10 if "O" wins, or 0 otherwise

        // Check rows
        for (TableRow row : board) {
            String line = "";
            for (int i = 0; i < row.getChildCount(); i++) {
                EditText cell = (EditText) row.getChildAt(i);
                line += cell.getText().toString();
            }
            if (line.equals("XXX"))
                return 10;
            else if (line.equals("OOO"))
                return -10;
        }

        // Check columns
        for (int col = 0; col < board.length; col++) {
            String line = "";
            for (TableRow row : board) {
                EditText cell = (EditText) row.getChildAt(col);
                line += cell.getText().toString();
            }
            if (line.equals("XXX"))
                return 10;
            else if (line.equals("OOO"))
                return -10;
        }

        // Check diagonals
        String line1 = "";
        String line2 = "";
        for (int i = 0; i < board.length; i++) {
            line1 += ((EditText) board[i].getChildAt(i)).getText().toString();
            line2 += ((EditText) board[i].getChildAt(board.length - 1 - i)).getText().toString();
        }
        if (line1.equals("XXX") || line2.equals("XXX"))
            return 10;
        else if (line1.equals("OOO") || line2.equals("OOO"))
            return -10;

        return 0;
    }

    private  String [] getStringArray

    private List <Pair<Integer, Integer>> getEmptyCells () {

        List <Pair<Integer, Integer>> emptyCells = new ArrayList<>();
        for (TableRow tableRow : rowsList) {
            EditText editText = null;
            String editTextText = null;

            for (int i = 0; i < tableRow.getChildCount(); i++) {
                if (tableRow.getChildAt(i) instanceof EditText) {
                    editText = (EditText) tableRow.getChildAt(i);
                    editTextText = editText.getText().toString();
                    if (editTextText.isEmpty()) {
                        emptyCells.add(editTextPositions.get(editText));
                    }
                }
            }
        }

        return  emptyCells;
    }

    private EditText getRandomEditText() {
        // Ensure there are rows available
        if (rowsList == null || rowsList.isEmpty()) {
            return null; // Or handle the case appropriately
        }

        Random random = new Random();

        // Select a random TableRow
        int rowIndex = random.nextInt(rowsList.size());
        TableRow selectedRow = rowsList.get(rowIndex);

        // Ensure the TableRow has children
        if (selectedRow.getChildCount() == 0) {
            return null; // Or handle the case appropriately
        }

        // Select a random EditText within the TableRow
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
