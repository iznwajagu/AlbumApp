package com.zubizaza.albumapp.views;

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

import com.zubizaza.albumapp.R;
import com.zubizaza.albumapp.data.model.Album;
import com.zubizaza.albumapp.di.Injector;
import com.zubizaza.albumapp.viewmodels.AlbumViewModel;
import com.zubizaza.albumapp.viewmodels.ViewModelFactory;

import java.util.List;

public class AlbumListFragment extends Fragment {

    private AlbumViewModel mViewModel;
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

        ViewModelFactory factory = Injector.provideViewModelFactory(this.getContext());
        AlbumViewModel mViewModel = ViewModelProviders.of(this, factory).get(AlbumViewModel.class);

        mViewModel.publishAlbumsToSubsribers().observe(this, albumList -> {
            setAdapter(albumList);
        });

        mViewModel.statusMessage.observe(this, message ->{
            showMessage(message);
        });

    }

    private void setAdapter(List<Album> albumList) {

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
    }


    private void showMessage(String message) {
        //Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        Snackbar.make(getView().getRootView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

    }


}
