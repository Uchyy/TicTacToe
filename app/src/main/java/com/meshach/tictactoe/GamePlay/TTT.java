package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.meshach.tictactoe.GamePlay.Player;
import com.meshach.tictactoe.R;

import java.util.List;
import java.util.Map;
import android.os.Handler;

public class TTT extends AppCompatActivity {
    private Player player1, player2, currentPlayer, playerX;
    private List<TableRow> rowsList;
    private  boolean gameOver = false;
    private Context context;
    private int winningLine, count = 0;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    CPUPlay cpu;
    UserPlay user;

    public TTT(Player player1, Player player2, List<TableRow> rowsList,  Map<EditText, Pair<Integer, Integer>> editTextPositions) {
        this.player1 = player1;
        this.player2 = player2;
        this.rowsList = rowsList;
        currentPlayer = (player1.getPlayerSymbol().equals("X")) ? player1 : player2;
        playerX = (player1.getPlayerSymbol().equals("X")) ? player1 : player2;
        this.editTextPositions = editTextPositions;

    }

    public void setContext(Context context) {
        this.context = context;
    }


    public void startGame(View view) {
        if (gameOver) return; // Game over, ignore clicks
        Log.d("1: Current Player", currentPlayer.toString());

        String text = currentPlayer.getPlayerSymbol();
        user = new UserPlay(context, rowsList);

        if (currentPlayer.isCPU()) {
            //makeCPUMove();
            cpu = new CPUPlay(context, rowsList, currentPlayer);
            cpu.isCPUPlayer();

            if (checkForWin() || checkForDraw()) {
                gameOver = true;
                // Handle game over
            }

        } else {

            EditText editText = (EditText) view;
            if (editText.getText().toString().isEmpty()) {
                setEditTextProperties(editText, text);
            }
        }

        if (checkForWin() || checkForDraw()) {
            gameOver = true;
            // Handle game over
        } else {
            switchPlayer(); // Switch to the other player
            if (currentPlayer.isCPU()) {
                new Handler().postDelayed(() -> startGame(null), 300);
            }
        }

    }

    private boolean checkForWin( ) {
        boolean win = user.checkRow() || user.checkCol() || user.checkDiag();
        if (win) {
            Toast.makeText(context, "WINNER WINNER!!", Toast.LENGTH_SHORT).show();
        }
        return win;
    }

    private boolean checkForDraw() {
        // Iterate over all EditText views or board positions
        count++;

        Log.d("COUNT", "Count is: "+count );

        for (EditText editText : editTextPositions.keySet()) {
            if (editText.getText().toString().isEmpty()) {
                Log.d("CheckForDraw", "Found empty cell: " + editTextPositions.get(editText));
                return false;
            }
        }
        Toast.makeText(context, "BOARD IS FULL!!", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void restartGame () {

        if (playerX.isCPU()) {
            switchPlayer();
            startGame(rowsList.get(0).getChildAt(0));
        }


    }
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
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
