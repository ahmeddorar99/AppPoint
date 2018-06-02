package com.project.geo.finalproject;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Chang_font extends AppCompatActivity {

    private static final String TAG = "";
    TextView Test, Text;
    Spinner ChooseFont;
    Button Save;
    String Font_Type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_font);


        Save = (Button) findViewById(R.id.button8);
        Text = (TextView) findViewById(R.id.textView35);
        ChooseFont = (Spinner) findViewById(R.id.spinner4);
        Test = (TextView) findViewById(R.id.textView38);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chang_font.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Fonts));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ChooseFont.setAdapter(adapter);
        Font_Type = "font/Roboto-Regular.ttf";


        ChooseFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        Typeface face = Typeface.createFromAsset(getAssets(), "font/Roboto-Regular.ttf");
                        Font_Type = "font/Roboto-Regular.ttf";
                        Test.setTypeface(face);

                    }
                    break;
                    case 1: {
                        Typeface face = Typeface.createFromAsset(getAssets(), "font/RobotoCondensed-Regular.ttf");
                        Font_Type = "RobotoCondensed-Regular.ttf";
                        Test.setTypeface(face);
                    }
                    break;
                    case 2: {
                        Typeface face = Typeface.createFromAsset(getAssets(),
                                "font/NotoSerif-Regular.ttf");
                        Font_Type = "font/NotoSerif-Regular.ttf";
                        Test.setTypeface(face);
                    }
                    break;
                    case 3: {
                        Typeface face = Typeface.createFromAsset(getAssets(),
                                "font/droid-sans-mono.ttf");
                        Font_Type = "font/droid-sans-mono.ttf";
                        Test.setTypeface(face);

                    }
                    break;
                    case 4: {
                        Typeface face = Typeface.createFromAsset(getAssets(),
                                "font/CutiveMono.ttf");
                        Font_Type = "font/CutiveMono.ttf";
                        Test.setTypeface(face);

                    }
                    break;
                    case 5: {
                        Typeface face = Typeface.createFromAsset(getAssets(),
                                "font/ComingSoon.ttf");
                        Font_Type = "font/ComingSoon.ttf";
                        Test.setTypeface(face);

                    }
                    break;
                    case 6: {
                        Typeface face = Typeface.createFromAsset(getAssets(),
                                "font/DancingScript-Regular.ttf");
                        Font_Type = "font/DancingScript-Regular.ttf";
                        Test.setTypeface(face);
                    }
                    break;
                    case 7: {

                        Typeface face = Typeface.createFromAsset(getAssets(),
                                "font/CarroisGothicSC-Regular.ttf");
                        Font_Type = "font/CarroisGothicSC-Regular.ttf";
                        Test.setTypeface(face);

                    }
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Fun();

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    writeToFile(Font_Type, Chang_font.this);
                    onBackPressed();
                    Toast.makeText(Chang_font.this, "Font Change Sucessfuly", Toast.LENGTH_SHORT).show();


                } catch (Exception ex) {
                    Toast.makeText(Chang_font.this, ex.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("FontType.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public void Fun() {
        GetPath Get = new GetPath();

        if (Get.readFromFile(Chang_font.this).isEmpty() == false) {
            Typeface face = Typeface.createFromAsset(getAssets(), Get.readFromFile(Chang_font.this));
            Save.setTypeface(face);
            Text.setTypeface(face);

        }
        if (Get.readFromFile2(Chang_font.this).isEmpty() == false) {
            Float size = Float.parseFloat(Get.readFromFile2(Chang_font.this));
            Save.setTextSize(size);
            Text.setTextSize(size);

        }

    }
}
