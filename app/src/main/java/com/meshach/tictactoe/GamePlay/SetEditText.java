package com.meshach.tictactoe.GamePlay;

import android.content.Context;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.meshach.tictactoe.R;

public class SetEditText {

    private Context context;

    public SetEditText (Context context) {
        this.context = context;
    }

    public void setEditTextProperties(EditText editText, String text) {
        editText.setText(text);
        int color = text.equals("X") ? R.color.blueX : R.color.yellowO;
        editText.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setWinningMoves(EditText editText, String text) {
        //editText.setText(text);
        editText.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
    }



}
