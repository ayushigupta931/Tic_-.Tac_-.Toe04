package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseXO extends AppCompatActivity {

    public static String shapePlayer;
    public static String shapeBot;
    public static boolean yesOrNo;
    RadioButton cross;
    RadioButton zero;
    RadioButton yes;
    RadioButton no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_xo);

        cross = (RadioButton) findViewById(R.id.cross);
        zero = (RadioButton) findViewById(R.id.zero);

        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);

        TextView arrow = (TextView) findViewById(R.id.arrow1);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChooseXO.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        TextView play = (TextView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if no option is selected then bot will start
                if(yes.isChecked())
                    yesOrNo = true;
                else
                    yesOrNo = false;

                if (cross.isChecked()) {
                    shapePlayer = "x";
                    shapeBot = "o";
                } else {
                    shapePlayer = "o";
                    shapeBot = "x";
                }


                if (cross.isChecked() || zero.isChecked()) {
                    Intent intent2 = new Intent(ChooseXO.this, SinglePlayer.class);
                    startActivity(intent2);
                } else {
                    //mandatory to select the shape
                    Toast.makeText(ChooseXO.this, "You haven't selected any shape !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}