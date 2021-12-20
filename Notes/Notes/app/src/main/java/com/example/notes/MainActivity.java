package com.example.notes;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
//import android.widget.Button;
//import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{
    private static final String TAG = "Edit Act";

    private final ArrayList< Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter mAdapter;
    private ActivityResultLauncher<Intent> arl;
    private Note note;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        mAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteList.addAll(loadFile());
        size= noteList.size();
        this.setTitle("Notes: " + size);
        mAdapter.notifyDataSetChanged();
        //

        arl= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult);

        Log.d(TAG,"size "+ noteList.size());

    }

    public void handleResult(ActivityResult n){
        Intent note = n.getData();
        if (note.hasExtra("userInputs")) {
            Note nt = (Note) note.getExtras().getSerializable("userInputs");
            noteList.add(nt);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
        this.setTitle("Notes: " + size);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        saveNote();
        this.setTitle("Notes: " + size);
        mAdapter.notifyDataSetChanged();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate((R.menu.note_menu), menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "About App", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.new_note) {
            Toast.makeText(this, "New Note", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            arl.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Note> loadFile() {

        ArrayList<Note> nList = new ArrayList<>();
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String desc = jsonObject.getString("note");
                String date = jsonObject.getString("date");

                Note note = new Note(title, desc,date);
                nList.add(note);
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nList;
    }

    private void saveNote() {
        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(noteList);
            printWriter.close();
            fos.close();

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int spot = recyclerView.getChildAdapterPosition(view);
        Note n =noteList.get(spot);
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("note",n);
        noteList.remove(spot);
        mAdapter.notifyDataSetChanged();
        arl.launch(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        if (noteList.size()==0){
            return true;
        }else {
            int spot = recyclerView.getChildAdapterPosition(view);
            Note n = noteList.get(spot);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    noteList.remove(spot);
                    mAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("CANCEL", (dialog, id) -> { });
            builder.setMessage("Do you want to Delete your Note?");
            builder.setTitle("Delete Note?");

            AlertDialog dialog = builder.create();
            dialog.show();
            Log.d("main", "long" + noteList.size());
            return true;
        }
    }
}
