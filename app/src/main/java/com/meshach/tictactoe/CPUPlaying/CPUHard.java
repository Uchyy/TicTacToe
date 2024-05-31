package com.meshach.tictactoe.CPUPlaying;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.meshach.tictactoe.GamePlay.Player;
import com.meshach.tictactoe.GamePlay.TTT;
import com.meshach.tictactoe.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CPUHard {

    private List<TableRow> rowsList;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private Player AI;
    private Context context;
    private List<Pair<Integer, Integer>> emptyCells = new ArrayList<>();

    public CPUHard(List<TableRow> rowsList, Map<EditText, Pair<Integer, Integer>> editTextPositions, Player AI, Context context) {
        this.rowsList = rowsList;
        this.editTextPositions = editTextPositions;
        this.AI = AI;
        this.context = context;
    }

    public void setEmptyCellsHard() {
        for (EditText editText : editTextPositions.keySet()) {
            if (editText.getText().toString().isEmpty()) {
                emptyCells.add(editTextPositions.get(editText));
            }
        }
    }

    public void hardMove() {
        setEmptyCellsHard();

        Log.d("EMPTY CELLS!!!: ", emptyCells.toString());
        Log.d("EMPTY CELLS size!!!: ", String.valueOf(emptyCells.size()));

            int[] arr = bestMove();
            int i = arr[0], j = arr[1];

            TableRow tableRow = rowsList.get(i);
            EditText editText = (EditText) tableRow.getChildAt(j);
            if (editText.getText().toString().isEmpty()) {
                setEditTextProperties(editText, AI.getPlayerSymbol());
                Log.d("Entering else 2: ", "");
            } else {
                Log.d("Entering else 3: ", "");
                hardMove();
            }
    }

    private int[] bestMove() {
        setEmptyCellsHard();
        Log.d("BestMove: ", emptyCells.toString());
        String[] arr = getStringArray(rowsList);
        int best = Integer.MIN_VALUE;
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

        // If no immediate win or block, proceed with the usual minimax logic
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
                if (moveValue > best) {
                    best = moveValue;
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
        String cpu = AI.getPlayerSymbol().equals("X") ? "XXX" : "OOO";
        String opp = AI.getPlayerSymbol().equals("X") ? "OOO" : "XXX";

        // Check rows
        for (String string : board) {
            if (string.equals(cpu)) return 10;
            if (string.equals(opp)) return -10;
        }

        // Check columns
        for (int i = 0; i < board.length; i++) {
            StringBuilder column = new StringBuilder();
            for (String string : board) {
                column.append(string.charAt(i));
            }
            if (column.toString().equals(cpu)) return 10;
            if (column.toString().equals(opp)) return -10;
        }

        // Check diagonals
        StringBuilder diagonal1 = new StringBuilder();
        StringBuilder diagonal2 = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            diagonal1.append(board[i].charAt(i));
            diagonal2.append(board[i].charAt(board.length - 1 - i));
        }
        if (diagonal1.toString().equals(cpu) || diagonal2.toString().equals(cpu)) return 10;
        if (diagonal1.toString().equals(opp) || diagonal2.toString().equals(opp)) return -10;

        Log.d("Score is : ", "12");
        return 0;
    }

    private void setEditTextProperties(EditText editText, String text) {
        editText.setText(text);
        int color = text.equals("X") ? R.color.blueX : R.color.yellowO;
        editText.setTextColor(ContextCompat.getColor(context, color));
    }




}