package com.chatterbox.chatterbox.data.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.data.models.ChatsModel;

import java.util.List;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox.data.adapters
 * Created by lusinabrian on 02/09/16 at 16:16
 * <p/>
 * Description: Adapter that populates the ChatsFragmentRecyclerView
 */
public class ChatsFragAdapter extends RecyclerView.Adapter<ChatsFragAdapter.ViewHolder>{
    private Context mContext;
    private List<ChatsModel> chatsModelList;
    public int itemLayout;

    /*constructor*/
    public ChatsFragAdapter(Context mContext, List<ChatsModel> chatsModelList, int itemLayout){
        this.mContext = mContext;
        this.itemLayout = itemLayout;
        this.chatsModelList = chatsModelList;
        this.notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ChatsModel chatsModel = chatsModelList.get(position);
        holder.itemView.setTag(chatsModel);
        holder.bind(chatsModel);

        //load images using Glider library
        Glide.with(mContext).load(chatsModel.getPhotoUrl()).into(holder.lastChatterImg);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView lastChatterImg;
        public TextView chatLastMessage, chatLastChatter, chatTimeStamp;

        public ViewHolder(View itemView) {
            super(itemView);
            lastChatterImg = (ImageView) itemView.findViewById(R.id.chatfrag_item_img);
            chatLastMessage = (TextView) itemView.findViewById(R.id.chatsfrag_last_msg_id);
            chatLastChatter = (TextView) itemView.findViewById(R.id.chatsfrag_whochat_id);
            chatTimeStamp = (TextView) itemView.findViewById(R.id.chatsfrag_timestamp_id);
        }
        public void bind(ChatsModel chatsModel){
            chatLastMessage.setText(chatsModel.getText());
            chatLastChatter.setText(chatsModel.getName());
            chatTimeStamp.setText(chatsModel.getTime());
        }
    }

    public void add(ChatsModel itemModel, int postion){
        chatsModelList.add(postion,itemModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatsModelList.size();
    }

}
