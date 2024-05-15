package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.meshach.tictactoe.MainActivity;

import java.util.List;

public class UserPlay {
    private List<TableRow> rowsList;
    private Context context;

    public UserPlay(Context context, List<TableRow> rowsList ) {
        this.context = context;
        this.rowsList = rowsList;
    }

    boolean checkRow() {

        int count = 0;
        for (TableRow tableRow : rowsList) {
            String firstEditTextText = null;
            count++;

            // Check if the first EditText in the row is empty
            if (tableRow.getChildAt(0) instanceof EditText) {
                EditText firstEditText = (EditText) tableRow.getChildAt(0);
                firstEditTextText = firstEditText.getText().toString();
                if (firstEditTextText.isEmpty()) {
                    continue; // Skip to the next row if the first EditText is empty
                }
            }

            // Check if all EditTexts in the row have the same non-empty text
            boolean allTextsEqual = true;
            for (int i = 1; i < tableRow.getChildCount(); i++) {
                if (tableRow.getChildAt(i) instanceof EditText) {
                    EditText editText = (EditText) tableRow.getChildAt(i);
                    String editTextText = editText.getText().toString();
                    if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                        allTextsEqual = false;
                    }
                }
            }

            if (allTextsEqual) {
                //this.winningLine = count;
                Toast.makeText(context, "Winning Row is: Row "+count+"!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;
    }

    boolean checkCol() {
        for (int j = 0; j < rowsList.size(); j++) {
            String firstEditTextText = null;
            boolean allTextsEqual = true;

            // Get the text of the first EditText in the column
            TableRow firstRow = rowsList.get(0);
            if (firstRow.getChildAt(j) instanceof EditText) {
                EditText firstEditText = (EditText) firstRow.getChildAt(j);
                firstEditTextText = firstEditText.getText().toString();
                if (firstEditTextText.isEmpty()) {
                    continue; // Skip to the next column if the first EditText is empty
                }
            }

            // Check if all EditTexts in the column have the same non-empty text
            for (int i = 1; i < rowsList.size(); i++) {
                TableRow tableRow = rowsList.get(i);
                if (tableRow.getChildAt(j) instanceof EditText) {
                    EditText editText = (EditText) tableRow.getChildAt(j);
                    String editTextText = editText.getText().toString();
                    if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                        allTextsEqual = false;
                        break; // Break out of the loop if any EditText doesn't match the first EditText
                    }
                }
            }

            // Return true if all EditTexts in the column have the same non-empty text
            if (allTextsEqual) {
                //this.winningLine = j;
                Toast.makeText(context, "Winning Column is: Column " + j + "!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;
    }

    boolean checkDiag() {
        String firstEditTextText = null;
        int size = rowsList.size();
        int mid = size / 2;

        TableRow middleRow = rowsList.get(mid);
        EditText middleEditText = (EditText) middleRow.getChildAt(mid);
        firstEditTextText = middleEditText.getText().toString();
        if (firstEditTextText.isEmpty()) return false;

        // Check main diagonal (top-left to bottom-right)
        boolean mainDiagEqual = true;
        for (int i = 0; i < size; i++) {
            TableRow row = rowsList.get(i);
            EditText editText = (EditText) row.getChildAt(i);
            String editTextText = editText.getText().toString();
            if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                mainDiagEqual = false;
                break;
            }
        }

        // Check secondary diagonal (top-right to bottom-left)
        boolean secDiagEqual = true;
        for (int i = 0; i < size; i++) {
            TableRow row = rowsList.get(i);
            EditText editText = (EditText) row.getChildAt(size - 1 - i);
            String editTextText = editText.getText().toString();
            if (editTextText.isEmpty() || !editTextText.equals(firstEditTextText)) {
                secDiagEqual = false;
                break;
            }
        }

        if (mainDiagEqual || secDiagEqual) {
            Toast.makeText(context, "Winning Diagonal!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

}
