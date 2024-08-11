package com.meshach.tictactoe.Classes;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.GameOverActivity;
import com.meshach.tictactoe.GamePlay.SetEditText;
import com.meshach.tictactoe.GameViewModel;
import com.meshach.tictactoe.MyAnimations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrawClass extends AppCompatActivity {

    private GameViewModel viewModel;
    private List<EditText> allCells;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private List<TableRow> rowsList;
    private ViewModelStoreOwner owner;
    private Context context;

    public DrawClass(ViewModelStoreOwner owner) {
        this.owner = owner;

        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);
        editTextPositions = viewModel.getEditTextPositions().getValue();
        rowsList = viewModel.getRowsList().getValue();
        initializeAllCells();
    }

    private void initializeAllCells() {
        allCells = new ArrayList<>();
        EditText editText = null;
        for (TableRow row : rowsList) {
            for (int i = 0; i < row.getChildCount(); i++) {
                if (row.getChildAt(i) instanceof EditText) {
                    editText = (EditText) row.getChildAt(i);
                    allCells.add(editText);
                }
            }
        }

        context = editText.getContext();

    }

    public void setDrawAnim() {

            MyAnimations anim = new MyAnimations();
            SetEditText setEditText = new SetEditText(context );
            Handler handler = new Handler(Looper.getMainLooper());
            int delay = 200; // Delay between animations

            for (int i = 0; i < allCells.size(); i++) {
                final EditText editText = allCells.get(i);
                final String text = editText.getText().toString();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setEditText.setWinningMoves(editText, text);
                    }
                }, i * delay);
            }

        Intent intent = new Intent(context, GameOverActivity.class);
        new Handler().postDelayed(() -> context.startActivity(intent), 2000);


        Log.d("DRAW", "Draw animation set for all cells.");
    }

}
