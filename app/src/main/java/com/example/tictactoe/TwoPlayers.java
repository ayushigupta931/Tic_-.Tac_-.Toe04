package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPlayers extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private Button reset;
    private Button home;

    //player 1 == 1
    //player 2 == 0
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //p1 == true
    //p2 == false
    boolean activePlayer = true;

    //WINNING COMBINATIONS
    // rows : {0, 1, 2}, {3, 4, 5}, {6, 7, 8}
    // columns : {0, 3, 6}, {1, 4, 7}, {2, 5, 8}
    // diagonal : {0, 4, 8}, {2, 4, 6}
    int[][] winningCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int count = 0;

    private TextView player1;
    private TextView player2;
    private TextView player_1_score;
    private TextView player_2_score;
    private TextView status;

    int player1Score = 0;
    int player2Score = 0;

    // k=1 if player 1 wins
    // k=0 if player 2 wins
    // k=2 for tie
    int k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players);

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);
        player_1_score = (TextView) findViewById(R.id.player_1_score);
        player_2_score = (TextView) findViewById(R.id.player_2_score);
        status = (TextView) findViewById(R.id.status);
        reset = (Button) findViewById(R.id.reset);
        home = (Button) findViewById(R.id.home);

        player1.setText(EnterNames.Player1);
        player2.setText(EnterNames.Player2);

        int i;
        for (i = 0; i < buttons.length; i++) {
            String buttonId = "button_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    status.setVisibility(View.INVISIBLE);

                    //button already clicked
                    if (!(((Button) v).getText().toString().equals(""))) {
                        Toast.makeText(TwoPlayers.this, "Button already clicked!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String ButtonID = v.getResources().getResourceEntryName(v.getId());
                        int gameStatePointer = Integer.parseInt(ButtonID.substring(ButtonID.length() - 1));
                        if (activePlayer) {
                            ((Button) v).setText("x");
                            ((Button) v).setTextColor(Color.RED);
                            gameState[gameStatePointer] = 1;
                        } else {
                            ((Button) v).setText("o");
                            ((Button) v).setTextColor(Color.CYAN);
                            gameState[gameStatePointer] = 0;
                        }
                    }
                    count++;

                    if (checkWinner()) {
                        playAgain();
                    } else if (count == 9) {
                        k = 2;
                        playAgain();
                    } else
                        activePlayer = !activePlayer;
                }

            });
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k = -1;
                playAgain();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoPlayers.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean checkWinner() {

        k = -1;
        boolean result = false;
        for (int[] winningCombination : winningCombinations) {
            if (gameState[winningCombination[0]] == gameState[winningCombination[1]] && gameState[winningCombination[1]] == gameState[winningCombination[2]] && gameState[winningCombination[0]] == 1) {
                k = 1;
                player1Score += 1;
                result = true;
                break;
            }
            if (gameState[winningCombination[0]] == gameState[winningCombination[1]] && gameState[winningCombination[1]] == gameState[winningCombination[2]] && gameState[winningCombination[0]] == 0) {
                k = 0;
                player2Score += 1;
                result = true;
                break;
            }
        }
        return result;
    }

    public void playAgain() {
        status.setVisibility(View.VISIBLE);
        activePlayer = true;
        count = 0;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            gameState[i] = 2;
        }

        if (k == 1) {
            status.setText((EnterNames.Player1).toUpperCase() + " won the game!");

        } else if (k == 0) {
            status.setText((EnterNames.Player2).toUpperCase() + " won the game!");

        } else if (k == 2) {
            status.setText(R.string.tie);
        } else {
            status.setText("");
            player1Score = 0;
            player2Score = 0;
        }

        String p1_score = "" + player1Score;
        String p2_score = "" + player2Score;

        player_1_score.setText(p1_score);
        player_2_score.setText(p2_score);
    }

}