package com.example.mydaysapp2.ui.group;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydaysapp2.R;
import com.example.mydaysapp2.data.model.Chat;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    private ArrayList<Chat> messages;
    private String currentUser;

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == MSG_TYPE_RIGHT) {
            View view = inflater.inflate(R.layout.chat_item_right,parent,false);
            return new ChatAdapter.MyViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.chat_item_left,parent,false);
            return new ChatAdapter.MyViewHolder(view);
        }

    }

    public ChatAdapter(Context context,String currentUser, ArrayList<Chat> messages) {
        this.context = context;
        this.currentUser=currentUser;
        this.messages = messages;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat currentItem = messages.get(position);
      //  holder.profile_image.setImageResource(currentItem.get);
        Log.d("groupVMcreatingNote", currentItem.getMessage());
        holder.show_message.setText(currentItem.getMessage());

        String tab[] = currentItem.getSender().split("");
        String firstLetter = tab[1];
        holder.profile_image.setText(firstLetter);
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSender().equals(currentUser)){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView show_message;
        TextView profile_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }
}
