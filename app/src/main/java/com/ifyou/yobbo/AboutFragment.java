package com.ifyou.yobbo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ifyou on 04.05.2016.
 */
public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView gp = (TextView) view.findViewById(R.id.aboutgp);
        TextView gp2 = (TextView) view.findViewById(R.id.aboutgp2);
        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/115708999579517686862"));
                startActivity(browserIntent);
            }
        });
        gp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/113175829569090793963"));
                startActivity(browserIntent);
            }
        });
    }
}
