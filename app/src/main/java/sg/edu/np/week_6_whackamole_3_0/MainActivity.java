package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */

    private EditText username, passowrd;
    private Button login;
    private TextView register;
    MyDBHandler dbHandler;
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        passowrd = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.loginBtn);
        dbHandler = new MyDBHandler(this);


    }

    public void onClick(View v){
        String name = username.getText().toString();
        String pw = passowrd.getText().toString();

        Log.v(TAG, FILENAME + ": Logging in with: " + username + ": " + pw);

        if (!isValidUser(name, pw)){
            Log.v(TAG, FILENAME + ": Invalid user!");
            username.setText(null);
            passowrd.setText(null);
            return;
        }

        Log.v(TAG, FILENAME + ": Valid User! Logging in");
        Intent intent = new Intent(MainActivity.this, Levels.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", name);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void onClickRegister(View v){
        Log.v(TAG, FILENAME + ": Create new user!");
        Intent intent = new Intent(MainActivity.this, RegisterUser.class);
        startActivity(intent);
    }

    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean isValidUser(String userName, String password){
        UserData userData = dbHandler.findUser(userName);
        if (userData == null){
            Log.v(TAG, FILENAME+" Could not find the username!");
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        Log.v(TAG, FILENAME + ": Running Checks..." + userData.getMyUserName() + ": " + userData.getMyPassword() +" <--> "+ userName + " " + password);

        if (!userData.getMyPassword().equals(password)){
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
