package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SinglePlayer extends AppCompatActivity {

    private TextView[] buttons = new TextView[9];
    private TextView reset;
    private TextView home;
    private TextView playyAgain;
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
        reset = (TextView) findViewById(R.id.reset1);
        home = (TextView) findViewById(R.id.home1);
        playyAgain = (TextView) findViewById(R.id.playAgain);

        for (int i = 0; i < buttons.length; i++) {
            String buttonId = "button_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = (TextView) findViewById(resourceId);
            buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    m = 1;


                    //button already clicked
                    if (!(((TextView) v).getText().toString().equals(""))) {
                        Toast.makeText(SinglePlayer.this, "Button already clicked!", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    String ButtonID = v.getResources().getResourceEntryName(v.getId());
                    int gameStatePointer = Integer.parseInt(ButtonID.substring(ButtonID.length() - 1));

                    if(playyAgain.getVisibility() != View.VISIBLE) {
                        ((TextView) v).setText(ChooseXO.shapePlayer);
                        gameState[gameStatePointer] = 1;
                        ((TextView) v).setTextColor(Color.MAGENTA);
                        ((TextView) v).setShadowLayer(20, 0, 0, Color.MAGENTA);

                        count++;
                    }

                    if (checkWinner()) {
                        status.setVisibility(View.VISIBLE);
                        if (k == -1) {
                            status.setText(R.string.youWon);

                        } else if (k == 1) {
                            status.setText(R.string.botWon);
                        } else if (k == 0) {
                            status.setText(R.string.tie);
                        }

                        String p_score = "" + playerScore;
                        String b_score = "" + botScore;

                        player_score.setText(p_score);
                        bot_score.setText(b_score);

                        playyAgain.setVisibility(View.VISIBLE);
                        playyAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(k!=-2){
                                    status.setVisibility(View.INVISIBLE);
                                    playAgain();
                                }
                            }
                        });

                        return;
                    }

                    if(playyAgain.getVisibility() != View.VISIBLE)
                    chooseBestMove();

                    count++;
                    if (checkWinner()) {

                        status.setVisibility(View.VISIBLE);
                        if (k == -1) {
                            status.setText(R.string.youWon);

                        } else if (k == 1) {
                            status.setText(R.string.botWon);
                        } else if (k == 0) {
                            status.setText(R.string.tie);
                        }

                        String p_score = "" + playerScore;
                        String b_score = "" + botScore;

                        player_score.setText(p_score);
                        bot_score.setText(b_score);

                        playyAgain.setVisibility(View.VISIBLE);
                        playyAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(k!=-2){
                                    status.setVisibility(View.INVISIBLE);
                                    playAgain();
                                }
                            }
                        });
                    }
                }

            });
        }

        if(!(ChooseXO.yesOrNo)){
            buttons[0].setText(ChooseXO.shapeBot);
            buttons[0].setTextColor(Color.CYAN);
            buttons[0].setShadowLayer(20,0,0,Color.CYAN);
            gameState[0] = 0;
            count++;
        }


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status.setText("");
                playerScore = 0;
                botScore = 0;

                String p_score = "" + playerScore;
                String b_score = "" + botScore;

                player_score.setText(p_score);
                bot_score.setText(b_score);
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
        if(playyAgain.getVisibility() != View.VISIBLE) {
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
        }}
        return result;
    }

    public void playAgain() {
        playyAgain.setVisibility(View.INVISIBLE);

        count = 0;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            gameState[i] = 2;
        }
        k = -2;
        if(!(ChooseXO.yesOrNo) && playyAgain.getVisibility() != View.VISIBLE){
            buttons[0].setText(ChooseXO.shapeBot);
            buttons[0].setTextColor(Color.CYAN);
            buttons[0].setShadowLayer(20,0,0,Color.CYAN);
            gameState[0] = 0;
            count++;
        }

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
        buttons[bestMove].setShadowLayer(20,0,0,Color.CYAN);
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