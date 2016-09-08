package com.chatterbox.chatterbox.views.drawerfragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatterbox.chatterbox.R;
import com.chatterbox.chatterbox.adapters.ChatsFragAdapter;
import com.chatterbox.chatterbox.views.MainActivity;
import com.chatterbox.chatterbox.models.ChatsModel;
import com.chatterbox.chatterbox.touchlisteners.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 02/09/16 at 12:01
 * <p/>
 * Description:
 */
public class ChatsFragment extends Fragment{
    private RecyclerView recyclerView;
    private ChatsModel chatsModel;
    private ChatsFragAdapter chatsFragAdapter;
    private List<ChatsModel> chatsModelList;
    private CoordinatorLayout coordinatorLayout;
    private static final String CHATSFRAG_TAG = ChatsFragment.class.getSimpleName();

    /**require empty constructor*/
    public ChatsFragment(){}

    /**gets a new instance of the chats fragment**/
    public static Fragment newInstance() {
        ChatsFragment chatsFragment = new ChatsFragment();
        chatsFragment.setRetainInstance(true);
        return chatsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatsModelList = new ArrayList<>();
        chatsFragAdapter = new ChatsFragAdapter(getActivity(), chatsModelList, R.layout.chatsfrag_item_layout);
        prepareMockChatItems();

        if(!isNetworkAvailable()) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getString(R.string.snackbar_warning_no_internet_conn), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.snackbar_no_internet_conn_retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, getString(R.string.snackbar_no_internet_conn_retry), Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                        }
                    });
            snackbar.show();
        }
    }

    /**Check network connectivity for mobile device*/
    private boolean isNetworkAvailable() {
        ConnectivityManager connMan = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMan.getActiveNetworkInfo();
        return networkInfo !=null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO: open chat activity based on position of chats in RecyclerView
                Intent openChat = new Intent(getActivity(), MainActivity.class);
                startActivity(openChat);
            }
        }));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chatsfrag_layout, container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.chatsfragment_recyclerview_id);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.chatsfrag_coordinatorLayout_id);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatsFragAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**called once fragment becomes visible*/
    @Override
    public void onStart() {
        super.onStart();
    }

    /**fragment becomes active*/
    @Override
    public void onResume() {
        super.onResume();
    }

    /**called when user leaves fragment where changes should be committed. Changes should persist
     * beyond current user session*/
    @Override
    public void onPause() {
        super.onPause();
    }

    /**when fragment is stopped*/
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * fragment is destroyed after this
     * */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Performs final clean up of fragments state but not guaranteed to be called by Android platform
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*mock images and text for food items*/
    private void prepareMockChatItems() {
        //add mock data
        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);
        chatsModel = new ChatsModel("The Lusina", "The Lusina", "Message", "Wednesday");
        chatsModelList.add(chatsModel);

        Log.d(CHATSFRAG_TAG, chatsModel.toString());
    }
/*END*/
}
