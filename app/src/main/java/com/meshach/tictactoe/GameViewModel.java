package com.meshach.tictactoe;

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
    private MutableLiveData<Player> currentPlayer = new MutableLiveData<>();
    private MutableLiveData<Map<EditText, Pair<Integer, Integer>>> editTextPositions = new MutableLiveData<>(new HashMap<>());
    private MutableLiveData<List<TableRow>> rowsList = new MutableLiveData<>(new ArrayList<>());

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
}
