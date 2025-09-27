package com.meshach.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.slider.Slider;
import com.meshach.tictactoe.Classes.GameViewModel;
import com.meshach.tictactoe.Classes.Player;
import com.meshach.tictactoe.GamePlay.GameManager;
import com.meshach.tictactoe.GamePlay.SetEditText;
import com.meshach.tictactoe.GamePlay.TTT;
import com.meshach.tictactoe.helpers.ThemeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // --- Game state ---
    private GameViewModel gameViewModel;
    private TTT ttt;
    private String userPlayer, mode;
    private int boardSize;
    private boolean vsCPU, newRound, isDark;

    // --- Players ---
    private Player player1, player2, currentPlayer, playerX;

    private ConstraintLayout gameBoardIncluded;
    private TableLayout tableLayout;
    private List<TableRow> rowsList = new ArrayList<>();
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private Map<EditText, String> gameState;

    private Slider sliderMode;
    private TextView sliderLabel, oTextView, xTextView, drawTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        addAds();

        // --- Get intent extras ---
        Intent intent = getIntent();
        newRound = intent.getBooleanExtra("newRound", false);
        boardSize = intent.getIntExtra("board", 0);
        userPlayer = intent.getStringExtra("player1");
        vsCPU = intent.getBooleanExtra("vsCPU", true);
        String p2 = (userPlayer.equals("X")) ? "O" : "X";

        // --- Setup ViewModel and positions map ---
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        editTextPositions = new HashMap<>();

        // --- Find views ---
        // --- Board UI ---
        ConstraintLayout gameConstraintLayout = findViewById(R.id.coordinatorLayout);
        gameBoardIncluded = findViewById(R.id.gameBoardIncluded);
        tableLayout = findViewById(R.id.gameBoard);

        // --- Theme ---
        isDark = ThemeUtils.isDark(this);
        gameConstraintLayout.setBackgroundResource(isDark ? R.drawable.app_bg2 : R.drawable.app_white2);

        // --- Setup players ---
        player1 = new Player(userPlayer, vsCPU);
        player2 = vsCPU ? new Player(p2) : new Player(p2, vsCPU);

        // --- Initialize board + ViewModel ---
        initializeBoard();
        initializeGameViewModel();

        // --- Setup TTT engine ---
        ttt = new TTT(player1, player2, this);
        ttt.setContext(getApplicationContext());

        currentPlayer = player1.getPlayerSymbol().equals("X") ? player1 : player2;
        playerX = player1.getPlayerSymbol().equals("X") ? player1 : player2;

        if (currentPlayer.isCPU()) ttt.startGame(rowsList.get(0).getChildAt(0));

        // --- UI components ---
        setupUIComponents();

        // --- Observers ---
        setupObservers();

        // --- Back button confirm exit ---
        setupBackPressedHandler();
    }

    // --- Initialization helpers ---
    private void initializeGameViewModel() {
        gameViewModel.setCurrentPlayer(player1.getPlayerSymbol().equals("X") ? player1 : player2);
        gameViewModel.setEditTextPositions(editTextPositions);
        gameViewModel.setRowsList(rowsList);
        gameViewModel.setMode(mode);
    }

    private void setupUIComponents() {
        sliderMode = findViewById(R.id.sliderMode);
        sliderLabel = findViewById(R.id.labelMode);
        oTextView = findViewById(R.id.otextview);
        xTextView = findViewById(R.id.xtextview);
        drawTextView = findViewById(R.id.draw);

        setupSlider();
        setupRestartButton();
        if (!newRound) resetScores();
        sliderMode.setValue(100);
    }

    private void setupObservers() {
        final Observer<List<TableRow>> nameObserver = tableRows -> {
            for (TableRow row : rowsList) {
                for (int i = 0; i < row.getChildCount(); i++) {
                    if (row.getChildAt(i) instanceof EditText) {
                        EditText editText = (EditText) row.getChildAt(i);
                        setEditTextProperties(editText, editText.getText().toString());
                    }
                }
            }
        };
        gameViewModel.getRowsList().observe(this, nameObserver);

        gameViewModel.getPlayerOWins().observe(this, wins -> oTextView.setText(String.valueOf(wins)));
        gameViewModel.getPlayerXWins().observe(this, wins -> xTextView.setText(String.valueOf(wins)));
        gameViewModel.getDraws().observe(this, drawCount -> drawTextView.setText(String.valueOf(drawCount)));
    }

    private void setupBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            startActivity(new Intent(MainActivity.this, StartUp.class));
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    // --- Board / Game ---
    public void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < boardSize; j++) {
                EditText editText = getEditText();

                editTextPositions.put(editText, new Pair<>(i, j));
                tableRow.addView(editText);
            }

            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            rowsList.add(tableRow);
            tableLayout.addView(tableRow);
        }

        if (!isDark) {
            tableLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    @NonNull
    private EditText getEditText() {
        EditText editText = new EditText(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        editText.setLayoutParams(params);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, (boardSize == 3) ? 40 : 30);
        params.setMargins((boardSize == 3) ? 20 : 10, (boardSize == 3) ? 20 : 10,
                (boardSize == 3) ? 20 : 10, (boardSize == 3) ? 20 : 10);

        editText.setElevation(30);
        editText.setEms(1);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setGravity(Gravity.CENTER);
        editText.setBackgroundResource(R.drawable.edittextbg);
        editText.setEnabled(true);
        editText.setClickable(true);
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setText("");
        editText.setOnClickListener(this);
        return editText;
    }

    private void reloadTableLayout() {
        tableLayout.removeAllViews();
        rowsList.clear();
        editTextPositions.clear();
        initializeBoard();
        gameViewModel.setMode(mode);
        gameViewModel.setCurrentPlayer(playerX);

        new GameManager(getApplicationContext(), this).reset();
        if (playerX.isCPU()) ttt.startGame(rowsList.get(0).getChildAt(0));
    }

    // --- UI Helpers ---
    private void setEditTextProperties(EditText editText, String text) {
        new SetEditText(this).setEditTextProperties(editText, text);
    }

    private void setupSlider() {
        sliderMode.addOnChangeListener((slider, value, fromUser) -> updateSliderLabel(value));
    }

    private void setupRestartButton() {
        // --- Score/Controls ---
        Button restart = findViewById(R.id.restartBtn);
        restart.setOnClickListener(view -> reloadTableLayout());
    }

    private void resetScores() {
        gameViewModel.setDraws(0);
        gameViewModel.setPlayerOWins(0);
        gameViewModel.setPlayerXWins(0);
    }

    private void updateSliderLabel(float value) {
        String label = "";
        if (value == 50) label = "EASY";
        else if (value == 100) label = "MEDIUM";
        else if (value == 150) label = "HARD";

        sliderLabel.setText(label);
        mode = label;
        reloadTableLayout();
    }

    // --- Ads ---
    private void addAds() {
        LinearLayout adContainer = findViewById(R.id.adContainer);
        // --- Ads ---
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.adID));
        adContainer.addView(adView);
        adView.loadAd(new AdRequest.Builder().build());
    }

    // --- Lifecycle ---
    @Override
    public void onClick(View view) {
        ttt.startGame(view);
    }

    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        boolean newRound = intent.getBooleanExtra("newRound", false);
        if (newRound) reloadTableLayout();
    }
}
