package com.meshach.tictactoe.Classes;

import androidx.lifecycle.MutableLiveData;

public class Player {
    private String playerSymbol; // 'X' or 'O'
    private boolean vsCPU;
    private boolean isFirstPlayer;
    private boolean isCPU; // Indicates whether the player is a CPU player

    public Player(String playerSymbol, boolean vsCPU) {

        this.playerSymbol = playerSymbol;
        this.vsCPU = vsCPU;
        this.isFirstPlayer = (playerSymbol.equals("X")); // X always goes first
        this.isCPU = isCPU; // Default assumption: the player is not a CPU player
    }

    // Constructor for CPU player
    public Player(String playerSymbol) {
        this.playerSymbol = playerSymbol;
        this.vsCPU = true;
        this.isFirstPlayer = (playerSymbol.equals("X"));
        this.isCPU = true;
    }

    public String getPlayerSymbol() {
        return playerSymbol;
    }

    public boolean isVsCPU() {
        return vsCPU;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public boolean isCPU() {
        return isCPU;
    }


    // Method to determine if the player is going first
    public void determineFirstPlayer() {
        isFirstPlayer = (playerSymbol.equals("X"));
    }

    @Override
    public String toString() {
        return "Player{" +
                " playerSymbol='" + playerSymbol + '\'' +
                ", vsCPU=" + vsCPU +
                ", isFirstPlayer=" + isFirstPlayer +
                ", isCPU=" + isCPU +
                '}';
    }

}

