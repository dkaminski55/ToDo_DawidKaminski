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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_dawidkaminski.Classes.ToDo;
import com.example.todo_dawidkaminski.Classes.ToDoDueDate;
import com.example.todo_dawidkaminski.Classes.ToDoRepository;
import com.example.todo_dawidkaminski.Classes.ToDoViewModel;

import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder> {

    private final ArrayList<ToDo> todoList;
    private LayoutInflater inflater;
    private Context _context;

    public ToDoListAdapter(Context context, ArrayList<ToDo> todoList){
        inflater = LayoutInflater.from(context);
        _context = context;
        this.todoList = todoList;
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ToDo todo;
        public final TextView todoItemView;
        public final TextView todoItemDueDateView;
        final ToDoListAdapter mAdapter;
        private ToDoViewModel todoViewModel = ViewModelProviders.of((MainActivity)_context).get(ToDoViewModel.class);

        public ToDoViewHolder(View itemView, ToDoListAdapter adapter){
            super(itemView);
            todoItemView = itemView.findViewById(R.id.todoItem);
            todoItemDueDateView = itemView.findViewById(R.id.todo_due_date);
            itemView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
            int selectedIndex = getLayoutPosition();
            ToDo todo = todoList.get(selectedIndex);
            todoViewModel.setToDo(todo);
            mAdapter.notifyDataSetChanged();
            //Use this to open selected To-Do item
            Intent intent = DetailsActivity.newIntent(_context, todo.getId());
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
        ToDo currentItem = todoList.get(position);
        holder.todoItemView.setText(currentItem.getTitle());
        ToDoDueDate dueDate = currentItem.getDueDate();
        holder.todoItemDueDateView.setText(dueDate.getDueDateText());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
