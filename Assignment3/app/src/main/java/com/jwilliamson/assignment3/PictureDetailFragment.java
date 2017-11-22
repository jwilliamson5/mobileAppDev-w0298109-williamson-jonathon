package com.jwilliamson.assignment3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;

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
        if (mItem != null) {
            try {
                ImageView iv = ((ImageView) rootView.findViewById(R.id.picture_detail));
                iv.setImageDrawable(Drawable.createFromStream(getActivity().getAssets().open(mItem.path), null));
                final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.myanimation);
                iv.startAnimation(animation);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.startAnimation(animation);
                    }
                });
            } catch (Exception e) {
                Log.e("Fragment Error", e.toString());
            }
        }

        return rootView;
    }
}
