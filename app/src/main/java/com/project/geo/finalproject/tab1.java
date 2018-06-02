package com.project.geo.finalproject;

import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.geo.finalproject.Model.PostClass;

import java.util.ArrayList;

public class tab1 extends Fragment {
    private static final String TAG = "tab1";
    String email, Gender, Birthdate;
    FirebaseDatabase database;
    DatabaseReference Posts;
    DatabaseReference Long_Lati;
    public Double aLong, lati;
    String Key2;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<PostClass> ArrPost;
    final ArrayList<String> PostChild = new ArrayList<>();
    int Range;
    View view;
    Spinner Spiner2;
    TextView mtextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        view = inflater.inflate(R.layout.tab1, container, false);
        email = getArguments().getString("Username");
        Gender = getArguments().getString("Gender");
        Birthdate = getArguments().getString("Birthdte");

        Spiner2 = (Spinner) view.findViewById(R.id.spinner2);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        database = FirebaseDatabase.getInstance();
        Posts = database.getReference("Posts");
        ArrPost = new ArrayList<>();
        mtextView = (TextView) view.findViewById(R.id.textView6);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Range));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Spiner2.setAdapter(adapter);

        try {
            Posts.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        String childName = child.getKey();
                        PostChild.add(childName);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_SHORT).show();
        }

        Spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (PostChild.isEmpty() == false) {
                    try {
                        if (i == 1) {
                            Range = 1000;
                            Get_All_Poists();
                        } else if (i == 2) {
                            Range = 2000;
                            Get_All_Poists();
                        } else if (i == 3) {
                            Range = 3000;
                            Get_All_Poists();
                        } else if (i == 4) {
                            Range = 4000;
                            Get_All_Poists();
                        } else if (i == 5) {
                            Range = 5000;
                            Get_All_Poists();
                        }
                    } catch (Exception ex) {
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ChangeFont(view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        return view;
    }
   // function test that if posit in range or not
    public Boolean isInside(String Point1, String Point2, int Km) {

        Boolean Is_Inside = false;
        String Tem1, Tem2, Tem3, Tem4;
        String[] Point1_Arry = Point1.split("_");
        String[] Point2_Arry = Point2.split("_");

        Tem1 = Point1_Arry[0].replace(' ', '.');
        Point1_Arry[0] = Tem1;
        Tem2 = Point1_Arry[1].replace(' ', '.');
        Point1_Arry[1] = Tem2;
        Tem3 = Point2_Arry[0].replace(' ', '.');
        Point2_Arry[0] = Tem3;
        Tem4 = Point2_Arry[1].replace(' ', '.');
        Point2_Arry[1] = Tem4;

        Double X1, Y1, X2, Y2;
        X1 = Double.parseDouble(Point1_Arry[0]);
        Y1 = Double.parseDouble(Point1_Arry[1]);
        X2 = Double.parseDouble(Point2_Arry[0]);
        Y2 = Double.parseDouble(Point2_Arry[1]);

        Location loc1 = new Location("");
        loc1.setLatitude(X1);
        loc1.setLongitude(Y1);

        Location loc2 = new Location("");
        loc2.setLatitude(X2);
        loc2.setLongitude(Y2);

        float distanceInMeters = loc1.distanceTo(loc2);

        if (distanceInMeters <= Km) {
            Is_Inside = true;
        } else {
            Is_Inside = false;

        }

        return Is_Inside;
    }

    public void Get_All_Poists() {

        ArrPost = new ArrayList<>();
        try {
            GpsTracker gpsTracker = new GpsTracker(getActivity());
            if (gpsTracker.canGetLocation()) {
                lati = gpsTracker.getLatitude();
                aLong = gpsTracker.getLongitude();
            } else {
                gpsTracker.showSettingsAlert();
            }
        } catch (Exception ex) {

        }
        String Str_long = aLong.toString();
        Str_long = Str_long.replace('.', ' ');

        String Str_lat = lati.toString();
        Str_lat = Str_lat.replace('.', ' ');

        Key2 = Str_long + "_" + Str_lat;

        Long_Lati = Posts.child(Str_long + "_" + Str_lat);

        DatabaseReference x[] = new DatabaseReference[PostChild.size()];
        DatabaseReference x2[] = new DatabaseReference[PostChild.size()];

        for (int i = 0; i < PostChild.size(); i++) {
            if (isInside(Key2, PostChild.get(i), Range)) {
                // get all posts from firebase
                String Name = PostChild.get(i);
                x[i] = Posts.child(Name);
                try {
                    x[i].addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                PostClass Post = child.getValue(PostClass.class);
                                ArrPost.add(Post);
                                mAdapter = new MainAdapter(ArrPost, email, Gender, Birthdate);
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception ex) {
                }

            }
        }


    }

    public void ChangeFont(View V) {
        GetPath Get = new GetPath();
        String S = Get.readFromFile(V.getContext());
        if (S.isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(V.getContext().getAssets(), S);
            mtextView.setTypeface(face);

        }
    }


}




