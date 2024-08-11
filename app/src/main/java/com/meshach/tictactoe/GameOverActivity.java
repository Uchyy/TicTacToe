package com.meshach.tictactoe;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.meshach.tictactoe.Classes.Player;

public class GameOverActivity extends AppCompatActivity {

    private GameViewModel viewModel;
    private Player player;
    private TextView titleTextView;
    private TextView msgTextView;
    private TextView symbolTextView;
    private Button quitBtn;  
    private Button againBtn;
    private String playerSymbol;

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

       /* AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.CustomDialogTheme));
        builder.setView(R.layout.activity_game_over);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);*/


        initializeComponents();

    }

    public void initializeComponents() {
        // Do nothing to prevent the dialog from being cancelled when back button is pressed
        titleTextView = findViewById(R.id.textView01);
        msgTextView = findViewById(R.id.textView02);
        symbolTextView = findViewById(R.id.textView03);
        quitBtn = findViewById(R.id.quitBtn);
        againBtn = findViewById(R.id.againBtn);
        symbolTextView = findViewById(R.id.textView03);

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        //player = viewModel.getCurrentPlayer().getValue();
        //Log.d("GAME OVER PLAYER: ", player.toString());
        //playerSymbol = player.getPlayerSymbol();
        playerSymbol = getIntent().getStringExtra("playerSymbol");
        boolean isCPU = getIntent().getBooleanExtra("isCPU", false);

        if (isCPU) {
            titleTextView.setText("YOU LOST!");
        } else {
            titleTextView.setText("YOU WON!!");
        }

        int color = playerSymbol.equals("X") ? R.color.blueX : R.color.yellowO;
        symbolTextView.setText(playerSymbol);
        symbolTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), color));


        if (color == R.color.blueX) {
            againBtn.setBackgroundResource(R.drawable.ylwbutton);
            againBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.yellowO));
        } else {
            againBtn.setBackgroundResource(R.drawable.bbuebtn);
            againBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), color));
        }

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(GameOverActivity.this, StartUp.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  // Apply the animation
                    finish();
                }, 2000);
            }
        });

        againBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                    intent.putExtra("restart", true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  // Apply the animation
                    finish();
                }, 2000);
            }
        });

    }
}