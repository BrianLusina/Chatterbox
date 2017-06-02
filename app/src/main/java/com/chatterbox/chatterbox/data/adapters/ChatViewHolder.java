package com.chatterbox.chatterbox.data.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chatterbox.chatterbox.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.data.adapters
 * Created by lusinabrian on 02/09/16 at 17:52
 * <p/>
 * Description: Adapter for chat items in each individual Chat
 */
public class ChatViewHolder extends RecyclerView.ViewHolder{
    public TextView messageTextView;
    public TextView messengerTextView;
    public CircleImageView messengerImageView;

    public ChatViewHolder(View v) {
        super(v);
        messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
        messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
        messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
    }

}
