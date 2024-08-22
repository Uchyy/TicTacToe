package com.meshach.tictactoe.Classes;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.GamePlay.GameManager;
import com.meshach.tictactoe.GameViewModel;

public class GameOver {

    private GameManager gameManager;
    private GameViewModel viewModel;
    private ViewModelStoreOwner owner;
    private Player currentPlayer;
    private Context context;

    private String gameOverMode;
    private boolean checkForWin;

    public GameOver(String gameOverMode, ViewModelStoreOwner owner, Context context) {
        this.gameOverMode = gameOverMode;
        this.owner = owner;
        this.context = context;

        // Initialize ViewModel and other necessary components
        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);
        currentPlayer = viewModel.getCurrentPlayer().getValue();

        if (currentPlayer == null) {
            Log.e("GameOver", "Current player is null during initialization");
        } else {
            Log.d("GameOver", "Current player during initialization: " + currentPlayer.getPlayerSymbol());
        }

        gameManager = GameManager.getInstance(context, owner);
        checkForWin = gameOverMode.equals("WIN");
    }

    public void atGameOver() {
        Log.e("GameOver CLASS.", "Current player is null");
        if (currentPlayer == null) {
            Log.e("GameOver", "Current player is null");
            return;
        }

        Log.d("CURRENT PLAYER: ", currentPlayer.getPlayerSymbol());

        if (checkForWin) {
            String winningLine = gameManager.getWinningLine();
            Log.d("WINNING LINE IS: ", winningLine);

            // Retrieve the current win count
            if ("X".equals(currentPlayer.getPlayerSymbol())) {
                Integer wins = viewModel.getPlayerXWins().getValue();
                if (wins != null) {
                    Log.d("BEFORE - X WINS: ", String.valueOf(wins));
                    viewModel.setPlayerXWins(wins + 1);
                }
            } else if ("O".equals(currentPlayer.getPlayerSymbol())) {
                Integer wins = viewModel.getPlayerOWins().getValue();
                if (wins != null) {
                    Log.d("BEFORE - 0 WINS: ", String.valueOf(wins));
                    viewModel.setPlayerOWins(wins + 1);
                }
            }

            String winningSegment = gameManager.getWinningLine();
            Winner winner = new Winner(currentPlayer.getPlayerSymbol(), winningSegment, owner);
            winner.setWinningAnim();

        } else {
            Integer draw = viewModel.getDraws().getValue();
            if (draw != null) {
                Log.d("BEFORE - DRAWS: ", String.valueOf(draw));
                viewModel.setDraws(draw + 1);
            }

            DrawClass drawClass = new DrawClass(owner);
            drawClass.setDrawAnim();
        }
    }


}
