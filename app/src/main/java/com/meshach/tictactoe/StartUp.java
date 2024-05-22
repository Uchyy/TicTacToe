package com.meshach.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class StartUp extends AppCompatActivity implements View.OnClickListener{

    //HashMap<String, String> userDetails = new HashMap<String, String>();
    private String userPlayer; private int board; private boolean vsCPU;
    private Button buttonX;
    private Button buttonO;
    private Button buttonVsCPU;
    private Button goBack, by3Button, by5Button;
    private Button buttonVsPlayer;
    private LinearLayout linearLayoutBoard;
    private MyAnimations anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
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
            ((Button) btn1).setTextColor(ContextCompat.getColor(this, R.color.darkblue));

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
            anim.fadeOut(linearLayoutBoard);
            //anim.fadeOutActivity(this);
            new Handler().postDelayed(() -> startActivity(intent), 1000);
        }

    }

}