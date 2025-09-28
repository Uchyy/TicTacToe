package com.meshach.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.meshach.tictactoe.Classes.GameViewModel;
import com.meshach.tictactoe.GamePlay.GameManager;
import com.meshach.tictactoe.helpers.ThemeUtils;

import java.util.HashMap;

public class StartUp extends AppCompatActivity implements View.OnClickListener{

    //HashMap<String, String> userDetails = new HashMap<String, String>();
    private String userPlayer; private int board; private boolean vsCPU;
    private Button buttonX;
    private Button buttonO;
    private Button buttonVsCPU;
    private Button goBack, by3Button, by5Button;
    private Button buttonVsPlayer;
    private ConstraintLayout linearLayoutBoard;
    private MyAnimations anim;
    private Boolean resetGame, newRound;
    private AdView adView, bannerAd;
    private ImageButton bulbBtn;
    private FrameLayout startUpMainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        startUpMainLayout = findViewById(R.id.startUpMainLayout);

        bulbBtn = findViewById(R.id.bulbBtn);
        boolean isDark = ThemeUtils.isDark(this);

        startUpMainLayout.setBackgroundResource(isDark ? R.drawable.app_bg2 : R.drawable.app_white2);
        bulbBtn.setBackgroundResource(isDark ? R.color.color_primary_dark : android.R.color.white);
        bulbBtn.setImageResource(isDark ? R.drawable.icons8_light_100 : R.drawable.bulb);

        bulbBtn.setOnClickListener(v -> {
            boolean nowDark = !ThemeUtils.isDark(this);
            ThemeUtils.setDark(this, nowDark);

            startUpMainLayout.setBackgroundResource(nowDark ? R.drawable.app_bg2 : R.drawable.app_white2);
            bulbBtn.setBackgroundResource(nowDark ? R.color.color_primary_dark : android.R.color.white);
            bulbBtn.setImageResource(nowDark ? R.drawable.icons8_light_100 : R.drawable.bulb);
        });

        //addAds();

        Intent intent = getIntent();
        resetGame = intent.getBooleanExtra("resetGame", false);
        Log.d("START-UP RESET VALUE: ", Boolean.toString(resetGame));
        if (resetGame) {
            GameManager manager = new GameManager(getApplicationContext(), this);
            manager.reset();
        }

        GameViewModel gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        gameViewModel.resetGame();

        newRound = intent.getBooleanExtra("newRound", false);
        anim = new MyAnimations();
        userPlayer = "O";

        buttonO = findViewById(R.id.buttonO);
        buttonO.setOnClickListener(this);
        buttonX = findViewById(R.id.buttonX);
        buttonX.setOnClickListener(this);
        buttonVsCPU = findViewById(R.id.vsCPu);
        buttonVsCPU.setOnClickListener(this);

        linearLayoutBoard = findViewById(R.id.viewChooseBoard);

        buttonVsPlayer = findViewById(R.id.vsPlayer);
        buttonVsPlayer.setOnClickListener(this);
        goBack = linearLayoutBoard.findViewById(R.id.backBtn);
        goBack.setOnClickListener(this);
        by3Button =  linearLayoutBoard.findViewById(R.id.by3Button);
        by3Button.setOnClickListener(this);
        by5Button =  linearLayoutBoard.findViewById(R.id.by5Button);
        by5Button.setOnClickListener(this);

        linearLayoutBoard.setVisibility(View.INVISIBLE);

    }

    private void setPlayer(View btn1, View btn2) {

        if (btn1 instanceof Button && btn2 instanceof Button) {
            btn1.setBackgroundResource(R.drawable.thumb);
            ((Button) btn1).setTextColor(ContextCompat.getColor(this, R.color.color_lightPrimary_dark));

            btn2.setBackgroundResource(R.drawable.track);
            ((Button) btn2).setTextColor(ContextCompat.getColor(this, R.color.gray));

            userPlayer = ((Button) btn1).getText().toString() ;

            Toast.makeText(this, "You are Player "+userPlayer+"!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(StartUp.this, MainActivity.class);
        int id = view.getId();

        if (id == R.id.buttonO) {
            setPlayer(view, buttonX);
            userPlayer = "O";
        }
        else if (id == R.id.buttonX) {
            setPlayer(view, buttonO);
            userPlayer = "X";
        }
        else if (id == R.id.vsCPu) {
            vsCPU = true;
            anim.fadeIn(linearLayoutBoard);
        }
        else if (id == R.id.vsPlayer) {
            vsCPU = false;
            anim.fadeIn(linearLayoutBoard);
        }
        else if (id == R.id.backBtn) {
            anim.fadeOut(linearLayoutBoard);
        }
        else if (id == R.id.by3Button ||  id == R.id.by5Button) {

            if (id == R.id.by5Button) {
                board = 5;
                //startActivity(intent);
            } else if (id == R.id.by3Button) {
                board = 3;
                //startActivity(intent);
            }

            intent.putExtra("player1", userPlayer);
            intent.putExtra("board", board);
            intent.putExtra("vsCPU", vsCPU);
            intent.putExtra("resetGame", resetGame);
            intent.putExtra("newRound", newRound);
            anim.fadeOut(linearLayoutBoard);
            //anim.fadeOutActivity(this);
            new Handler().postDelayed(() -> startActivity(intent), 1000);
        }

    }

}