package com.meshach.tictactoe.Classes;

import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meshach.tictactoe.Classes.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameViewModel extends ViewModel {
    private MutableLiveData<String> mode = new MutableLiveData<>();
    private MutableLiveData<Integer> playerXWins;
    private MutableLiveData<Integer> draws;
    private MutableLiveData<Integer> playerOWins;
    private MutableLiveData<EditText>  currEditText = new MutableLiveData<>();
    private MutableLiveData<Player> currentPlayer = new MutableLiveData<>();
    private MutableLiveData<Map<EditText, Pair<Integer, Integer>>> editTextPositions = new MutableLiveData<>(new HashMap<>());
    private MutableLiveData<List<TableRow>> rowsList = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<EditText> currentEditText = new MutableLiveData<>();
    private String gameOverMode;

    public GameViewModel() {
        playerOWins = new MutableLiveData<>();
        playerXWins = new MutableLiveData<>();
        draws = new MutableLiveData<>();

        // Set initial values to 0
        playerOWins.setValue(0);
        playerXWins.setValue(0);
        draws.setValue(0);

    }

    // String Mode
    public LiveData<String> getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode.setValue(mode);
    }

    // Map EditTextPositions
    public LiveData<Map<EditText, Pair<Integer, Integer>>> getEditTextPositions() {
        return editTextPositions;
    }
    public void setEditTextPositions(Map<EditText, Pair<Integer, Integer>> editTextPositions) {
        this.editTextPositions.setValue(editTextPositions);
    }

    // List: rowsList
    public LiveData<List<TableRow>> getRowsList() {
        return rowsList;
    }
    public void setRowsList(List<TableRow> rowsList) {
        this.rowsList.setValue(rowsList);
    }

    // Player: Current Player
    public LiveData<Player> getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer.setValue(currentPlayer);
    }

    //Current Edittext
    public LiveData<EditText> getCurrentEditText() {
        return currentEditText;
    }
    public void setCurrentEditText(EditText currentEditText) {
        this.currentEditText.setValue(currentEditText);
    }

    //Player O Wins
    public LiveData<Integer> getPlayerOWins() {
        return playerOWins;
    }
    public void setPlayerOWins(Integer playerOWins) {
        this.playerOWins.setValue(playerOWins);
        Log.d("WINNER PLAYER: ", "PLAYERO");
    }

    //Player X Wins
    public LiveData<Integer> getPlayerXWins() {
        return playerXWins;
    }
    public void setPlayerXWins(Integer playerXWins) {
        this.playerXWins.setValue(playerXWins);
        Log.d("WINNER PLAYER: ", "PLAYERX");
    }

    //Draws
    public LiveData<Integer> getDraws() {
        return draws;
    }
    public void setDraws(Integer draws) {
        this.draws.setValue(draws);
    }

    public void resetGame() {

        mode.setValue(null);
        playerXWins.setValue(0);
        playerOWins.setValue(0);
        draws.setValue(0);
        currEditText.setValue(null);
        currentPlayer.setValue(null);
        editTextPositions.setValue(new HashMap<>());
        rowsList.setValue(new ArrayList<>());
        currentEditText.setValue(null);
        gameOverMode = null;



    }

}
