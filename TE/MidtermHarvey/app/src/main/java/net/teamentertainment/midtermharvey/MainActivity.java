package net.teamentertainment.midtermharvey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView question;
    Button backB, nextB,submitB;
    RadioGroup rg;
    RadioButton rb;
    QForm[] quest;
    String title;
    int score = 0;
    static int count=0;
    private static final int WRITE_EXTERNAL_STORAGE_CODE =1;
    private static final int PERMISSION_REQUEST_STORAGE= 1000;
    private static final int READ_REQUEST_CODE= 42;

  class QForm {
      String question;
      int correct;
      int tOF;
      QForm() {
          question = "";
          correct  = 0;
          tOF = 0;
      }
      QForm(String q, int correct){
          this.question = q;
          tOF = 0;
          this.correct= correct;
      }
      void setQuestion (String q )
      {
          question = q;
      }
      void settOF (int tof){
          tOF = tof;
      }

      void setCorrect(int c){
          correct = c;
      }

      int getCorrect() { return correct;}

      String getQuestion() {return question;}
      int gettOF() {return tOF;}


  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializations

        question = (TextView) findViewById(R.id.question_TV);
        backB = (Button) findViewById(R.id.backBt);
        nextB = (Button) findViewById(R.id.nextBt);
        submitB = (Button) findViewById(R.id.submitBt);
        rg = (RadioGroup) findViewById(R.id.radioGroup);

         quest = new QForm[25];
         loadQuestions();
         question.setText(count+1+"/25 ."+quest[count].getQuestion());
         if (count == 0)
         {
             backB.setVisibility(View.INVISIBLE);
             submitB.setVisibility(View.INVISIBLE);
         }



        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rb = findViewById(rg.getCheckedRadioButtonId());
                if (rb.getText().equals("True")){
                    quest[count].settOF(1);
                }
                else
                    quest[count].settOF(-1);
                count++;
                question.setText(count+1+"/25. "+quest[count].getQuestion());


                    setVisibleBS(true,true);
                    if (count == 24 ) {
                        nextB.setVisibility(View.INVISIBLE);
                    }

               // UNselecct choice..!!!
                if (quest[count].tOF==0)
                rb.setChecked(false);

            }});
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                question.setText(count+1+"/25. "+quest[count].getQuestion());
                if (count == 0)
                {
                    setVisibleBS(false,false);
                    if(quest[0].gettOF() != 0){
                        setVisibleBS(false,true);
                    }
                } else if ( count < 24)
                    nextB.setVisibility(View.VISIBLE);
            }});

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count < 24)
                    submit();
                else
                    score();
            }

        });





    }

    public void setVisibleBS(boolean b , boolean s) {
        if (b)
            backB.setVisibility(View.VISIBLE);
        else
            backB.setVisibility(View.INVISIBLE);
        if (s)
            submitB.setVisibility(View.VISIBLE);
        else
            submitB.setVisibility(View.INVISIBLE);
    }

    public void score(){
        for (int i = 0 ; i < 25 ; i++) {
            if (quest[i].gettOF() == quest[i].getCorrect()){
                score+=4;
            }
            setTitle();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set a title for alert dialog
        builder.setTitle("Your Score");

        // Ask the final question
        builder.setMessage("Your Score is : " + score + "\nYour Title is: " + title);

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when user clicked the Yes button
                // Set the TextView visibility GONE
                finish();
                System.exit(0);
            }
        });

        // Set the alert dialog no button click listener
       /* builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when No button clicked
                Toast.makeText(getApplicationContext(),
                        "No Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();


    }

   public  void setTitle() {
      if (score > 90)
          title = "Android Genius";
      else if( score > 72)
          title = "Proficiency in Android Programming";
      else if ( score > 52)
          title = " Marginal Proficiency in Android Programming";
      else title = "No Proficiency in Android Programming";
    }

    public void submit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set a title for alert dialog
        builder.setTitle("Submitting Quiz");

        // Ask the final question
        builder.setMessage("Are you sure?");

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when user clicked the Yes button
                // Set the TextView visibility GONE
                score();

            }
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when No button clicked
                Toast.makeText(getApplicationContext(),
                        "Continue your Quiz", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    void loadQuestions(){
        quest[0] = new QForm("Arrays are store in stack?",-1);
        quest[1] = new QForm("Arrays are store in hipe?",1);
        quest[2] = new QForm("Are String primitives?" , -1);
        quest[3] = new QForm("Is Int32 a primitive?", -1);
        quest[4] = new QForm("String are Objects" ,1);
        quest[5] = new QForm("String are Immutable" , 1);
        quest[6] = new QForm("You can initiallize this data type as Following: \n" +
                             "\"string myName=  mySame\"" , -1);
        quest[7] = new QForm("Int has 8 byte allocated", -1);
        quest[8] = new QForm("Int has 32 bits allocated?",1);
        quest[9] = new QForm("Double are bigger in size than float?" , 1);
        quest[10] = new QForm("Long and double have same size?", 1);
        quest[11] = new QForm("Character has 1 size byte." , -1);
        quest[12] = new QForm("Android has 9 Primitive data types", -1);
        quest[13] = new QForm("Android has 8 Primivite data types",1);
        quest[14] = new QForm("DisplayToast print you a messageBox" , 1);
        quest[15] = new QForm("You have XML File in Android projects." , 1);
        quest[16] = new QForm("Android has compatibility with C++" , -1);
        quest[17] = new QForm("Android has compatibility with Java", 1);
        quest[18] = new QForm("Float has 4 bytes allocated" ,1);
        quest[19] = new QForm(" Is Integer a Object?", 1);
        quest[20] = new QForm("is int a object", -1);
        quest[21] = new QForm("is The R(Resource) file Important" , 1);
        quest[22] = new QForm("Is TextView a thing in java?" , 1);
        quest[23] = new QForm("is Botton a thing in java?" , -1);
        quest[24] = new QForm("you can rename you Application in Android", 1);
    }
}
