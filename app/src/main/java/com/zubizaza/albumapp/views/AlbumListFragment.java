package com.zubizaza.albumapp.views;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zubizaza.albumapp.AlbumApp;
import com.zubizaza.albumapp.R;
import com.zubizaza.albumapp.data.model.Album;
import com.zubizaza.albumapp.viewmodels.AlbumViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class AlbumListFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RecyclerView mRecyclerView;
    private TextView mMessageHolder;
    private AlbumListAdapter mAlbumListAdapter;
    private View mProgressBar;

    public static AlbumListFragment newInstance() {
        return new AlbumListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.album_list_fragment, container, false);

        mMessageHolder = rootView.findViewById(R.id.empty_placeholder);

        mProgressBar = rootView.findViewById(R.id.progress_circular);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration( new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerView.setNestedScrollingEnabled(false);

        mAlbumListAdapter = new AlbumListAdapter(getContext());

        mRecyclerView.setAdapter(mAlbumListAdapter);


        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AlbumApp) getActivity().getApplication()).getAppComponent().injectFragment(this);

        AlbumViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumViewModel.class);

        mViewModel.publishAlbumsToSubsribers().observe(this, albumList -> {
            setAdapter(albumList);
        });

        mViewModel.statusMessage.observe(this, message ->{
            mProgressBar.setVisibility(View.INVISIBLE);
            showMessage(message);
        });

    }

    private void setAdapter(List<Album> albumList) {
        Collections.sort(albumList);

        if (albumList != null && albumList.size() != 0){
            mAlbumListAdapter.swapAlbumList(albumList);
            showAlbumListView(true);
        }else{
            mMessageHolder.setText("Albums not available");
            showAlbumListView(false);
        }

    }

    private void showAlbumListView(boolean shouldShowList) {
        mRecyclerView.setVisibility(shouldShowList ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(shouldShowList ? View.GONE : View.VISIBLE);
        mMessageHolder.setVisibility(shouldShowList ? View.GONE : View.VISIBLE);
    }


    private void showMessage(String message) {
        //Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        Snackbar.make(getView().getRootView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

    }


}
