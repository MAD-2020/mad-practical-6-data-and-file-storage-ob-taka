package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RegisterUser extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */


    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    private TextView username, password;
    private Button registerBtn, cancleBtn;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.createBtn);
        cancleBtn = findViewById(R.id.cancelBtn);

        dbHandler = new MyDBHandler(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pw = password.getText().toString();

                if(dbHandler.findUser(name)!= null) {
                    Log.v(TAG, "User already exist during new user creation.");
                    username.setText(null);
                    password.setText(null);
                }
                ArrayList<Integer> levelList = new ArrayList<>();
                ArrayList<Integer> scoreList = new ArrayList<>();

                for(int i = 1; i <= 10; i++){
                    levelList.add(i);
                    scoreList.add(0);
                }

                UserData user = new UserData(name, pw, levelList, scoreList);
                dbHandler.addUser(user);
                Log.v(TAG, FILENAME + ": New user created successfully!");

                Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    protected void onStop() {
        super.onStop();
        finish();
    }
}
