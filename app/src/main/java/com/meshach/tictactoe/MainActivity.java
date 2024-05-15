package com.meshach.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.meshach.tictactoe.GamePlay.CPUPlay;
import com.meshach.tictactoe.GamePlay.Player;
import com.meshach.tictactoe.GamePlay.TTT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private GameBoard gameBoard;
    private TableLayout tableLayout;
    private String userPlayer; int boardSize; boolean vsCPU;
    private ConstraintLayout constraintLayout;
    private List<TableRow> rowsList = new ArrayList<>();
    private boolean isPlayerX;
    Player player1, player2, playerX;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    TTT ttt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        boardSize = intent.getIntExtra("board", 0);
        userPlayer = intent.getStringExtra("player1");
        vsCPU = intent.getBooleanExtra("vsCPU", true);
        String p2 = (userPlayer.equals("X")) ? "O" : "X";
        editTextPositions = new HashMap<>();

        constraintLayout = findViewById(R.id.coordinatorLayout);
        tableLayout = constraintLayout.findViewById(R.id.gameBoard);
        gameBoard = new GameBoard();


        player1 = new Player(userPlayer, vsCPU);
        if (vsCPU) {
            player2 = new Player(p2);
        } else {
            player2 = new Player(p2, vsCPU);
        }

        ttt = new TTT (player1, player2, rowsList, editTextPositions);
        ttt.setContext(getApplicationContext());
        Log.d("Player 1: ", player1.toString());
        Log.d("Player 2: ", player2.toString());

        initializeBoard();
        Player currentPlayer = (player1.getPlayerSymbol().equals("X")) ? player1 : player2;
        if (currentPlayer.isCPU()) ttt.startGame();

        Log.d("ROWSLIST: ", String.valueOf(rowsList.size()));

        //ttt.setPositionsMap(editTextPositions);

    }

    public void initializeBoard() {

        //tableLayout = constraintLayout.findViewById(R.id.gameBoard);
        for (int i = 0; i < boardSize; i++) {
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(rowParams);

            for (int j = 0; j < boardSize; j++) {
                EditText editText = new EditText(this);
                TableRow.LayoutParams params = new TableRow.LayoutParams(163, 180);
                editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

                if (boardSize == 3) {
                    params.setMargins(40, 40, 40, 40);
                    params.width = 250;
                    params.height = 250;
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
                } else {
                    params.setMargins(10, 10, 10, 10);
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                }

                editText.setElevation(30);
                editText.setLayoutParams(params);
                editText.setEms(1);               ; // Text size set to 1
                editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_NULL);
                editText.setBackgroundResource(R.drawable.edittextbg); // Custom background drawable
                editText.setEnabled(true);
                editText.setClickable(true);
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(false);
                editText.setText("");
                editText.setOnClickListener(this);
                editTextPositions.put(editText, new Pair<>(i, j));

                tableRow.addView(editText);
                //tableRow.setOnClickListener(this);
                tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
                editText.setCursorVisible(false);

            }
            rowsList.add(tableRow);
            tableLayout.addView(tableRow);
            tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            //tableLayout.setOnClickListener(this);
        }
    }

    public List<TableRow> getRowsList() {
        return rowsList;
    }

    public Context getContext() {
        return  getApplicationContext();
    }

    @Override
    public void onClick(View view) {
        ttt.startGame(view);
    }
}