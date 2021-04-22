package com.example.mydaysapp2.ui.mainpage.tabFirst;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.Goal;
import com.example.mydaysapp2.ui.group.GroupViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.MyViewHolder>{
    Context context;
    private ArrayList<Goal> goals;
    private GoalsViewModel mViewModel;
    private FirebaseAuth mAuth;
    private String currentUserName;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.goal_row,parent,false);
        return new GoalsAdapter.MyViewHolder(view);
    }

    public GoalsAdapter(Context context, ArrayList<Goal> goals, GoalsViewModel mViewModel, String currentUserName) {
        this.context = context;
        this.goals = goals;
        this.mViewModel = mViewModel;
        this.currentUserName = currentUserName;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Goal currentItem = goals.get(position);
        holder.goalTitleTv.setText(currentItem.getTitle());

        if (currentItem.getCreator()==currentUserName)
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewModel.deleteSubject(currentItem);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"onclick",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return goals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView goalTitleTv;
        ImageButton deleteButton;
        View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            goalTitleTv = itemView.findViewById(R.id.goalTextView);
            deleteButton = itemView.findViewById(R.id.deleteGoalButton);
            view = itemView.findViewById(R.id.goal);

        }
    }
}
