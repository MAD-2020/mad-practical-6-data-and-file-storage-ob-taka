package sg.edu.np.week_6_whackamole_3_0;

import java.util.ArrayList;

public class UserData {

    /* NOTE:
            THIS OBJECT DATA IS GIVEN.
            DO NOT CHANGE ANYTHING IN HERE.
            YOU ARE TO USE THIS UserData as it is in your answers.

        The UserData consist of the following:
        1. UserName
        2. Password
        3. A list of Scores related to a list of corresponding Levels
        4. A list of Levels related to a list of corresponding Scores

     */

    public static final String TABLE_NAME = "Accounts";

    public static final String COLUMN_NAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_SCORES = "Score";
    public static final String COLUMN_LEVELS = "Level";
    private String MyUserName;
    private String MyPassword;
    private ArrayList<Integer> Scores = new ArrayList<>();
    private ArrayList<Integer> Levels = new ArrayList<>();

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_LEVELS  + " INTEGER," +
                    COLUMN_SCORES + " INTEGER," +
                    "PRIMARY KEY (" + COLUMN_NAME + "," +COLUMN_LEVELS + ")" + ");";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    public UserData()
    {
    }

    public UserData(String myUserName, String myPassword, ArrayList<Integer> myLevels, ArrayList<Integer> myScores) {
        this.MyUserName = myUserName;
        this.MyPassword = myPassword;
        this.Levels = myLevels;
        this.Scores = myScores;
    }

    public ArrayList<Integer> getLevels() {
        return this.Levels;
    }

    public void setLevels(ArrayList<Integer> levels) {
        this.Levels = levels;
    }

    public ArrayList<Integer> getScores() {
        return this.Scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.Scores = scores;
    }

    public String getMyUserName() {
        return this.MyUserName;
    }

    public void setMyUserName(String myUserName) {
        this.MyUserName = myUserName;
    }

    public String getMyPassword() {
        return this.MyPassword;
    }

    public void setMyPassword(String myPassword) {
        this.MyPassword = myPassword;
    }
}
