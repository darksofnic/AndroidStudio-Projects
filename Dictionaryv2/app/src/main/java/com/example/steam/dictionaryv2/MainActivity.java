package com.example.steam.dictionaryv2;

import android.app.Activity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    private void DisplayToast (String msg)
    {
        Toast.makeText(getBaseContext(), msg,Toast.LENGTH_SHORT).show();
    }
}
/*
*      //
        // -- Button view ---
        Button btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener()
                                   {
                                       public void onClick(View v) {
                                           String str = "You have clicked the Open Button";
                                           DisplayToast(str);
                                       }
                                   }

        );

        // --- Button View ---
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                DisplayToast("You have clicked the save button");
            }
        });

        //---CheckBox---
        CheckBox checkBox = (CheckBox) findViewById(R.id.chkAutosave);
        checkBox.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if (((CheckBox)v).isChecked())
                    DisplayToast("CheckBox is checked");
                else
                    DisplayToast("CheckBox is unchecked");
            }
        });

         RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdbGp1);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = (RadioButton) findViewById(R.id.rdb1);
                if(rb1.isChecked())
                    DisplayToast("Option 1 checked!");
                else
                    DisplayToast("Option 2 checked!");
            }
        });

        //---ToggleButton---
        ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggle1);
        toggleButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (((ToggleButton)v).isChecked())
                    DisplayToast("Toggle Button is On");
                else
                    DisplayToast("Toggle button is off");
            }
        });
*
*
* */