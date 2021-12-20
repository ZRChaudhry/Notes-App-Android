package com.example.notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView viewNote, viewTitle, viewTime;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        viewNote=itemView.findViewById(R.id.noteViewTitle);
        viewTitle=itemView.findViewById(R.id.noteViewNote);
        viewTime=itemView.findViewById(R.id.noteViewTime);




    }
}
