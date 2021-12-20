package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;


public class EditActivity extends AppCompatActivity {
    //private static final String TAG = "Edit Act";

    EditText title;
    EditText note;
    String stringTitle;
    String stringNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title= findViewById(R.id.editNotes);
        note = findViewById(R.id.editTitle);
    }

    @Override
    public void onBackPressed() {
        stringTitle=title.getText().toString();
        stringNote = note.getText().toString();

        if (stringTitle.isEmpty() && stringNote !=null){
            Intent i = new Intent(this, MainActivity.class);
           // Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("LEAVE", (dialog, id) -> startActivity(i));
            builder.setNegativeButton("CANCEL", (dialog, id) -> {});
            builder.setMessage("Note Will Not Be Saved Without Title. ");
            builder.setTitle("Leave Without Saving");
            AlertDialog dialog = builder.create();
            dialog.show();


        }else if( stringTitle.isEmpty() && stringNote.isEmpty()) {
            //startActivity(i);
            super.onBackPressed();


        }else{
            Intent i = new Intent(this, MainActivity.class);
            // Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent titleData= new Intent();
                    Note n=new Note (stringTitle,stringNote, new Date().toString());
                    titleData.putExtra("userInputs", n);
                    setResult(RESULT_OK,titleData);
                    finish();
                }
            });
            builder.setNegativeButton("LEAVE", (dialog, id) -> {startActivity(i);});
            builder.setMessage("Would you like to save " + stringNote);
            builder.setTitle("SAVE NOTE?");
            AlertDialog dialog = builder.create();
            dialog.show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate((R.menu.edit_menu), menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.save){
            stringTitle=title.getText().toString();
            stringNote = note.getText().toString();
            if (stringTitle.isEmpty()&& stringNote != null){
                Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Intent i = new Intent(this, MainActivity.class);

                builder.setPositiveButton("LEAVE", (dialog, id) -> startActivity(i));
                builder.setNegativeButton("CANCEL", (dialog, id) -> {} );

                builder.setMessage("Note Will Not Be Saved Without Title. ");
                builder.setTitle("Leave Without Saving");

                AlertDialog dialog = builder.create();
                dialog.show();

            }else{
                Intent titleData= new Intent();
                Note n=new Note (stringTitle,stringNote, new Date().toString());
                titleData.putExtra("userInputs", n);
                setResult(RESULT_OK,titleData);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    }






