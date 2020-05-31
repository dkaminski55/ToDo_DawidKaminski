package com.example.todo_dawidkaminski;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_dawidkaminski.Classes.ToDo;

import java.util.ArrayList;
import java.util.LinkedList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder> {

    private final LinkedList<String> todoList;
    private LayoutInflater inflater;
    private Context _context;

    public ToDoListAdapter(Context context, LinkedList<String> todoList){
        inflater = LayoutInflater.from(context);
        _context = context;
        this.todoList = todoList;
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ToDo toDo;
        public final TextView todoItemView;
        final ToDoListAdapter mAdapter;

        public ToDoViewHolder(View itemView, ToDoListAdapter adapter){
            super(itemView);
            todoItemView = itemView.findViewById(R.id.todoItem);
            this.mAdapter = adapter;
        }

        public void bind(ToDo todo){
            toDo = todo;
        }

        @Override
        public void onClick(View v) {
            //Use this to open selected To-Do item
            Intent intent = DetailsActivity.newIntent(_context, toDo.getId());

            _context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ToDoListAdapter.ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ToDoItemView = inflater.inflate(R.layout.todo_list_item, parent, false);
        return new ToDoViewHolder(ToDoItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListAdapter.ToDoViewHolder holder, int position) {
        String currentItem = todoList.get(position);
        holder.todoItemView.setText(currentItem);
        //holder.bind(todo);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
