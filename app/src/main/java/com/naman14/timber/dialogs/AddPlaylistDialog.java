package com.naman14.timber.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.naman14.timber.musicplayer.MusicPlayer;
import com.naman14.timber.dataloaders.PlaylistLoader;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.helpers.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naman on 20/12/15.
 */
public class AddPlaylistDialog extends DialogFragment {

    public static AddPlaylistDialog newInstance(Song song) {
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(song);
        return newInstance(songs);
    }

    public static AddPlaylistDialog newInstance(ArrayList<Song> songList) {
        AddPlaylistDialog dialog = new AddPlaylistDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("songs", songList);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final List<Playlist> playlists = PlaylistLoader.getPlaylists(getActivity(), false);
        CharSequence[] chars = new CharSequence[playlists.size() + 1];
        chars[0] = "Create new playlist";

        for (int i = 0; i < playlists.size(); i++) {
            chars[i + 1] = playlists.get(i).name;
        }
        return new MaterialDialog.Builder(getActivity()).title("Add to playlist").items(chars).itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                ArrayList<Song> songs = getArguments().getParcelableArrayList("songs");
                if (which == 0) {
                    CreatePlaylistDialog.newInstance(songs).show(getActivity().getSupportFragmentManager(), "CREATE_PLAYLIST");
                    return;
                }

                MusicPlayer.addToPlaylist(getActivity(), songs, playlists.get(which - 1).id);
                dialog.dismiss();

            }
        }).build();
    }
}
