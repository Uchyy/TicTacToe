package com.meshach.tictactoe.Classes;

import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.GameViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Winner {

    private String playerSymbol;
    private String winningSegment;
    private EditText currentEdittext;
    private GameViewModel viewModel;

    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private  List <EditText> winningMoves;
    private  List<TableRow> rowsList;


    public Winner(String playerSymbol, String winningSegment, ViewModelStoreOwner owner) {
        this.playerSymbol = playerSymbol;
        this.winningSegment = winningSegment;

        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);
        editTextPositions = viewModel.getEditTextPositions().getValue();

        rowsList = viewModel.getRowsList().getValue();
        this.currentEdittext = viewModel.getCurrentEditText().getValue();

    }

    public List<EditText> getWinningList () {
        return winningMoves;
    }

    private void setWinningLine() {
        EditText curEditText = viewModel.getCurrentEditText().getValue();
        Pair<Integer, Integer> pair = editTextPositions.get(curEditText);

        switch (winningSegment) {
            case "ROW":
                int row = pair.first;
                TableRow tableRow = rowsList.get(row);

                for (int i = 0; i < tableRow.getChildCount(); i++) {
                    if (tableRow.getChildAt(i) instanceof EditText) {
                        EditText editText = (EditText) tableRow.getChildAt(i);
                        winningMoves.add(editText);
                    }
                }
                break;

            case "COL":
                int col = pair.second;
                for (TableRow tr : rowsList) {
                    if (tr.getChildAt(col) instanceof EditText) {
                        EditText editText = (EditText) tr.getChildAt(col);
                        winningMoves.add(editText);
                    }
                }
                break;

            case "DIAGONAL":
                int i = pair.first;
                int j = pair.second;
                if (i == j) { // Main diagonal
                    for (int k = 0; k < rowsList.size(); k++) {
                        TableRow tr = rowsList.get(k);
                        if (tr.getChildAt(k) instanceof EditText) {
                            EditText editText = (EditText) tr.getChildAt(k);
                            winningMoves.add(editText);
                        }
                    }
                } else if (i + j == rowsList.size() - 1) { // Anti-diagonal
                    for (int k = 0; k < rowsList.size(); k++) {
                        TableRow tr = rowsList.get(k);
                        if (tr.getChildAt(rowsList.size() - 1 - k) instanceof EditText) {
                            EditText editText = (EditText) tr.getChildAt(rowsList.size() - 1 - k);
                            winningMoves.add(editText);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "Winner{" +
                " Player = " + playerSymbol +
                ", winningLine = " + winningSegment +
                "}";
    }

}
