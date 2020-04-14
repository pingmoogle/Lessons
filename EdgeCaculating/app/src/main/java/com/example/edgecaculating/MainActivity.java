package com.example.edgecaculating;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private Button resetBtn;
    private Button infoBtn;
    private Button hitBtn;
    private SeekBar barMain;
    private TextView scoreTxt;
    private TextView roundTxt;
    private TextView title;
    private int scores = 0;
    private int round = 1;
    private int random = 0;
    private int sum = 0;
    public void checkGuess(){
        AlertDialog.Builder dia = new AlertDialog.Builder(MainActivity.this);
        int difference = Math.abs(random - scores);
        int point = 100 - difference;
        String titleText = "";
        if(difference == 0){
            titleText = "Perfect!";
            point += 100;
        }else if(difference < 5){
            titleText = "You almost had it!";
            if(difference == 1){
                point += 50;
            }
        }else if(difference < 10){
            titleText = "Pretty Good!";
        }else{
            titleText = "Not even close...";
        }
        dia.setTitle(titleText);
        dia.setMessage("You scored " + point + "pts.\n");
        dia.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                random = (int)(1+Math.random()*(100));
                title.setText("Put the Bull's Eye as close as you can to:"+ random);

            }
        });
        dia.show();
        sum += point;
        round += 1;
        scoreTxt.setText("score: "+ sum);
        roundTxt.setText("round:" + round);
    }
    public void newGame(){
        random = (int)(Math.random()*100+1);
        title.setText("Put the Bull's Eye as close as you can to: "+ random);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetBtn = (Button) findViewById(R.id.resBtn);
        infoBtn = (Button) findViewById(R.id.iBtn);
        hitBtn = (Button) findViewById(R.id.hitButton);
        barMain = (SeekBar) findViewById(R.id.seekBar);
        scoreTxt = (TextView) findViewById(R.id.scoreText);
        roundTxt = (TextView) findViewById(R.id.roundText);
        title = (TextView) findViewById(R.id.title);

        newGame();

        barMain.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        scores = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                }
        );
        hitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
                newGame();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum = 0;
                round = 1;
                scoreTxt.setText("score: "+ sum);
                roundTxt.setText("round: " + round);
                newGame();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ruleActivity.class);
                startActivity(intent);
            }
        });

    }

}
