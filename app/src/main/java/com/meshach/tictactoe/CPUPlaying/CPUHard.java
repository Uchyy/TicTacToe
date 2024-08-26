package com.meshach.tictactoe.CPUPlaying;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.Classes.Player;
import com.meshach.tictactoe.GamePlay.SetEditText;
import com.meshach.tictactoe.Classes.GameViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CPUHard {

    private List<TableRow> rowsList;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private Player AI;
    private Context context;
    private List<Pair<Integer, Integer>> emptyCells = new ArrayList<>();
    private GameViewModel viewModel;
    private ViewModelStoreOwner owner;
    private int sequenceLength;

    public CPUHard(Context context, ViewModelStoreOwner owner) {
        this.context = context;
        this.owner = owner;
        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);

        rowsList = viewModel.getRowsList().getValue();
        Log.d("CPUPlay ROWSLIST: ", rowsList != null ? rowsList.toString() : "RowsList is null");

        this.sequenceLength = (rowsList.size() == 5) ? 4 : 3;

        editTextPositions = viewModel.getEditTextPositions().getValue();
        Log.d("CPUPlay Map: ", editTextPositions != null ? editTextPositions.toString() : "EditTextPositions is null");

        AI = viewModel.getCurrentPlayer().getValue();
        Log.d("CPUPlay CurPlayer: ", AI != null ? AI.toString() : "Current player is null");
    }

    public void setEmptyCellsHard() {
        emptyCells.clear();
        for (EditText editText : editTextPositions.keySet()) {
            if (editText.getText().toString().isEmpty()) {
                emptyCells.add(editTextPositions.get(editText));
            }
        }
    }

    public void hardMove() {
        setEmptyCellsHard();

        int[] arr = bestMove();
        int i = arr[0], j = arr[1];

        TableRow tableRow = rowsList.get(i);
        EditText editText = (EditText) tableRow.getChildAt(j);
        if (editText.getText().toString().isEmpty()) {
            setEditTextProperties(editText, AI.getPlayerSymbol());
            viewModel.setCurrentEditText(editText);
        } else {
            hardMove();
        }
    }

    private int[] bestMove() {
        setEmptyCellsHard();
        String[] arr = getStringArray(rowsList);
        int bestScore = Integer.MIN_VALUE;
        int[] bestPair = new int[2];

        // Check if CPU can win
        for (Pair<Integer, Integer> pair : emptyCells) {
            int i = pair.first;
            int j = pair.second;
            char c = AI.getPlayerSymbol().charAt(0);

            // Try the AI move
            if (arr[i].charAt(j) == ' ') {
                StringBuilder sb = new StringBuilder(arr[i]);
                sb.setCharAt(j, c);
                arr[i] = sb.toString(); // Update the array with the new string

                int moveValue = evaluateBoard(arr);
                if (moveValue == 10) { // Check if this move wins the game
                    Log.d("Winning move found: ", Arrays.toString(new int[]{i, j}));
                    return new int[]{i, j}; // Return immediately if a winning move is found
                }

                // Revert the move
                sb.setCharAt(j, ' ');
                arr[i] = sb.toString();
            }
        }

        // Check if CPU needs to block the opponent
        char opponentSymbol = AI.getPlayerSymbol().equals("X") ? 'O' : 'X';
        for (Pair<Integer, Integer> pair : emptyCells) {
            int i = pair.first;
            int j = pair.second;

            // Try the opponent's move to check if we need to block
            if (arr[i].charAt(j) == ' ') {
                StringBuilder sb = new StringBuilder(arr[i]);
                sb.setCharAt(j, opponentSymbol);
                arr[i] = sb.toString(); // Update the array with the opponent's move

                int moveValue = evaluateBoard(arr);
                if (moveValue == -10) {
                    Log.d("Blocking move found: ", Arrays.toString(new int[]{i, j}));
                    return new int[]{i, j}; // Block this move
                }

                // Revert the move
                sb.setCharAt(j, ' ');
                arr[i] = sb.toString();
            }
        }

        // If no immediate win or block, proceed with the usual evaluation logic
        for (Pair<Integer, Integer> pair : emptyCells) {
            int i = pair.first;
            int j = pair.second;
            char c = AI.getPlayerSymbol().charAt(0);

            // Try the AI move
            if (arr[i].charAt(j) == ' ') {
                StringBuilder sb = new StringBuilder(arr[i]);
                sb.setCharAt(j, c);
                arr[i] = sb.toString(); // Update the array with the new string

                int moveValue = evaluateBoard(arr);
                if (moveValue > bestScore) {
                    bestScore = moveValue;
                    bestPair = new int[]{i, j};
                }

                // Revert the move
                sb.setCharAt(j, ' ');
                arr[i] = sb.toString();
            }
        }

        Log.d("BestMove", "Best pair found: " + Arrays.toString(bestPair));
        return bestPair;
    }

    private String[] getStringArray(List<TableRow> rowsLists) {
        String[] arr = new String[rowsLists.size()];
        int j = 0;

        for (TableRow tableRow : rowsLists) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < tableRow.getChildCount(); i++) {
                if (tableRow.getChildAt(i) instanceof EditText) {
                    EditText editText = (EditText) tableRow.getChildAt(i);
                    String editTextText = editText.getText().toString();
                    s.append(editTextText.isEmpty() ? " " : editTextText);
                }
            }
            arr[j] = s.toString();
            j++;
        }

        Log.d("Array is: ", Arrays.toString(arr));
        return arr;
    }

    private int evaluateBoard(String[] board) {
        String cpu = new String(new char[sequenceLength]).replace("\0", AI.getPlayerSymbol());
        String opp = new String(new char[sequenceLength]).replace("\0", AI.getPlayerSymbol().equals("X") ? "O" : "X");

        // Check rows
        for (String string : board) {
            if (string.contains(cpu)) return 10;
            if (string.contains(opp)) return -10;
        }

        // Check columns
        for (int i = 0; i < board.length; i++) {
            StringBuilder column = new StringBuilder();
            for (String string : board) {
                column.append(string.charAt(i));
            }
            if (column.toString().contains(cpu)) return 10;
            if (column.toString().contains(opp)) return -10;
        }

        // Check main diagonals
        for (int startRow = 0; startRow <= board.length - sequenceLength; startRow++) {
            for (int startCol = 0; startCol <= board.length - sequenceLength; startCol++) {
                StringBuilder mainDiag = new StringBuilder();
                for (int k = 0; k < sequenceLength; k++) {
                    mainDiag.append(board[startRow + k].charAt(startCol + k));
                }
                if (mainDiag.toString().equals(cpu)) return 10;
                if (mainDiag.toString().equals(opp)) return -10;
            }
        }

        // Check secondary diagonals
        for (int startRow = 0; startRow <= board.length - sequenceLength; startRow++) {
            for (int startCol = sequenceLength - 1; startCol < board.length; startCol++) {
                StringBuilder secDiag = new StringBuilder();
                for (int k = 0; k < sequenceLength; k++) {
                    secDiag.append(board[startRow + k].charAt(startCol - k));
                }
                if (secDiag.toString().equals(cpu)) return 10;
                if (secDiag.toString().equals(opp)) return -10;
            }
        }

        return 0;
    }

    private void setEditTextProperties(EditText editText, String text) {
        SetEditText setEditText = new SetEditText(context);
        setEditText.setEditTextProperties(editText, text);
    }
}
