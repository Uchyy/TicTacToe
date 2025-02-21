package com.meshach.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowMetrics;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.slider.Slider;
import com.meshach.tictactoe.Classes.GameViewModel;
import com.meshach.tictactoe.Classes.Player;
import com.meshach.tictactoe.GamePlay.GameManager;
import com.meshach.tictactoe.GamePlay.SetEditText;
import com.meshach.tictactoe.GamePlay.TTT;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private GameViewModel gameViewModel;
    private TableLayout tableLayout;
    private String userPlayer; int boardSize; boolean vsCPU;
    private ConstraintLayout constraintLayout;
    LinearLayout adContainerView;
    private List<TableRow> rowsList = new ArrayList<>();
    private boolean isPlayerX;
    private Player player1, player2, currentPlayer, playerX;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private TTT ttt;
    private String mode;
    private Button restart;
    private Slider sliderMode;
    private TextView sliderLabel;
    private TextView oTextView;
    private TextView xTextView;
    private TextView drawTextView;
    private  Map<EditText, String> gameState;
    private boolean newRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*List<String> testDeviceIds = Arrays.asList("71B02F0BEA066F8F6CDBB05A95633D08");
        RequestConfiguration configuration = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build();
        MobileAds.setRequestConfiguration(configuration);*/
        MobileAds.initialize(this, initializationStatus -> {
            runAd(); // Ensure ads only run after initialization
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initial setup if no saved state
        Intent intent = getIntent();
        newRound = intent.getBooleanExtra("newRound", false);
        boardSize = intent.getIntExtra("board", 0);
        userPlayer = intent.getStringExtra("player1");
        vsCPU = intent.getBooleanExtra("vsCPU", true);
        String p2 = (userPlayer.equals("X")) ? "O" : "X";


        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        editTextPositions = new HashMap<>();

        constraintLayout = findViewById(R.id.coordinatorLayout);
        tableLayout = findViewById(R.id.gameBoard);

        player1 = new Player(userPlayer, vsCPU);
        if (vsCPU) {
            player2 = new Player(p2);
        } else {
            player2 = new Player(p2, vsCPU);
        }

        tableLayout = constraintLayout.findViewById(R.id.gameBoard);

        initializeBoard();
        initializeGameViewModel();

        ttt = new TTT(player1, player2, (this));
        ttt.setContext(getApplicationContext());

        currentPlayer = (player1.getPlayerSymbol().equals("X")) ? player1 : player2;
        playerX = (player1.getPlayerSymbol().equals("X")) ? player1 : player2;;
        if (currentPlayer.isCPU()) ttt.startGame(rowsList.get(0).getChildAt(0));

        setupUIComponents();

        final Observer <List<TableRow>> nameObserver = new Observer<List<TableRow>>() {
            @Override
            public void onChanged(List<TableRow> tableRows) {
                for (TableRow tableRow : rowsList) {
                    EditText editText = null;
                    String editTextText = null;

                    for (int i = 0; i < tableRow.getChildCount(); i++) {
                        if (tableRow.getChildAt(i) instanceof EditText) {
                            editText = (EditText) tableRow.getChildAt(i);
                            editTextText = editText.getText().toString();
                            setEditTextProperties (editText, editTextText);
                        }
                    }
                }
            }

        };  gameViewModel.getRowsList().observe(this, nameObserver);

        gameViewModel.getPlayerOWins().observe(this, wins -> {
            oTextView.setText(String.valueOf(wins));
        });

        // Observe the LiveData for player X wins
        gameViewModel.getPlayerXWins().observe(this, wins -> {
            xTextView.setText(String.valueOf(wins));
        });

        // Observe the LiveData for draws
        gameViewModel.getDraws().observe(this, drawCount -> {
            drawTextView.setText(String.valueOf(drawCount));
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Navigate to the target activity on confirmation
                            Intent intent = new Intent(MainActivity.this, StartUp.class);
                            startActivity(intent);
                            finish();  // Optional: Close the current activity
                        })
                        .setNegativeButton("No", null)  // Do nothing on "No"
                        .show();
            }
        });

    }

    private void initializeGameViewModel() {
        gameViewModel.setCurrentPlayer(player1.getPlayerSymbol().equals("X") ? player1 : player2);
        gameViewModel.setEditTextPositions(editTextPositions);
        gameViewModel.setRowsList(rowsList);
        gameViewModel.setMode(mode);
    }

    private void setEditTextProperties(EditText editText, String text) {
        SetEditText setEditText = new SetEditText(this);
        setEditText.setEditTextProperties(editText, text);
    }

    private void setupUIComponents() {
        sliderMode = findViewById(R.id.sliderMode);
        sliderLabel = findViewById(R.id.labelMode);
        oTextView = findViewById(R.id.otextview);
        xTextView = findViewById(R.id.xtextview);
        drawTextView = findViewById(R.id.draw);

        setupSlider();
        setupRestartButton();
        if (!newRound) {
            resetScores();
        }

        sliderMode.setValue(100);
    }

    private void setupSlider() {
        //updateSliderLabel(10);
        sliderMode.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                updateSliderLabel(sliderMode.getValue());
            }
        });
    }

    private void setupRestartButton() {
        restart = findViewById(R.id.restartBtn);
        restart.setOnClickListener(view -> reloadTableLayout());
    }

    private void resetScores() {
        gameViewModel.setDraws(0);
        gameViewModel.setPlayerOWins(0);
        gameViewModel.setPlayerXWins(0);
    }


    private void reloadTableLayout() {
        //.d("Restarting", "Table alyout restarting");
        tableLayout.removeAllViews();
        rowsList.clear();
        editTextPositions.clear();
        initializeBoard();
        gameViewModel.setMode(mode);
        gameViewModel.setCurrentPlayer(playerX);

        GameManager manager = new GameManager(getApplicationContext(), this);
        manager.reset();
        //ttt.restartGame();
        if (playerX.isCPU()) ttt.startGame(rowsList.get(0).getChildAt(0));
    }

    private void updateSliderLabel(float value) {
        String label = "";
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
        reloadTableLayout();
    }

    public void initializeBoard() {
        //.makeText(getApplicationContext(), " Initializing Board...", //.LENGTH_SHORT).show();

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
        }
    }

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
        setIntent(intent); // Update the intent

        boolean newRound = intent.getBooleanExtra("newRound", false);

        if (newRound) {
            reloadTableLayout();
        }

    }

    public AdSize getAdSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int adWidthPixels = displayMetrics.widthPixels;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
            adWidthPixels = windowMetrics.getBounds().width();
        }

        float density = displayMetrics.density;
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    public void runAd() {

        adContainerView = findViewById(R.id.adContainer);
        AdView adView = new AdView(adContainerView.getContext());
        adView.setAdUnitId(getString(R.string.testadID));
        adView.setAdSize(getAdSize());

    // Replace ad container with new ad view.

        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.d("AdInfo", "Ad Clicked");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e("AdError", "Ad failed to load: ");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("AdInfo", "Ad successfully loaded");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked();
            }
        });
    }

}