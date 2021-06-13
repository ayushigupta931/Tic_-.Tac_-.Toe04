package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SinglePlayer extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private Button reset;
    private Button home;

    //player == 1
    //bot == 0
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};


    //WINNING COMBINATIONS
    // rows : {0, 1, 2}, {3, 4, 5}, {6, 7, 8}
    // columns : {0, 3, 6}, {1, 4, 7}, {2, 5, 8}
    // diagonal : {0, 4, 8}, {2, 4, 6}
    int[][] winningCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int count = 0;

    private TextView player_score;
    private TextView bot_score;
    private TextView status;

    int playerScore = 0;
    int botScore = 0;

    // k = -1 if player wins
    // k = 1 if bot wins
    // k = 0 for tie
    double k;
    int m = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        player_score = (TextView) findViewById(R.id.your_score);
        bot_score = (TextView) findViewById(R.id.botScore);
        status = (TextView) findViewById(R.id.status1);
        reset = (Button) findViewById(R.id.reset1);
        home = (Button) findViewById(R.id.home1);

        for (int i = 0; i < buttons.length; i++) {
            String buttonId = "button_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    m = 1;
                    status.setVisibility(View.INVISIBLE);

                    //button already clicked
                    if (!(((Button) v).getText().toString().equals(""))) {
                        Toast.makeText(SinglePlayer.this, "Button already clicked!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    String ButtonID = v.getResources().getResourceEntryName(v.getId());
                    int gameStatePointer = Integer.parseInt(ButtonID.substring(ButtonID.length() - 1));

                    ((Button) v).setText(ChooseXO.shapePlayer);
                    gameState[gameStatePointer] = 1;
                    ((Button) v).setTextColor(Color.RED);
                    count++;


                    if (checkWinner()) {
                        playAgain();
                        return;
                    }

                    chooseBestMove();

                    count++;
                    if (checkWinner()) {
                        playAgain();
                    }
                }

            });
        }


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k = -2;
                playAgain();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SinglePlayer.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean checkWinner() {

        boolean result = false;
        k = -2;
        for (int[] winningCombination : winningCombinations) {
            if (gameState[winningCombination[0]] == gameState[winningCombination[1]] && gameState[winningCombination[1]] == gameState[winningCombination[2]] && gameState[winningCombination[0]] == 1) {
                result = true;
                k = -1;
                if (m == 1)
                    playerScore += 1;
                break;
            }

            if (gameState[winningCombination[0]] == gameState[winningCombination[1]] && gameState[winningCombination[1]] == gameState[winningCombination[2]] && gameState[winningCombination[0]] == 0) {
                result = true;
                k = 1;
                if (m == 1)
                    botScore += 1;
                break;
            }
        }

        if (count == 9 && k == -2) {
            result = true;
            k = 0;
        }
        return result;
    }

    public void playAgain() {

        status.setVisibility(View.VISIBLE);
        count = 0;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            gameState[i] = 2;
        }

        if (k == -1) {
            status.setText(R.string.youWon);

        } else if (k == 1) {
            status.setText(R.string.botWon);
        } else if (k == 0) {
            status.setText(R.string.tie);
        } else {
            status.setText("");
            playerScore = 0;
            botScore = 0;
        }

        String p_score = "" + playerScore;
        String b_score = "" + botScore;

        player_score.setText(p_score);
        bot_score.setText(b_score);


    }

    public void chooseBestMove() {
        m = 0;
        double bestScore = Double.NEGATIVE_INFINITY;
        int bestMove = 0;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText().toString().equals("")) {

                buttons[i].setText(ChooseXO.shapeBot);
                gameState[i] = 0;
                count += 1;
                double score = minmax(false);
                buttons[i].setText("");
                gameState[i] = 2;
                count -= 1;

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }

            }
        }
        m = 1;
        buttons[bestMove].setText(ChooseXO.shapeBot);
        buttons[bestMove].setTextColor(Color.CYAN);
        gameState[bestMove] = 0;

    }

    public double minmax(boolean ismaximizing) {

        if (checkWinner()) {
            return k * (10 - count);
        }

        if (ismaximizing) {
            double bestScore = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].getText().toString().equals("")) {

                    buttons[i].setText(ChooseXO.shapeBot);
                    gameState[i] = 0;

                    count += 1;
                    double score = minmax(false);
                    buttons[i].setText("");
                    gameState[i] = 2;

                    count -= 1;

                    if (score > bestScore) {
                        bestScore = score;

                    }

                }
            }

            return bestScore;
        } else {

            double bestScore = Double.POSITIVE_INFINITY;
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].getText().toString().equals("")) {
                    buttons[i].setText(ChooseXO.shapePlayer);
                    gameState[i] = 1;
                    count += 1;

                    double score = minmax(true);

                    count -= 1;
                    buttons[i].setText("");
                    gameState[i] = 2;
                    if (score < bestScore) {
                        bestScore = score;

                    }

                }
            }

            return bestScore;
        }

    }

}