package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {

    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    UserData userData;
    ArrayList<Integer> level;
    ArrayList<Integer> score;
    Context context;
    private CustomScoreAdaptor.OnItemClickListener listener;

    public CustomScoreAdaptor(UserData userdata, Context context){
       this.userData = userdata;
       this.context = context;
       this.level = userdata.getLevels();
       this.score = userdata.getScores();
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level, null);

        return new CustomScoreViewHolder(view, listener);
    }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position){

        String currentscore = String.valueOf(score.get(position));
        String currentlevel = String.valueOf(level.get(position));

        holder.score.setText(currentscore);
        holder.level.setText(currentlevel);

        Log.v(TAG, FILENAME + " Showing level " + level + " with highest score: " + score);
    }

    public int getItemCount(){
       return level.size();
    }

    public void setOnItemClickListener(CustomScoreAdaptor.OnItemClickListener mListener){
        this.listener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}