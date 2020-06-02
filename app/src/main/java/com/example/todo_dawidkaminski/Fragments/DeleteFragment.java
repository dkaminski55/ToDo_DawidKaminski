package com.example.todo_dawidkaminski.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.todo_dawidkaminski.Classes.ToDo;
import com.example.todo_dawidkaminski.Classes.ToDoRepository;
import com.example.todo_dawidkaminski.Classes.ToDoViewModel;
import com.example.todo_dawidkaminski.DetailsActivity;
import com.example.todo_dawidkaminski.MainActivity;
import com.example.todo_dawidkaminski.R;

public class DeleteFragment extends Fragment {

    private ToDoViewModel todoViewModel;
    private ToDo todo;
    private final String LOG_TAG = DeleteFragment.class.getSimpleName();
    private ToDoRepository todoRepo = ToDoRepository.getInstance();

    private TextView itemTitle;
    private TextView itemDescription;
    private TextView itemStatus;

    public static DeleteFragment newInstance() {
        return new DeleteFragment();
    }

    public DeleteFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d( LOG_TAG, "onCreateView");

        todoViewModel = ViewModelProviders.of(getActivity()).get(ToDoViewModel.class);
        todo = todoViewModel.getToDo();
        final View view = inflater.inflate(R.layout.fragment_delete, container, false);
        updateUI(view);
        return view;
    }


    private void updateUI(View view){
        itemTitle = view.findViewById(R.id.txtTitle);
        itemDescription = view.findViewById(R.id.txtDescription);
        itemStatus = view.findViewById(R.id.txtStatus);

        itemTitle.setText(todo.getTitle());
        itemDescription.setText(todo.getDescription());
        itemStatus.setText(todo.getCompletionStatus() == true ? "Completed" : "Incomplete");

        Button buttonDelete = view.findViewById(R.id.buttonDeleteConfirm);
        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Delete To-Do");
                builder.setMessage("Are you sure you want to delete the '" + todo.getTitle() + "' To-Do?");
                builder.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(todoRepo.GetAll().size() == 1){
                                    //return to MainActivity
                                    todoRepo.delete(todo);
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    //return to details page an navigate to appropriate to-do
                                    if(todoRepo.isFirst(todo)){
                                        todoViewModel.setToDo(todoRepo.getNext(todo));
                                    }
                                    else if(todoRepo.isLast(todo)){
                                        todoViewModel.setToDo(todoRepo.getPrevious(todo));
                                    }
                                    else{
                                        //deleted item is somewhere in between first and last and therefore we should show the next
                                        todoViewModel.setToDo(todoRepo.getNext(todo));
                                    }
                                    todoRepo.delete(todo);

                                    ToDoFragment todoFragment = ToDoFragment.newInstance();
                                    assert getFragmentManager() != null;
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.container, todoFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
