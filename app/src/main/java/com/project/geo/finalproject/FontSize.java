package com.project.geo.finalproject;

import android.content.Context;
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

public class FontSize extends AppCompatActivity {
    TextView Test2, Text2;
    Spinner ChooseFontSize;
    Button SaveSize;
    String Font_Size2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);

        SaveSize = (Button) findViewById(R.id.btnSaveFont);
        Text2 = (TextView) findViewById(R.id.textFontSize);
        ChooseFontSize = (Spinner) findViewById(R.id.spinner5);
        Test2 = (TextView) findViewById(R.id.textViewFont);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FontSize.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.FontsSize));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ChooseFontSize.setAdapter(adapter);
        Font_Size2 = "18";
        ChooseFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        Test2.setTextSize(12);
                        Font_Size2 = "10";
                    }
                    break;
                    case 1: {
                        Test2.setTextSize(18);
                        Font_Size2 = "14";
                    }
                    break;
                    case 2: {
                        Test2.setTextSize(24);
                        Font_Size2 = "18";
                    }
                    break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SaveSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToFile(Font_Size2, FontSize.this);
                Toast.makeText(FontSize.this, "Font Size Change Sucessfuly", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("FontSize.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


}
