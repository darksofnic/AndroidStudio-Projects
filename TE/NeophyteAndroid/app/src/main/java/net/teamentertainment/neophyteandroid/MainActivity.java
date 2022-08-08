package net.teamentertainment.neophyteandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String textInp;
    Button send;
    Button clear;
    EditText messageF;
    TextView textVL;


   int count=0;
    public static int part = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageF = (EditText) findViewById(R.id.textF);
        textVL = (TextView) findViewById(R.id.textL);
        send = (Button) findViewById(R.id.button);
        clear = (Button) findViewById(R.id.button2);


clear.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 messageF.setText("");
                             }
});

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInp = messageF.getText().toString().trim();

                if (check() == true)
                    Toast.makeText(MainActivity.this, "Count  " + count, Toast.LENGTH_SHORT).show();
                part = 0;
                count = 0;
            }
        });
    }
    public boolean check() {

        if (!CheckDelL(textInp.charAt(part))){
            Toast.makeText(MainActivity.this, "MISSING LEFT DELIMITER if the right delimiter is missing", Toast.LENGTH_SHORT).show();

            return false;
        }
        else
        {
            for (int i = part; i < textInp.length(); i++){
                if ( returnOp(textInp.charAt(part)) == (textInp.charAt(i)))
                {
                    count++;
                    part = i +1;
                    if (part < textInp.length())
                    if (check() == false)
                        return false;

                    return true;
                }

            }
            Toast.makeText(MainActivity.this, "MISSING RIGHT DELIMITER if the right delimiter is missing", Toast.LENGTH_SHORT).show();
            return false;

        }

    }

   public char returnOp (char d){

        if (d == '{')
            return '}';
        else if (d == '}')
            return '{';
        else if (d == '[')
            return ']';
        else if (d == ']')
            return '[';
        else if (d == '(')
            return ')';
        else
            return '(';

   }


    boolean CheckDelL(char d)
    {
        if (d == '{' || d == '[' || d == '(')
            return true;

        return false;
    }

    boolean CheckDelR(char d)
    {
        if (d == '}' || d == ']' || d == ')')
            return true;
        return false;
    }

    public void quit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set a title for alert dialog
        builder.setTitle("Exiting the program");

        // Ask the final question
        builder.setMessage("Are you sure?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when user clicked the Yes button
                // Set the TextView visibility GONE
                finish();
                System.exit(0);
            }
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when No button clicked
                Toast.makeText(getApplicationContext(),
                        "No Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }
}

/*
In this exercise you are going to make the neophyte Android programmer happy by providing a utility that checks delimiters to see if there are equal numbers of parentheses,
braces and brackets( ( )  {}  [] ).

Your program must prompt the user to enter a string containing delimiters.
 When the user does this the delimiters will be counted and one of the following messages must be returned:
  MISSING RIGHT DELIMITER if the right delimiter is missing or if the left delimiter is missing;
  ERROR AT 0 or whatever offset the error occurred.   If a delimiter is entered other than the three referenced, do not consider this an error.
The program ends when you hit ENTER without entering any delimiter.

 */