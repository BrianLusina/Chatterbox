package com.chatterbox.chatterbox.drawerfragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatterbox.chatterbox.R;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 02/09/16 at 12:01
 * <p/>
 * Description:
 */
public class ChatsFragment extends Fragment{

    /**require empty constructor*/
    public ChatsFragment(){}

    /**gets a new instance of the chats fragment**/
    public static Fragment newInstance() {
        ChatsFragment chatsFragment = new ChatsFragment();
        chatsFragment.setRetainInstance(true);
        return chatsFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chatsfrag_layout, container,false);

        return rootView;
    }

/*END*/
}
