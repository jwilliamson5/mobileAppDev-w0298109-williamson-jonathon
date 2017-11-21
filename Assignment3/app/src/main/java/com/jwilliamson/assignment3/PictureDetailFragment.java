package com.jwilliamson.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;

/**
 * A fragment representing a single Picture detail screen.
 * This fragment is either contained in a {@link PictureListActivity}
 * in two-pane mode (on tablets) or a {@link PictureDetailActivity}
 * on handsets.
 */
public class PictureDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Picture mItem;
    private Gson gson;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PictureDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        if (prefs.contains("selection")) {
            String id = prefs.getString("selection", "1");
            mItem = gson.fromJson(prefs.getString(id, ""), Picture.class);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.picture_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((ImageView) rootView.findViewById(R.id.picture_detail)).setImageResource(Integer.parseInt(mItem.id));
        }

        return rootView;
    }
}
