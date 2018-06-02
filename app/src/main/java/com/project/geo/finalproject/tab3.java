package com.project.geo.finalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class tab3 extends Fragment {
    private static final String TAG = "tab3";
    CircleImageView mImageView;
    TextView Name, user, GPS, About, Supporttex, FontType, Notificat, Them, ChangSize;
    String fullname, email, ProfileImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);

        mImageView = (CircleImageView) view.findViewById(R.id.profile_imag2e);
        Name = (TextView) view.findViewById(R.id.textView11);
        user = (TextView) view.findViewById(R.id.textView12);
        GPS = (TextView) view.findViewById(R.id.textView15);
        About = (TextView) view.findViewById(R.id.textView17);
        Supporttex = (TextView) view.findViewById(R.id.textView19);
        FontType = (TextView) view.findViewById(R.id.textView13);
        Notificat = (TextView) view.findViewById(R.id.textView18);
        Them = (TextView) view.findViewById(R.id.textView16);
        ChangSize = (TextView) view.findViewById(R.id.textView14);

        email = getArguments().getString("Username");
        ProfileImage = getArguments().getString("ProfileImage");
        fullname = getArguments().getString("Fname") + " " + getArguments().getString("Lname");

        if (ProfileImage != null)
            Picasso.get().load(ProfileImage).resize(55, 55).centerCrop().into(mImageView);


        Name.setText("  " + fullname);
        user.setText("  " + email);

        ChangeFont(view);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        ProfileActivity.class);
                intent.putExtra("Username", email);
                intent.putExtra("ProfileImage", ProfileImage);
                startActivity(intent);
            }
        });
        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        ProfileActivity.class);
                intent.putExtra("Username", email);
                intent.putExtra("ProfileImage", ProfileImage);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        ProfileActivity.class);
                intent.putExtra("Username", email);
                intent.putExtra("ProfileImage", ProfileImage);
                startActivity(intent);
            }
        });

        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
            }
        });
        Supporttex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Support.class);
                intent.putExtra("Username", email);
                startActivity(intent);
            }
        });

        FontType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Chang_font.class);
                startActivity(intent);
            }
        });
        ChangSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FontSize.class);
                startActivity(intent);
            }
        });
        return view;


    }

    public void ChangeFont(View V) {
        GetPath Get = new GetPath();
        String S = Get.readFromFile(V.getContext());
        if (S.isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(V.getContext().getAssets(), S);
            Name.setTypeface(face); user.setTypeface(face); GPS.setTypeface(face); About.setTypeface(face); Supporttex.setTypeface(face);
            FontType.setTypeface(face); Notificat.setTypeface(face); ChangSize.setTypeface(face); Them.setTypeface(face);
        }
        if (Get.readFromFile2(V.getContext()).isEmpty() == false) {
            Float size = Float.parseFloat(Get.readFromFile2(V.getContext()));
            Name.setTextSize(size); user.setTextSize(size); GPS.setTextSize(size); About.setTextSize(size); Supporttex.setTextSize(size);
            FontType.setTextSize(size); Notificat.setTextSize(size); ChangSize.setTextSize(size); Them.setTextSize(size);
        }
    }
}
