package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class Main4Activity extends AppCompatActivity {

    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private boolean resume = false;
    int delay, level;
    int score = 0;
    int location1, location2 = 0;
    String username;
    Button back;
    MyDBHandler handler;
    TextView result;
    Handler mHandler = new Handler();
    Random random = new Random();
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.v(TAG, "New Mole Location!");
            setNewMole();
            mHandler.postDelayed(this, delay*1000);
        }
    };

    private void readyTimer(){
       readyTimer = new CountDownTimer(10000,10000) {
           @Override
           public void onTick(long millisUntilFinished) {
               final Toast toast = Toast.makeText(getApplicationContext(), format("Get Ready In %d seconds",millisUntilFinished/1000), Toast.LENGTH_SHORT);
               toast.show();
               Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
               Handler toastH = new Handler();
               toastH.postDelayed(new Runnable() { //this is to make sure every toast only run for 1 second
                   @Override
                   public void run() {
                       toast.cancel();
                   }
               }, 1000);
           }

           @Override
           public void onFinish() {
               Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT).show();
               Log.v(TAG, "Ready CountDown Complete!");
               readyTimer.cancel();
               setNewMole();
               placeMoleTimer();
               setButtons();
               resume = true;
           }
       };
       readyTimer.start();
    }

    private void setButtons() {
        for (final int x : BUTTON_IDS){
            Button btn = findViewById(x);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    Button click = findViewById(id);
                    doCheck(click);
                    mHandler.removeCallbacks(mRunnable);
                    setNewMole();
                    placeMoleTimer();
                }
            });
        }
    }

    private void placeMoleTimer(){
        mHandler.postDelayed(mRunnable, delay*10000);
    }
    private static final int[] BUTTON_IDS = {
            R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        handler = new MyDBHandler(this);
        result = findViewById(R.id.mText);
        back = findViewById(R.id.back_btn);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        level = bundle.getInt("level");
        delay = Math.abs(level - 11);
        Log.v(TAG, FILENAME+ ": Load level " + level + " for: " + username);
        Log.v(TAG, "Level: "+ level + " Delay: "+ delay + "s");

        readyTimer();
        result.setText(String.valueOf(score));
        Log.v(TAG, "Current User Score: " + score);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserScore();
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }
    private void doCheck(Button checkButton)
    {
       if (checkButton.getText().toString().equals("*")){
           score++;
           Log.v(TAG, "Hit, score added!");
       }
       else {
           if (score > 0){
               score--;
               Log.v(TAG, "Missed, point deducted!");
           }else{
               Log.v(TAG, "Missed Hit!");
           }
       }
        result.setText(valueOf(score));
    }

    public void setNewMole()
    {
        if (level < 6){ // set one random mole
            int randomLocation = random.nextInt(9);
            Button this_btn = findViewById(BUTTON_IDS[randomLocation]);
            Button last_btn = findViewById(BUTTON_IDS[location1]);
            last_btn.setText("O");
            this_btn.setText("*");
            location1 = randomLocation;
        }else{ // set two random mole
            int randomLocation = random.nextInt(9);
            int randomLocation2 = random.nextInt(9);
            while (randomLocation == randomLocation2){ // to make sure two random location is distinct
                randomLocation2 = random.nextInt(9);
            }
            Button last_btn1 = findViewById(BUTTON_IDS[location1]);
            Button last_btn2 = findViewById(BUTTON_IDS[location2]);
            last_btn1.setText("O");
            last_btn2.setText("O");

            Button this_btn = findViewById(BUTTON_IDS[randomLocation]);
            Button this_btn2 = findViewById(BUTTON_IDS[randomLocation2]);
            this_btn.setText("*");
            this_btn2.setText("*");

            location1 = randomLocation;
            location2 = randomLocation2;

        }

    }

    private void updateUserScore()
    {
        Log.v(TAG, FILENAME + ": Update User Score...");
        UserData userData = handler.findUser(username);
        readyTimer.cancel();
        mHandler.removeCallbacks(mRunnable);

        int bestScore = userData.getScores().get(level-1);
        if (bestScore < score){
            userData.getScores().set(level-1,score);
            handler.deleteAccount(username);
            handler.addUser(userData);
        }

        Log.v(TAG, FILENAME + ": Redirect to level select page");
        Intent intent = new Intent(Main4Activity.this, Levels.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        intent.putExtras(bundle);

        startActivity(intent);
    }

}
