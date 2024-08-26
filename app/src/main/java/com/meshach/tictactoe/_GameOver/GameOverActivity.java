package com.meshach.tictactoe._GameOver;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.meshach.tictactoe.Classes.Player;
import com.meshach.tictactoe.MainActivity;
import com.meshach.tictactoe.R;
import com.meshach.tictactoe.StartUp;

public class GameOverActivity extends AppCompatActivity  implements  View.OnClickListener{

    //private GameViewModel viewModel;
    private Player player;
    private TextView titleTextView;
    private TextView msgTextView;
    private TextView symbolTextView;
    private Button quitBtn;
    private Button againBtn;
    private String playerSymbol, mode;
    private boolean isCPU;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);

        setFinishOnTouchOutside(false);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to prevent the dialog from being cancelled when back button is pressed
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        initializeComponents();

        if (mode.equals("WIN")) {
            modeIsWIN();
        } else {
            modeIsDraw();
        }

    }

    public void initializeComponents() {
        // Do nothing to prevent the dialog from being cancelled when back button is pressed
        titleTextView = findViewById(R.id.textView01);
        msgTextView = findViewById(R.id.textView02);
        symbolTextView = findViewById(R.id.textView03);
        quitBtn = findViewById(R.id.quitBtn);
        againBtn = findViewById(R.id.againBtn);
        symbolTextView = findViewById(R.id.textView03);

        playerSymbol = getIntent().getStringExtra("playerSymbol");
        isCPU = getIntent().getBooleanExtra("isCPU", false);
        onNewIntent(getIntent());

        color = playerSymbol.equals("X") ? R.color.blueX : R.color.yellowO;
        setColour(color);

        quitBtn.setOnClickListener(this);
        againBtn.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent); // Update the intent

        mode = intent.getStringExtra("mode");
        Log.d("GAMEOVER: MODE", mode);
    }

    private void setColour(int color) {

        if (color == R.color.blueX) {
            againBtn.setBackgroundResource(R.drawable.ylwbutton);
            againBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.yellowO));
        } else {
            againBtn.setBackgroundResource(R.drawable.bbuebtn);
            againBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blueX));
        }

        if (color == R.color.blueX) {
            againBtn.setBackgroundResource(R.drawable.ylwbutton);
            againBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.yellowO));
        } else {
            againBtn.setBackgroundResource(R.drawable.bbuebtn);
            againBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blueX));
        }

    }

    private void modeIsDraw() {
        if (mode.equals("DRAW")) {
            titleTextView.setText("YOU TIED!!");
            titleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blueX));
        }

        msgTextView.setVisibility(View.INVISIBLE);

        symbolTextView.setText("XO");
        symbolTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) symbolTextView.getLayoutParams();
        params.horizontalBias = 0.5f;

        againBtn.setBackgroundResource(R.drawable.ylwbutton);

    }

    private void modeIsWIN() {
        if (isCPU) {
            titleTextView.setText("YOU LOST!");
        } else {
            titleTextView.setText("YOU WON!!");
        }

        symbolTextView.setText(playerSymbol);
        symbolTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), color));
        setColour(color);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.quitBtn) {
            // Handle quit button click
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(GameOverActivity.this, StartUp.class);
                boolean reset = true;
                intent.putExtra("resetGame", true);
                Log.d("GAME OVER RESET VALUE: ", Boolean.toString(reset));
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  // Apply the animation
                finish();
            }, 1000);
        } else if (view.getId() == R.id.againBtn) {
            // Handle again button click
            new Handler().postDelayed(() -> {
                Toast.makeText(getApplicationContext(), "New Round?.....", Toast.LENGTH_SHORT).show();
                Intent newRoundIntent = new Intent(GameOverActivity.this, MainActivity.class);
                newRoundIntent.putExtra("newRound", true);
                newRoundIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(newRoundIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  // Apply the animation
                finish();
            }, 2000);
        }
    }


}