package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EnterNames extends AppCompatActivity {

    public static String Player1;
    public static String Player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);

        EditText player_1 = (EditText) findViewById(R.id.player1);
        EditText player_2 = (EditText) findViewById(R.id.player2);


        ImageView arrow = (ImageView) findViewById(R.id.arrow);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EnterNames.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        Button play1 = (Button) findViewById(R.id.play1);
        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player1 = player_1.getText().toString();
                Player2 = player_2.getText().toString();


                if (Player1.equals("") && Player2.equals("")) {
                    Player1 = "Player 1";
                    Player2 = "Player 2";
                } else if (Player1.equals("")) {
                    Player1 = "Player 1";
                } else if (Player2.equals("")) {
                    Player2 = "Player 2";
                }

                Intent intent2 = new Intent(EnterNames.this, TwoPlayers.class);
                startActivity(intent2);
            }
        });
    }
}