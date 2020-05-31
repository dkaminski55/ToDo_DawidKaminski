package com.example.todo_dawidkaminski.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.example.todo_dawidkaminski.MainActivity;
import com.example.todo_dawidkaminski.R;

public class ToDoFragment extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
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


    private void updateUI(View view){
        itemTitle = view.findViewById(R.id.editItemTitle);
        itemDescription = view.findViewById(R.id.editItemDescription);
        itemCompleted = view.findViewById(R.id.editItemStatus);

        itemTitle.setText(todo.getTitle());
        itemDescription.setText(todo.getDescription());
        itemCompleted.setChecked(todo.getCompletionStatus());

//        Button mButtonNewTask = view.findViewById(R.id.buttonNewTask);
//        mButtonNewTask.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                mViewModel.setTask(mTask);
//
//                TaskAddFragment taskAddFragment = TaskAddFragment.newInstance();
//                assert getFragmentManager() != null;
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, taskAddFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//
//            }
//        });
//
//        Button mButtonNext = view.findViewById(R.id.buttonNext);
//        mButtonNext.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//                if ( sTaskRepository.isLast(mTask)) {
//
//                    Toast toast = Toast.makeText(
//                            getActivity(),
//                            "Hurray! end of tasks, time to plant trees and read poetry!",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//                } else {
//
//                    mTask = sTaskRepository.getNextTask(mTask);
//                    mViewModel.setTask(mTask);
//                    mTextViewTitle.setText(mTask.getTitle());
//                    mTextViewDescription.setText(mTask.getDescription());
//                    mTextViewStatus.setText(mTask.getStatus());
//
//                }
//            }
//
//        });
//
//        Button mButtonDetail = view.findViewById(R.id.buttonDetail);
//        mButtonDetail.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//                mViewModel.setTask(mTask);
//
//                TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance();
//                assert getFragmentManager() != null;
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, taskDetailFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//
//            }
//        });
//
//        Button mButtonPrev = view.findViewById(R.id.buttonPrev);
//        mButtonPrev.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//                if ( sTaskRepository.isFirst(mTask)) {
//
//                    Toast toast = Toast.makeText(
//                            getActivity(),
//                            "First task!",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//                } else {
//
//                    mTask = sTaskRepository.getPrevTask(mTask);
//                    mViewModel.setTask(mTask);
//                    mTextViewTitle.setText(mTask.getTitle());
//                    mTextViewDescription.setText(mTask.getDescription());
//                    mTextViewStatus.setText(mTask.getStatus());
//
//                }
//            }
//
//        });

    }
}
