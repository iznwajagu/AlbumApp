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

        mViewModel.getAlbumList().observe(this, albumList -> {
            setAdapter(albumList);
        });

        mViewModel.getStatusMessage().observe(this, message ->{
            showMessage(message);
        });

        mViewModel.progressBarState.observe(this, progressbarStatus -> {
            mProgressBar.setVisibility(View.INVISIBLE);
        });

    }

    private void setAdapter(List<Album> albumList) {

        mAlbumListAdapter.swapAlbumList(albumList);
        mRecyclerView.setVisibility(View.VISIBLE);
        mMessageHolder.setVisibility(View.INVISIBLE);

    }


    private void showMessage(String message) {

        Snackbar.make(getView().getRootView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

    }


}
