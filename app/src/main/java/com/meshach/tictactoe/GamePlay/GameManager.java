package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.GameViewModel;

import java.util.List;

public class GameManager {
    private List<TableRow> rowsList;
    private Context context;
    private GameViewModel viewModel;
    private int sequenceLength;
    private static GameManager instance;
    private enum winningLine {
        ROW,
        COL,
        MAINDIAGONAL,
        SECDIAGONAL
    };
    private winningLine line;

    public GameManager(Context context, ViewModelStoreOwner owner ) {
        this.context = context;
        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);
        rowsList = viewModel.getRowsList().getValue();

        assert rowsList != null;
        sequenceLength = (rowsList.size() == 5) ? 4 : 3;

    }

    public static GameManager getInstance(Context context, ViewModelStoreOwner owner) {
        if (instance == null) {
            instance = new GameManager(context, owner);
        }
        return instance;
    }

    public boolean checkRow() {
        for (TableRow tableRow : rowsList) {
            for (int start = 0; start <= tableRow.getChildCount() - sequenceLength; start++) {
                boolean allTextsEqual = true;
                String firstEditTextText = null;

                if (tableRow.getChildAt(start) instanceof EditText) {
                    EditText firstEditText = (EditText) tableRow.getChildAt(start);
                    firstEditTextText = firstEditText.getText().toString();
                    if (firstEditTextText.isEmpty()) {
                        continue;
                    }
                }

                for (int offset = 1; offset < sequenceLength; offset++) {
                    if (tableRow.getChildAt(start + offset) instanceof EditText) {
                        EditText editText = (EditText) tableRow.getChildAt(start + offset);
                        String editTextText = editText.getText().toString();
                        if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                            allTextsEqual = false;
                            break;
                        }
                    }
                }

                if (allTextsEqual) {
                    Toast.makeText(context, "Winning Row Segment Found!", Toast.LENGTH_SHORT).show();
                    line = winningLine.ROW;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkCol() {
        int columnCount = rowsList.get(0).getChildCount();
        for (int col = 0; col < columnCount; col++) {
            for (int start = 0; start <= rowsList.size() - sequenceLength; start++) {
                boolean allTextsEqual = true;
                String firstEditTextText = null;

                if (rowsList.get(start).getChildAt(col) instanceof EditText) {
                    EditText firstEditText = (EditText) rowsList.get(start).getChildAt(col);
                    firstEditTextText = firstEditText.getText().toString();
                    if (firstEditTextText.isEmpty()) {
                        continue;
                    }
                }

                for (int offset = 1; offset < sequenceLength; offset++) {
                    if (rowsList.get(start + offset).getChildAt(col) instanceof EditText) {
                        EditText editText = (EditText) rowsList.get(start + offset).getChildAt(col);
                        String editTextText = editText.getText().toString();
                        if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                            allTextsEqual = false;
                            break;
                        }
                    }
                }

                if (allTextsEqual) {
                    Toast.makeText(context, "Winning Column Segment Found!", Toast.LENGTH_SHORT).show();
                    line = winningLine.COL;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkDiag() {
        int size = rowsList.size();

        // Check main diagonals
        for (int rowStart = 0; rowStart <= size - sequenceLength; rowStart++) {
            for (int colStart = 0; colStart <= size - sequenceLength; colStart++) {
                boolean mainDiagEqual = true;
                String firstEditTextText = null;

                if (rowsList.get(rowStart).getChildAt(colStart) instanceof EditText) {
                    EditText firstEditText = (EditText) rowsList.get(rowStart).getChildAt(colStart);
                    firstEditTextText = firstEditText.getText().toString();
                    if (firstEditTextText.isEmpty()) {
                        continue;
                    }
                }

                for (int offset = 1; offset < sequenceLength; offset++) {
                    if (rowsList.get(rowStart + offset).getChildAt(colStart + offset) instanceof EditText) {
                        EditText editText = (EditText) rowsList.get(rowStart + offset).getChildAt(colStart + offset);
                        String editTextText = editText.getText().toString();
                        if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                            mainDiagEqual = false;
                            break;
                        }
                    }
                }

                if (mainDiagEqual) {
                    Toast.makeText(context, "Winning Main Diagonal Segment Found!", Toast.LENGTH_SHORT).show();
                    line = winningLine.MAINDIAGONAL;
                    return true;
                }
            }
        }

        // Check secondary diagonals
        for (int rowStart = 0; rowStart <= size - sequenceLength; rowStart++) {
            for (int colStart = sequenceLength - 1; colStart < size; colStart++) {
                boolean secDiagEqual = true;
                String firstEditTextText = null;

                if (rowsList.get(rowStart).getChildAt(colStart) instanceof EditText) {
                    EditText firstEditText = (EditText) rowsList.get(rowStart).getChildAt(colStart);
                    firstEditTextText = firstEditText.getText().toString();
                    if (firstEditTextText.isEmpty()) {
                        continue;
                    }
                }

                for (int offset = 1; offset < sequenceLength; offset++) {
                    if (rowsList.get(rowStart + offset).getChildAt(colStart - offset) instanceof EditText) {
                        EditText editText = (EditText) rowsList.get(rowStart + offset).getChildAt(colStart - offset);
                        String editTextText = editText.getText().toString();
                        if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                            secDiagEqual = false;
                            break;
                        }
                    }
                }

                if (secDiagEqual) {
                    Toast.makeText(context, "Winning Secondary Diagonal Segment Found!", Toast.LENGTH_SHORT).show();
                    line = winningLine.SECDIAGONAL;
                    return true;
                }
            }
        }

        return false;
    }

    public String getWinningLine () {
        Log.d("GET WINNING LINE CALLED:", "LINE IS: "+ line);
        return String.valueOf(line);
    }
}
