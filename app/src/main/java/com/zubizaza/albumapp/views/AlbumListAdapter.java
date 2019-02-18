/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zubizaza.albumapp.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zubizaza.albumapp.R;
import com.zubizaza.albumapp.data.model.Album;

import java.util.List;

class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumAdapterViewHolder> {

    private final Context mContext;
    private final Resources resources;
    private List<Album> mAlbum;


    AlbumListAdapter(@NonNull Context context) {
        mContext = context;
        resources = context.getResources();
    }


    @Override
    public AlbumAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, viewGroup, false);
        view.setFocusable(true);
        return new AlbumAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AlbumAdapterViewHolder albumAdapterViewHolder, int position) {

        Album currentAlbum = mAlbum.get(position);

        String mAlbumTitle =  String.format(resources.getString(R.string.album_title), currentAlbum.getTitle());
        albumAdapterViewHolder.titleView.setText(mAlbumTitle);

        String mAlbumId =  String.format(resources.getString(R.string.album_id), currentAlbum.getId());
         albumAdapterViewHolder.albumIdView.setText(mAlbumId);

        String mUserId =  String.format(resources.getString(R.string.user_id), currentAlbum.getUserId());
        albumAdapterViewHolder.userIdView.setText(mUserId);

    }

    @Override
    public int getItemCount() {
        if (null == mAlbum) return 0;
        return mAlbum.size();
    }


    void swapAlbumList(final List<Album> newAlbums) {
        if (mAlbum == null) {
            mAlbum = newAlbums;
            notifyDataSetChanged();
        } else {

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mAlbum.size();
                }

                @Override
                public int getNewListSize() {
                    return newAlbums.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mAlbum.get(oldItemPosition).getId() ==
                            newAlbums.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Album newC = newAlbums.get(newItemPosition);
                    Album oldC = mAlbum.get(oldItemPosition);
                    return newC.getId() == oldC.getId()
                            && newC.getId().equals(oldC.getId());
                }
            });
            mAlbum = newAlbums;
            result.dispatchUpdatesTo(this);
        }
    }




    class AlbumAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView titleView;
        final TextView userIdView;
        final TextView albumIdView;

        AlbumAdapterViewHolder(View view) {

            super(view);
            titleView = view.findViewById(R.id.album_title);
            userIdView = view.findViewById(R.id.user_id);
            albumIdView = view.findViewById(R.id.album_id);

        }

    }

}