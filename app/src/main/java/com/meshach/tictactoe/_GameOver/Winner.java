package com.meshach.tictactoe._GameOver;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TableRow;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meshach.tictactoe.Classes.Player;
import com.meshach.tictactoe.GamePlay.SetEditText;
import com.meshach.tictactoe.Classes.GameViewModel;
import com.meshach.tictactoe.MyAnimations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Winner {

    private String playerSymbol;
    private String winningSegment;
    private EditText currentEdittext;
    private GameViewModel viewModel;
    private Map<EditText, Pair<Integer, Integer>> editTextPositions;
    private  List <EditText> winningMoves;
    private  List<TableRow> rowsList;
    private Context context;
    private Player player;


    public Winner(String playerSymbol, String winningSegment, ViewModelStoreOwner owner) {
        this.playerSymbol = playerSymbol;
        this.winningSegment = winningSegment;

        viewModel = new ViewModelProvider(owner).get(GameViewModel.class);
        editTextPositions = viewModel.getEditTextPositions().getValue();
        Log.d("PAIRPOS SIZE FROM WINNER.CLASS: ", String.valueOf(editTextPositions.size()));

        rowsList = viewModel.getRowsList().getValue();
        this.currentEdittext = viewModel.getCurrentEditText().getValue();
        Log.d("ROWLIST SIZE FROM WINNER.CLASS: ", String.valueOf(rowsList.size()));

        player = viewModel.getCurrentPlayer().getValue();


    }

    private List<EditText> getWinningList () {
        return winningMoves;
    }

    private void setWinningList() {
        winningMoves = new ArrayList<>();
        EditText curEditText = viewModel.getCurrentEditText().getValue();
        context = curEditText.getContext();
        String text = curEditText.getText().toString();
        Pair<Integer, Integer> pair = editTextPositions.get(curEditText);
        int size = rowsList.size() - 1;

        switch (winningSegment) {
            case "No winning line":
                Log.d("WINNER LINE", "NO WINNING LINE" );
                return;
            case "ROW":
                int row = pair.first;
                TableRow tableRow = rowsList.get(row);

                for (int i = 0; i < tableRow.getChildCount(); i++) {
                    if (tableRow.getChildAt(i) instanceof EditText) {
                        EditText editText = (EditText) tableRow.getChildAt(i);
                        if (editText.getText().toString().equals(text)) winningMoves.add(editText);
                    }
                }
                break;

            case "COL":
                int col = pair.second;

                for (TableRow tr : rowsList) {
                    if (tr.getChildAt(col) instanceof EditText) {
                        EditText editText = (EditText) tr.getChildAt(col);
                        if (editText.getText().toString().equals(text)) winningMoves.add(editText);
                    }
                }
                break;

            case "MAINDIAGONAL":
                int iMain = pair.first;
                int jMain = pair.second;

                int min = Math.min(iMain, jMain);
                jMain = jMain - min;
                iMain = iMain - min;

                while (iMain <= size && jMain <= size) {
                    TableRow tr = rowsList.get(iMain);
                    if (tr.getChildAt(jMain) instanceof EditText) {
                        EditText editText = (EditText) tr.getChildAt(jMain);
                        if (editText.getText().toString().equals(text)) winningMoves.add(editText);
                    }

                    iMain++; jMain++;
                }
                break;

            case "SECDIAGONAL":

                int iSec = pair.first;
                int jSec = pair.second;
                int total = iSec + jSec;

                if (total == size) {
                    iSec = 0;
                    jSec = size;
                } else if (total < size) {
                    iSec = 0;
                    jSec = total;
                } else {
                    iSec = total - size;
                    jSec = size;
                }

                Log.d("iSEC: ", String.valueOf(iSec));
                Log.d("iJEC: ", String.valueOf(jSec));

                while (iSec <= size && jSec >= 0) {
                    TableRow tr = rowsList.get(iSec);
                    if (tr.getChildAt(jSec) instanceof EditText) {
                        EditText editText = (EditText) tr.getChildAt(jSec);

                        if (editText.getText().toString().equals(text)) {
                            winningMoves.add(editText);
                        }
                    }
                    iSec++;
                    jSec--;
                }
                break;

        }
    }

    public void setWinningAnim() {
        setWinningList();
        SetEditText setEditText = new SetEditText(currentEdittext.getContext());
        Log.d("WIINING-LIST SIZE: ", String.valueOf(getWinningList().size()));
        MyAnimations anim = new MyAnimations();

        Handler handler = new Handler(Looper.getMainLooper()); // Ensure the handler runs on the main thread
        int delay = 350; // Initial delay in milliseconds

        for (int i = 0; i < winningMoves.size(); i++) {
            final EditText editText = winningMoves.get(i);
            final String text = editText.getText().toString();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //  anim.scaleAnimation(editText);
                    setEditText.setWinningMoves(editText, text);
                }
            }, i * delay); // Multiply delay by i to stagger updates
        }

        Log.d("WINNER PLAYER: ", player.toString());

        Intent intent = new Intent(context, GameOverActivity.class);
        intent.putExtra("playerSymbol", player.getPlayerSymbol());
        intent.putExtra("isCPU", player.isCPU());
        intent.putExtra("mode", "WIN");
        new Handler().postDelayed(() -> context.startActivity(intent), 2000);

    }

    @Override
    public String toString() {
        return "Winner{" +
                " Player = " + playerSymbol +
                ", winningLine = " + winningSegment +
                "}";
    }

}
