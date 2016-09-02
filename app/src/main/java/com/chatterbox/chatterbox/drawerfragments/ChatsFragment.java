package com.chatterbox.chatterbox.drawerfragments;

import android.support.v4.app.Fragment;

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

}
