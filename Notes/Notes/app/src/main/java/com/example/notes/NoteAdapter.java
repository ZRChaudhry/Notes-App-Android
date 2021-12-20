package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteAdapter  extends RecyclerView.Adapter<NoteViewHolder>{

    private List<Note>nlist;
    private MainActivity mainAct;

    NoteAdapter(List<Note> nList, MainActivity ma){
        this.nlist= nList;
        mainAct=ma;


    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note n=nlist.get(position);
        holder.viewTitle.setText(n.getTitle());
        holder.viewNote.setText(n.getNote());
        holder.viewTime.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return nlist.size();
    }
}
