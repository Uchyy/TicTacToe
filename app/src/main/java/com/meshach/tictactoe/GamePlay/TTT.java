package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.CPUPlaying.CPUPlay;
import com.meshach.tictactoe.Classes.GameOver;
import com.meshach.tictactoe.Classes.Player;
import com.meshach.tictactoe.Classes.Winner;
import com.meshach.tictactoe.GameViewModel;

import java.util.List;
import java.util.Map;
import android.os.Handler;

public class TTT extends AppCompatActivity {
    private Player player1, player2, currentPlayer, playerX;
    private List<TableRow> rowsList;
    private  boolean gameOver = false;
    private Context context;
    private String mode;
    private EditText curEditText;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private CPUPlay cpu;
    private GameManager gameManager;
    private GameViewModel viewModel;
    private ViewModelStoreOwner owner;


    public TTT(Player player1, Player player2, ViewModelStoreOwner owner) {
        this.player1 = player1;
        this.player2 = player2;
        this.owner = owner;

        playerX = (player1.getPlayerSymbol().equals("X")) ? player1 : player2;
        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);

        rowsList = viewModel.getRowsList().getValue();
        Log.d("TTT ROWSLIST: ", rowsList != null ? rowsList.toString() : "RowsList is null");

        editTextPositions = viewModel.getEditTextPositions().getValue();
        Log.d("TTT Map: ", editTextPositions != null ? editTextPositions.toString() : "EditTextPositions is null");

        currentPlayer = viewModel.getCurrentPlayer().getValue();
        Log.d("TTT CurPlayer: ", currentPlayer != null ? currentPlayer.toString() : "Current player is null");

        mode = String.valueOf(viewModel.getMode().getValue());
        Log.d("TTT Mode: ", mode != null ? mode : "Mode is null");
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void startGame(View view) {
        if (gameOver) return; // Game over, ignore clicks
        Log.d("1: Current Player", currentPlayer.toString());

        currentPlayer = viewModel.getCurrentPlayer().getValue();
        String text = currentPlayer.getPlayerSymbol();
        gameManager = GameManager.getInstance(context, owner);

        if (currentPlayer.isCPU()) {
            Log.d("TTT Mode is: ", mode);
            cpu = new CPUPlay(context, owner);
            cpu.isCPUPlayer();
            viewModel.setRowsList(rowsList);

            if (checkForWin() || checkForDraw()) {
                gameOver = true;
                atGameOver();
                return;
                // Handle game over
            }

        } else {

            EditText editText = (EditText) view;
            if (editText.getText().toString().isEmpty()) {
                setEditTextProperties(editText, text);
                viewModel.setCurrentEditText(editText);
            }  else {
                return;
            }
            viewModel.setRowsList(rowsList);

        }

        if (checkForWin() || checkForDraw()) {
            gameOver = true;
            atGameOver();
            return;
            // Handle game over
        } else {
            switchPlayer(); // Switch to the other player
            if (currentPlayer.isCPU()) {
                new Handler().postDelayed(() -> startGame(null), 300);
            }
        }
        viewModel.setRowsList(rowsList);
    }

    private boolean checkForWin( ) {
        boolean win = gameManager.checkRow() || gameManager.checkCol() || gameManager.checkDiag();
        if (win) {
            Toast.makeText(context, "WINNER WINNER!!", Toast.LENGTH_SHORT).show();
        }
        return win;
    }

    private boolean checkForDraw() {
        // Iterate over all EditText views or board positions
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
        viewModel.setCurrentPlayer(currentPlayer);
    }


    private void setEditTextProperties(EditText editText, String text) {
        SetEditText setEditText = new SetEditText(context);
        setEditText.setEditTextProperties(editText, text);
    }

    private void atGameOver() {
        Log.d("TTT GAMEOVER: ", currentPlayer.getPlayerSymbol());

        String gameOverMode = checkForWin() ? "WIN" : "DRAW";
        GameOver gameOverClass = new GameOver(gameOverMode, owner, context);
        gameOverClass.atGameOver();
    }

}
