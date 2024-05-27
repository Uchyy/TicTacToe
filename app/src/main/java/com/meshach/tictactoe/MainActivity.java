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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.slider.Slider;
import com.meshach.tictactoe.CPUPlaying.CPUPlay;
import com.meshach.tictactoe.GamePlay.Player;
import com.meshach.tictactoe.GamePlay.TTT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // Define keys for your state variables
    private static final String KEY_BOARD_SIZE = "board_size";
    private static final String KEY_USER_PLAYER = "user_player";
    private static final String KEY_VS_CPU = "vs_cpu";
    private static final String KEY_CURRENT_PLAYER = "current_player";
    private static final String KEY_EDIT_TEXT_POSITIONS = "edit_text_positions";
    private static final String KEY_GAME_STATE = "game_state";

    private GameBoard gameBoard;
    private TableLayout tableLayout;
    private String userPlayer; int boardSize; boolean vsCPU;
    private ConstraintLayout constraintLayout;
    private List<TableRow> rowsList = new ArrayList<>();
    private boolean isPlayerX;
    private Player player1, player2, currentPlayer;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private TTT ttt;
    private String mode = "EASY";
    private Button restart;
    private Slider sliderMode;
    private TextView sliderLabel;

    @Override
    protected void onPause() {
        super.onPause();
        reloadTableLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if (savedInstanceState != null) {
            // Restore state here
            boardSize = savedInstanceState.getInt(KEY_BOARD_SIZE);
            userPlayer = savedInstanceState.getString(KEY_USER_PLAYER);
            vsCPU = savedInstanceState.getBoolean(KEY_VS_CPU);
            currentPlayer = (Player) savedInstanceState.getSerializable(KEY_CURRENT_PLAYER);
            editTextPositions = (HashMap<EditText, Pair<Integer, Integer>>) savedInstanceState.getSerializable(KEY_EDIT_TEXT_POSITIONS);
            ttt.restoreGameState((HashMap<EditText, String>) savedInstanceState.getSerializable(KEY_GAME_STATE));
        } else {
            // Initial setup if no saved state
            Intent intent = getIntent();
            boardSize = intent.getIntExtra("board", 0);
            userPlayer = intent.getStringExtra("player1");
            vsCPU = intent.getBooleanExtra("vsCPU", true);
            String p2 = (userPlayer.equals("X")) ? "O" : "X";
            editTextPositions = new HashMap<>();

            constraintLayout = findViewById(R.id.coordinatorLayout);
            tableLayout = findViewById(R.id.gameBoard);
            gameBoard = new GameBoard();

            player1 = new Player(userPlayer, vsCPU);
            if (vsCPU) {
                player2 = new Player(p2);
            } else {
                player2 = new Player(p2, vsCPU);
            }

            ttt = new TTT(player1, player2, rowsList, editTextPositions);
            ttt.setContext(getApplicationContext());
            tableLayout = constraintLayout.findViewById(R.id.gameBoard);

            initializeBoard();
            currentPlayer = (player1.getPlayerSymbol().equals("X")) ? player1 : player2;
            if (currentPlayer.isCPU()) ttt.startGame(rowsList.get(0).getChildAt(0));

        }

        setupUIComponents();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BOARD_SIZE, boardSize);
        outState.putString(KEY_USER_PLAYER, userPlayer);
        outState.putBoolean(KEY_VS_CPU, vsCPU);
        outState.putSerializable(KEY_CURRENT_PLAYER, (Serializable) currentPlayer);
        outState.putSerializable(KEY_EDIT_TEXT_POSITIONS, (HashMap<EditText, Pair<Integer, Integer>>) editTextPositions);
        outState.putSerializable(KEY_GAME_STATE, (Serializable) ttt.getGameState());
    }

    private void setupUIComponents() {

        sliderMode = findViewById(R.id.sliderMode);
        sliderLabel = findViewById(R.id.labelMode);
        updateSliderLabel(sliderMode.getValue());

        sliderMode.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                updateSliderLabel(value);
            }
        });

        restart = findViewById(R.id.restartBtn);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadTableLayout();
            }
        });
    }
    private void reloadTableLayout() {
        Log.d("Restarting", "Table alyout restarting");
        tableLayout.removeAllViews();
        rowsList.clear();
        editTextPositions.clear();
        initializeBoard();
        ttt.restartGame();
    }

    private void updateSliderLabel(float value) {
        String label;
        if (value == 50) {
            label = "EASY";
        } else if (value == 100) {
            label = "MEDIUM";
        } else if (value == 150) {
            label = "HARD";
        } else {
            label = "";
        }
        sliderLabel.setText(label);
        mode = label;
        Log.d("1> Mode is: ", mode);
        reloadTableLayout();
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
                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        0, // 0dp width
                        TableRow.LayoutParams.WRAP_CONTENT, // wrap_content height
                        1.0f // weight to distribute evenly
                );
                editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
                editText.setLayoutParams(params);
                if (boardSize == 3) {
                    params.setMargins(20, 20, 20, 20);
                    //params.width = 150;
                    //params.height = 150;
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
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

    public String getMode() {
        Log.d("2> Mode is: ", mode);
        return mode;
    }

    public Map<EditText, Pair<Integer, Integer>> getEditTextPositions() {
        return editTextPositions;
    }

    @Override
    public void onClick(View view) {
        ttt.startGame(view);
    }
}