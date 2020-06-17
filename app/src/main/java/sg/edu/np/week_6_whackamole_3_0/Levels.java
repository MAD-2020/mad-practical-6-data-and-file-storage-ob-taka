package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static java.lang.String.format;

public class Levels extends AppCompatActivity {
    /* Hint:
        1. This displays the available levels from 1 to 10 to the user.
        2. The different levels makes use of the recyclerView and displays the highest score
           that corresponds to the different levels.
        3. Selection of the levels will load relevant Whack-A-Mole game.
        4. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        5. There is an option return to the login page.
     */
    private static final String FILENAME = "Main3Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    private String username;
    RecyclerView recyclerView;
    MyDBHandler handler;
    CustomScoreAdaptor mAdaptor;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);

        backBtn = findViewById(R.id.backBtn);
        handler = new MyDBHandler(this);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        UserData userData = handler.findUser(username);
        Log.v(TAG, FILENAME + ": Show level for User: "+ username);

        initRV(userData);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Levels.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRV(UserData x) {
        recyclerView = findViewById(R.id.mRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdaptor = new CustomScoreAdaptor(x, this);

        mAdaptor.setOnItemClickListener(new CustomScoreAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                nextLevel(username, position+1);
            }
        });

    }

    private void nextLevel(final String _username, final int i) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Whack-A-Mole Incoming!");
        builder.setMessage(format("Would you like to challenge Level %d ?",i));
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                next(_username, i);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private  void next(String username, int x){
        Intent intent = new Intent(Levels.this, Main4Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        bundle.putInt("level", x);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
