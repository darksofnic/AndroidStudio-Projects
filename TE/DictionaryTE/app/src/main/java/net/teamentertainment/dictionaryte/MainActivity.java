package net.teamentertainment.dictionaryte;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Vector;

import static java.lang.Character.toLowerCase;

public class MainActivity extends AppCompatActivity {

    // Variables Declarations
   public static int count = -1;
    Button searchB;
    TextView searchT;
    EditText searchF;
    TextView tv_output;
    public Dictionary[] dictionary;
    public BubbleSort bsort;
    public int[] index;
    private static final int PERMISSION_REQUEST_STORAGE= 1000;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = countLines()/3;
        readFromFile();
        tv_output = (TextView) findViewById(R.id.tv_output);
        searchB = (Button) findViewById(R.id.searchB);
        searchF = (EditText) findViewById(R.id.searchF);
        searchT = (TextView) findViewById(R.id.searchT);
        searchBox(false);
    }

    public static int getCount(){
        return count;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);

        if (count == 0) {
         activateOptions(menu,false);
        }
        else if (count >=24){
            menu.getItem(0).setEnabled(false);
            activateOptions(menu,true);
        }
        else{
         activateOptions(menu,true);
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                createWordList();
                return true;
            case R.id.item2: {

                tv_output.setText(langToLang("Noun"));
            }return true;
            case R.id.item3: {
                tv_output.setText(langToLang("Verb"));
            }return true;
            case R.id.item4: {
                tv_output.setText(langToLang("Preposition"));
            }
                Toast.makeText(this, "Item 4 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item5:
            {
                tv_output.setText(langToLang("Adjective"));
            }
                Toast.makeText(this, "Item 5 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item6:
            {
                tv_output.setText(langToLang("Adverb"));
            }
                Toast.makeText(this, "Item 6 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item7:
            {
                tv_output.setText(langToLang("Cardinal Number"));
            }
                Toast.makeText(this, "Item 7 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item8:
                tv_output.setText(langToLang("Noun"));
                Search();
                Toast.makeText(this, "Item 8 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item9:
                quit();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void Search(){
        searchBox(true);
        tv_output.setVisibility(View.INVISIBLE);

        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make Text Visible
                tv_output.setVisibility(View.VISIBLE);

                // Hide Virtual Keyboard after Button is Click
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchF.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                //Build Information Box
                StringBuilder textLine = new StringBuilder();
                Formatter format = new Formatter();
                String text = "|German Word|Part of speech|English Meaning|\n" +
                        "| ____________ + ____________  + _______________ |";

                textLine.append(text);
                textLine.append("\n");

                for (int i = 0;i < count; i++){
                    if (compare(word(dictionary[index[i]].germanWord),word(searchF.getText().toString().trim()))) {
                        format.format("|%1$15s|%2$21s|%3$26s|\n", dictionary[index[i]].germanWord,dictionary[index[i]].pOS,dictionary[index[i]].englishWord);
                    }
                }
                textLine.append(format.toString());
                textLine.append("\n");
                tv_output.setText(textLine.toString());

            }
        });

    }

    String word(String a){
        String [] c ;
        c = a.split(" ");
        if (c.length==2)
            return c[1].toLowerCase();
        else
        return a.toLowerCase();
    }

    boolean compare(String data, String input){

        data= data.replaceAll("ö", "o");
        input = input.replaceAll("ö", "o");
        data= data.replaceAll("ä", "a");
        input = input.replaceAll("ä", "a");
        data= data.replaceAll("ü", "u");
        input = input.replaceAll("ü", "u");

        if (data.equals(input)) {
            return true;
        }
        else {
            return false;
        }
    }

    void searchBox(boolean tOF){
        if (tOF)
        {
            searchB.setVisibility(View.VISIBLE);
            searchF.setVisibility(View.VISIBLE);
            searchT.setVisibility(View.VISIBLE);
        }
        else
        {
            searchB.setVisibility(View.GONE);
            searchF.setVisibility(View.GONE);
            searchT.setVisibility(View.GONE);
        }
    }



    private String langToLang(String typ) {
        tv_output.setVisibility(View.VISIBLE);
        searchBox(false);
        StringBuilder textLine = new StringBuilder();
        Formatter format = new Formatter();
        bsort = new BubbleSort(25);
        bsort.insert(dictionary);
        bsort.bubbleSort();
        index = new int[25];
        index = bsort.sorted();
        String text = "|German Word|Part of speech|English Meaning|\n" +
                      "| ____________ + ____________  + _______________ |";

        textLine.append(text);
        textLine.append("\n");

String [] plit;

      for (int i = 0;i < count; i++){
          plit = dictionary[index[i]].pOS.split("/");
          if (plit.length==2){
              if (plit[0].equals( typ) || plit[1].equals( typ))
                  format.format("|%1$15s|%2$21s|%3$26s|\n", dictionary[index[i]].germanWord,dictionary[index[i]].pOS,dictionary[index[i]].englishWord);}//

           if (dictionary[index[i]].pOS.equals( typ)) {
                format.format("|%1$15s|%2$21s|%3$26s|\n", dictionary[index[i]].germanWord,dictionary[index[i]].pOS,dictionary[index[i]].englishWord);
           }
        }
                textLine.append(format.toString());
                textLine.append("\n");
            return textLine.toString();
    }

    private void createWordList() {
        startActivity(new Intent(MainActivity.this, CreateWLActivity.class));
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    System.exit(0);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    private int countLines() {

        //create folder name "My Files"
        File file = new File(Environment.getExternalStorageDirectory() + "/TE/","list.txt");
        StringBuilder text = new StringBuilder();
        int i=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null){

                i++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    //REad content of the file
    private void readFile() {

        //create folder name "My Files"
        File file = new File(Environment.getExternalStorageDirectory() + "/TE/","list.txt");
        StringBuilder text = new StringBuilder();
        String[] arr;
        dictionary = new Dictionary[count];
        String line;
        int i=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));

            while((line = br.readLine()) != null){
                dictionary[i] = new Dictionary();
                dictionary[i].setEW(line);
                dictionary[i].setGW(br.readLine());
                dictionary[i].setPOS(br.readLine());
                i++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_REQUEST_STORAGE: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void readFromFile(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }
        readFile();

    }

    private void activateOptions( Menu menu,boolean tOf){
        menu.getItem(1).setEnabled(tOf);
        menu.getItem(2).setEnabled(tOf);
        menu.getItem(3).setEnabled(tOf);
        menu.getItem(4).setEnabled(tOf);
        menu.getItem(5).setEnabled(tOf);
        menu.getItem(6).setEnabled(tOf);
        menu.getItem(7).setEnabled(tOf);

    }


}

class Dictionary {

    String germanWord=" ";
    String pOS=" ";
    String englishWord=" ";

    public void setGW(String german){germanWord = german;}
    public void setPOS(String pos) { pOS = pos;}
    public void setEW(String ew) { englishWord = ew;}

}

class BubbleSort {
   private int [] theInt;
   private String[] theString;

BubbleSort(int max) {
    theInt = new int[max];
    theString = new String[max];

}

    void insert(Dictionary[] myArray){

        String[] split;
        for (int i = 0; i < theString.length;i++){
            theString[i] = myArray[i].germanWord;
            split = theString[i].split(" ");
            if(split.length == 2) theString[i] = split[1];
            else
                theString[i] = split[0];

            theInt[i]=i;
}
    }


    void bubbleSort(){

    for (int i= 0; i < theInt.length;i++) {
        for(int j = 0; j < theInt.length -1 ; j++) {
           if(gChart(theString[theInt[i]].charAt(0)) <  gChart(theString[theInt[j]].charAt(0))){
               swap(theInt,i,j);
            }
        }
    }

    }
    int [] sorted() {return theInt;}

    char gChart(char c){

    char cs;
        if ( c == '\u00D6' || c == '\u00F6') return 'o';
        else if ( c == '\u00C4' || c == '\u00E4') return 'a';
        else if ( c == '\u00DC' || c == '\u00FC') return 'u';

    return toLowerCase(c);

    }

    void swap(int [] rr,int objA, int objB)
    {
        int temp = rr[objA];
        rr[objA] = rr[objB];
        rr[objB] = temp;
    }
}

