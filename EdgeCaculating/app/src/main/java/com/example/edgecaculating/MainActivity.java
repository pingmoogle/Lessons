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

// 以上引用了需要的packages

public class MainActivity extends AppCompatActivity {
    private Button resetBtn;
    private Button infoBtn;
    private Button hitBtn;
    private SeekBar barMain;
    private TextView scoreTxt;
    private TextView roundTxt;
    private TextView title;
    // 以上定义了稍后需要使用的控件变量，在onCreate函数内绑定xml中的控件
    private int scores = 0;
    // 变量 score 记录了分数
    private int round = 1;
    // 变量 round 记录了轮次
    private int random = 0;
    // 变量 random 记录了每次生成的随机数
    private int sum = 0;
    // 变量 sum 统计了得分总和
    // 以上定义了全局变量
    public void checkGuess() {
        // cheakGuess 函数，比较random与滑出来的数字，增加相应分数
        // 弹出alertdialog窗体 给出相应提示
        AlertDialog.Builder dia = new AlertDialog.Builder(MainActivity.this);
        int difference = Math.abs(random - scores);
        int point = 100 - difference;
        String titleText = "";
        if (difference == 0) {
            titleText = "Perfect!";
            point += 100;
        } else if (difference < 5) {
            titleText = "You almost had it!";
            if (difference == 1) {
                point += 50;
            }
        } else if (difference < 10) {
            titleText = "Pretty Good!";
        } else {
            titleText = "Not even close...";
        }
        dia.setTitle(titleText);
        dia.setMessage("You scored " + point + "pts.\n");
        dia.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                random = (int) (1 + Math.random() * (100));
                title.setText("Put the Bull's Eye as close as you can to:" + random);

            }
        });
        dia.show();
        sum += point;
        round += 1;
        scoreTxt.setText("score: " + sum);
        roundTxt.setText("round:" + round);
        // 每一轮滑数字结束后，更新界面上轮数和分数的信息
    }

    public void newGame() {
        // newGame 函数，用来新产生一次game
        random = (int) (Math.random() * 100 + 1);
        title.setText("Put the Bull's Eye as close as you can to: " + random);
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
        // 以上绑定了xml控件与java变量的关系

        newGame();
        // 开始一轮新游戏

        barMain.setOnSeekBarChangeListener(
                // 监听滑块事件
                new SeekBar.OnSeekBarChangeListener() {
                    // 当滑块变化时，将滑块对应数字更新给变量scores
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
            // 监听hitme按钮
            @Override
            public void onClick(View v) {
                // 按下hitme按钮后，判断分数，之后进行下一轮游戏
                checkGuess();
                newGame();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            // 监听reset按钮
            @Override
            public void onClick(View v) {
                // 按下reset按钮后，将总分和轮数清零，开始新一轮游戏
                sum = 0;
                round = 1;
                scoreTxt.setText("score: " + sum);
                roundTxt.setText("round: " + round);
                newGame();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            // 监听info按钮
            @Override
            public void onClick(View v) {
                // 当按下info按钮后，显示游戏帮助窗体
                Intent intent = new Intent(MainActivity.this, ruleActivity.class);
                startActivity(intent);
            }
        });

    }

}
