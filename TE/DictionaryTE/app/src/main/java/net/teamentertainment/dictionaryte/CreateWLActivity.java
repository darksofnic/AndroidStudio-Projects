package net.teamentertainment.dictionaryte;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CreateWLActivity extends AppCompatActivity {
    // Variables Declarations
    int count = 0;//getIntent().getExtras().getInt("number");
    String mTextE, mTextG;
    Button createBt;
    EditText mENGInp, mGEMInp;
    RadioButton rb;
    RadioGroup rg;
    TextView textView;
    TextView tv_output;
    private static final int WRITE_EXTERNAL_STORAGE_CODE =1;
    private static final int PERMISSION_REQUEST_STORAGE= 1000;
    private static final int READ_REQUEST_CODE= 42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = MainActivity.getCount();
        setContentView(R.layout.activity_create_wl);
        String nWords = count + "/ 25";

        // Initializations
        createBt = findViewById(R.id.createBt);
        mENGInp = findViewById(R.id.editText);
        mGEMInp = findViewById(R.id.editText2);
        createBt.setText(nWords);
        rg = findViewById(R.id.radioGroup);



        createBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= 25){
                    finish();
                    System.exit(0);
                    overridePendingTransition(0, 0);
                    startActivity(new Intent(CreateWLActivity.this, MainActivity.class));
                    overridePendingTransition(0, 0);
                }

                rb = findViewById(rg.getCheckedRadioButtonId());
                if (isEmpty(mENGInp))
                    Toast.makeText(CreateWLActivity.this, "Please insert your English Word", Toast.LENGTH_SHORT).show();
                else if (isEmpty(mGEMInp))
                    Toast.makeText(CreateWLActivity.this, "Please insert your German Word", Toast.LENGTH_SHORT).show();

                else //(!(isEmpty(mENGInp)) && !(isEmpty(mGEMInp))){
                {

                    mTextE = mENGInp.getText().toString().trim();
                    mTextG = mGEMInp.getText().toString().trim();
                    rb.findViewById(rg.getCheckedRadioButtonId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_DENIED) {
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);
                        } else { // Permission has being granted
                            saveToTxtFile(mTextE, mTextG,rb.getText().toString());
                        }
                    } else { //System is < Than Marshmallow, no need to run time permission to save
                        saveToTxtFile(mTextE, mTextG,rb.getText().toString());

                    }

                    count++;
                    createBt.setText(count + "/25");
                    mENGInp.setText(""); mGEMInp.setText("");
                }
            }
        });
    }


    //REad content of the file
    private String readText(String input) {
        File file = new File(input);
        StringBuilder text = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null){
                text.append(line);
                text.append("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch(requestCode){
                case WRITE_EXTERNAL_STORAGE_CODE: {
                    // if request is cancelled, the result arrays are empty
                    if (grantResults.length > 0 && grantResults[0]
                            == PackageManager.PERMISSION_GRANTED){
                        // permission was granted.
                        saveToTxtFile(mTextE,mTextG,rb.getText().toString());
                    }
                    else
                        // Permission was denied
                        Toast.makeText(this,"Storage permission is required to store data",Toast.LENGTH_SHORT).show();
                }
                case PERMISSION_REQUEST_STORAGE: {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        private void saveToTxtFile(String mENG, String mGEM,String obj) {
            String mEG= mENG + "\n" + mGEM + "\n" +  obj + "\n";
            try{
                File path = Environment.getExternalStorageDirectory();
                //create folder name "My Files"
                File dir = new File(path + "/TE/");
                dir.mkdirs();
                //file name
                String fileName = "list.txt";

                File file = new File(dir, fileName);

                //File writer class is  used to store character in file
                FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(mEG);
                bw.close();
            }catch (Exception e){
                // if anything goes wrong
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    public boolean isEmpty(EditText option){
        if (option.getText().length() == 0)
            return true;


       return false;
    }



/*

createBt = findViewById(R.id.creates);

        // remove !!!!!!
        createBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){

                mText = mENGInp.getText().toString().trim();
                mText2= mGEMInp.getText().toString().trim();
                if (mText.isEmpty() || mText2.isEmpty())
                    Toast.makeText(MainActivity.this, "You are missing Something...",Toast.LENGTH_SHORT).show();
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_DENIED){
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions,WRITE_EXTERNAL_STORAGE_CODE);
                        }
                        else { // Permission has being granted
                            saveToTxtFile(mText,mText2);
                        }
                    }
                    else { //System is < Than Marshmallow, no need to run time permission to save
                        saveToTxtFile(mText,mText2);

                    }
                }
            }
        });
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE: {
                // if request is cancelled, the result arrays are empty
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED){
                    // permission was granted.
                    saveToTxtFile(mText,mText2);
                }
                else
                    // Permission was denied
                    Toast.makeText(this,"Storage permission is required to store data",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToTxtFile(String mENG, String mGEM) {
        String mEG= mENG + "|" + mGEM;
        try{
            File path = Environment.getExternalStorageDirectory();
            //create folder name "My Files"
            File dir = new File(path + "/TE/");
            dir.mkdirs();
            //file name
            String fileName = "list.txt";

            File file = new File(dir, fileName);

            //File writer class is  used to store character in file
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(mEG);
            bw.close();
        }catch (Exception e){
            // if anything goes wrong
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }*/
}
