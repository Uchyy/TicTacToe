package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.GameViewModel;
import java.util.List;

public class BoardChecker {
    private List<TableRow> rowsList;
    private Context context;
    private int sequenceLength;
    private GameViewModel viewModel;

    public BoardChecker(Context context, ViewModelStoreOwner owner, int sequenceLength) {
        this.context = context;

        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);
        rowsList = viewModel.getRowsList().getValue();

        this.sequenceLength = sequenceLength;
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
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkDiag() {
        int size = rowsList.size();

        // Check main diagonals
        for (int start = 0; start <= size - sequenceLength; start++) {
            for (int diagStart = 0; diagStart <= size - sequenceLength; diagStart++) {
                boolean mainDiagEqual = true;
                String firstEditTextText = null;

                if (rowsList.get(start).getChildAt(diagStart) instanceof EditText) {
                    EditText firstEditText = (EditText) rowsList.get(start).getChildAt(diagStart);
                    firstEditTextText = firstEditText.getText().toString();
                    if (firstEditTextText.isEmpty()) {
                        continue;
                    }
                }

                for (int offset = 1; offset < sequenceLength; offset++) {
                    if (rowsList.get(start + offset).getChildAt(diagStart + offset) instanceof EditText) {
                        EditText editText = (EditText) rowsList.get(start + offset).getChildAt(diagStart + offset);
                        String editTextText = editText.getText().toString();
                        if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                            mainDiagEqual = false;
                            break;
                        }
                    }
                }

                if (mainDiagEqual) {
                    Toast.makeText(context, "Winning Main Diagonal Segment Found!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }

        // Check secondary diagonals
        for (int start = 0; start <= size - sequenceLength; start++) {
            for (int diagStart = sequenceLength - 1; diagStart < size; diagStart++) {
                boolean secDiagEqual = true;
                String firstEditTextText = null;

                if (rowsList.get(start).getChildAt(diagStart) instanceof EditText) {
                    EditText firstEditText = (EditText) rowsList.get(start).getChildAt(diagStart);
                    firstEditTextText = firstEditText.getText().toString();
                    if (firstEditTextText.isEmpty()) {
                        continue;
                    }
                }

                for (int offset = 1; offset < sequenceLength; offset++) {
                    if (rowsList.get(start + offset).getChildAt(diagStart - offset) instanceof EditText) {
                        EditText editText = (EditText) rowsList.get(start + offset).getChildAt(diagStart - offset);
                        String editTextText = editText.getText().toString();
                        if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                            secDiagEqual = false;
                            break;
                        }
                    }
                }

                if (secDiagEqual) {
                    Toast.makeText(context, "Winning Secondary Diagonal Segment Found!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }

        return false;
    }
}
