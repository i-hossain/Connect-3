package com.ismail.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String colour = "Red";
    boolean isGameActive = true;
    String[] gameState = {"blank", "blank", "blank", "blank", "blank", "blank", "blank", "blank", "blank"};
    int[][] winStates = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int gridNumber = Integer.parseInt(counter.getTag().toString());

        if (gameState[gridNumber] == "blank" && isGameActive) {
            counter.setTranslationY(-1000f);
            if (colour == "Red") {
                counter.setImageResource(R.drawable.red);
                gameState[gridNumber] = colour;
                colour = "Yellow";
            } else {
                counter.setImageResource(R.drawable.yellow);
                gameState[gridNumber] = colour;
                colour = "Red";
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
        }
        checkForWin();
    }

    public void checkForWin() {
        for(int[] winstate : winStates) {
            if ((gameState[winstate[0]] != "blank") &&
                    (gameState[winstate[0]] == gameState[winstate[1]] && gameState[winstate[1]] == gameState[winstate[2]])) {
                // Someone has won
//                isGameActive = false;
//                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
//                winnerMessage.setText(gameState[winstate[0]] + " has won!");
//
//                LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.winnerLayout);
//                winnerLayout.setVisibility(View.VISIBLE);
                GameOverState("win", gameState[winstate[0]]);
            }
            else {
                boolean gameOver = true;
                for (String gamestate : gameState) {
                    if (gamestate == "blank")
                        gameOver = false;
                }

                if (gameOver)
                    GameOverState("draw", "null");
            }
        }
    }

    public void GameOverState(String state, String colour) {
        isGameActive = false;
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

        if (state == "win")
            winnerMessage.setText(colour + " has won!");
        else if (state == "draw")
            winnerMessage.setText("The Game is a draw");

        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.winnerLayout);
        winnerLayout.setVisibility(View.VISIBLE);
    }

    public void playAgain(View view) {
        resetWinnerMessage();
        resetGameState();
        resetColour();
        resetGrid();
    }

    public void resetWinnerMessage() {
        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.winnerLayout);
        winnerLayout.setVisibility(View.INVISIBLE);
    }

    public void resetGameState() {
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = "blank";
        }
        isGameActive = true;
    }

    public void resetColour() {
        colour = "Red";
    }

    public void resetGrid() {
        GridLayout grid = (GridLayout)findViewById(R.id.gridMain);

        for (int i = 0; i < grid.getChildCount(); i++) {
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}