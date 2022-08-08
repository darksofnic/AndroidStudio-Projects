package net.teamentertainment.te_palindrome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText field ;
    TextView tv_output;
    Button valid;
    StringBuilder build;
    StringBuilder reverse;
    char[] reguChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_output = findViewById(R.id.text_l);
        tv_output.setText("");
        valid = (Button) (findViewById(R.id.button));
        field = (EditText) findViewById(R.id.text_f);
        valid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                build = new StringBuilder();

                reverse = new StringBuilder();
                reguChar = field.getText().toString().toCharArray();
                for (int i = 0,j =reguChar.length-1; i < reguChar.length ; i++,j--)

                {
                    if (isValid(reguChar[j]) != '\0')
                    reverse.append(isValid(reguChar[j]));
                    if (isValid(reguChar[i]) != '\0')                       //StringBuilder has a function call reverse(), that give you the same result.
                    build.append(isValid(reguChar[i]));
                }



                if((build.toString().toLowerCase()).equals(reverse.toString().toLowerCase())){
                    Toast.makeText(MainActivity.this, "\""+field.getText().toString().trim()+"\""+" Is a Palindrome", Toast.LENGTH_SHORT).show();
                    tv_output.setText("\""+field.getText().toString().trim()+"\""+" Is a Palindrome");}
                else {
                    Toast.makeText(MainActivity.this, "\"" + field.getText().toString().trim() + "\"" + " Is NOT a Palindrome", Toast.LENGTH_SHORT).show();
                    tv_output.setText("\"" + field.getText().toString().trim() + "\"" + " Is NOT a Palindrome");
                }


                }
        });



    }
    private char isValid(char a){

         if ( a >= 'a' && a <= 'z' || a >= 'A' && a <= 'Z'  || a >= '0' && a <= '9' )
             return a;




        return '\0';
    }

}
