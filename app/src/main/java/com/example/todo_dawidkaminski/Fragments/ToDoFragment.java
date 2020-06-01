package com.example.todo_dawidkaminski.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


import com.example.todo_dawidkaminski.Classes.ToDo;
import com.example.todo_dawidkaminski.Classes.ToDoRepository;
import com.example.todo_dawidkaminski.Classes.ToDoViewModel;
import com.example.todo_dawidkaminski.DetailsActivity;
import com.example.todo_dawidkaminski.Listeners.OnSwipeTouchListener;
import com.example.todo_dawidkaminski.MainActivity;
import com.example.todo_dawidkaminski.R;

public class ToDoFragment extends Fragment {

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    private ToDoViewModel todoViewModel;
    private ToDo todo = new ToDo();
    private ToDoRepository todoRepo = ToDoRepository.getInstance();

    private EditText itemTitle;
    private EditText itemDescription;
    private Switch itemCompleted;

    public static ToDoFragment newInstance() {
        return new ToDoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d( LOG_TAG, "onCreateView");

        todoViewModel = ViewModelProviders.of(getActivity()).get(ToDoViewModel.class);

        if (todoViewModel.getToDo() == null){
            todo = todoRepo.GetFirstItem();
        } else {
            todo = todoViewModel.getToDo();
        }

        final View view = inflater.inflate(R.layout.activity_details, container, false);
        updateUI(view);
        return view;
    }

    @Override
    public void onPause(){
        Log.d(LOG_TAG, "onPause");
        super.onPause();
        UpdateItem();
    }

    public void UpdateItem(){
        todo.setTitle(itemTitle.getText().toString());
        todo.setDescription(itemDescription.getText().toString());
        todo.setCompletionStatus(itemCompleted.isChecked());
    }


    private void updateUI(View view){
        itemTitle = view.findViewById(R.id.editItemTitle);
        itemDescription = view.findViewById(R.id.editItemDescription);
        itemCompleted = view.findViewById(R.id.editItemStatus);

        itemTitle.setText(todo.getTitle());
        itemDescription.setText(todo.getDescription());
        itemCompleted.setChecked(todo.getCompletionStatus());


        Button mButtonNext = view.findViewById(R.id.buttonNext);
        mButtonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UpdateItem();
                next();
            }

        });

        Button mButtonPrev = view.findViewById(R.id.buttonPrevious);
        mButtonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                UpdateItem();
                previous();
            }
        });

        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()){
            public void onSwipeRight(){
                previous();
            }

            public void onSwipeLeft(){
                next();
            }
        });
    }

    private void next(){
        if ( todoRepo.isLast(todo)) {
            Toast toast = Toast.makeText(
                    getActivity(),
                    "This is the last To-Do!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            todo = todoRepo.getNext(todo);
            todoViewModel.setToDo(todo);
            itemTitle.setText(todo.getTitle());
            itemDescription.setText(todo.getDescription());
            itemCompleted.setChecked(todo.getCompletionStatus());
        }
    }

    private void previous(){
        if ( todoRepo.isFirst(todo)) {
            Toast toast = Toast.makeText(
                    getActivity(),
                    "This is the first To-Do!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            todo = todoRepo.getPrevious(todo);
            todoViewModel.setToDo(todo);
            itemTitle.setText(todo.getTitle());
            itemDescription.setText(todo.getDescription());
            itemCompleted.setChecked(todo.getCompletionStatus());
        }
    }
}

