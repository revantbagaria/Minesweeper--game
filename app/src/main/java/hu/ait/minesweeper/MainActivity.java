package hu.ait.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;

import hu.ait.minesweeper.model.MinesweeperModel;
import hu.ait.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linlayout;
    public Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linlayout = (LinearLayout) findViewById(R.id.activity_main);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        final MinesweeperView gameView = (MinesweeperView) findViewById(R.id.gameView);

        gameView.resetGame();

        Button btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gameView.resetGame();
            }
        });

        ToggleButton btnFlag = (ToggleButton) findViewById(R.id.btnFlag);
        btnFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    MinesweeperModel.flag=1;

                } else {
                    // The toggle is disabled
                    MinesweeperModel.flag=0;

                }
            }
        });

    }

    public void DisplayMessage(String s){
        Snackbar.make(linlayout, s, Snackbar.LENGTH_LONG).show();
    }
    public void StartTimer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void StopTimer(){
        chronometer.stop();
    }

}
