package com.example.todo_dawidkaminski.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
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

    private static final String LOG_TAG = ToDoFragment.class.getSimpleName();
    private ToDoViewModel todoViewModel;
    private ToDo todo = new ToDo();
    private ToDoRepository todoRepo = ToDoRepository.getInstance();
    private EditText itemTitle;
    private EditText itemDescription;
    private Switch itemCompleted;
    private TextView txtDueDate;

    private static Context _MainContext;
    public static ToDoFragment newInstance() {
        return new ToDoFragment();
    }

    public static ToDoFragment newInstance(Context mainContext){
        _MainContext = mainContext;
        return new ToDoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d( LOG_TAG, "onCreateView");
        //Nav first item viewer
        todoViewModel = ViewModelProviders.of(getActivity()).get(ToDoViewModel.class);
        //Recycler viewer
        ToDoViewModel MainActivityToDoViewModel = ViewModelProviders.of((MainActivity) _MainContext).get(ToDoViewModel.class);

        if(MainActivityToDoViewModel.getToDo() == null){
            if (todoViewModel.getToDo() == null){
                todo = todoRepo.GetFirstItem();
            } else {
                todo = todoViewModel.getToDo();
            }
        }
        else{
            todo = MainActivityToDoViewModel.getToDo();
            MainActivityToDoViewModel.setToDo(null);
        }

        final View view = inflater.inflate(R.layout.activity_details, container, false);
        updateUI(view);

        return view;
    }
    @Override
    public void onPause(){
        Log.d(LOG_TAG, "onPause");
        super.onPause();
        //Save changes made to the current to-do
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
        txtDueDate = view.findViewById(R.id.dueDate);

        itemTitle.setText(todo.getTitle());
        itemDescription.setText(todo.getDescription());
        itemCompleted.setChecked(todo.getCompletionStatus());
        txtDueDate.setText(todo.getDueDate().getDueDateText());

        Button ButtonNext = view.findViewById(R.id.buttonNext);
        ButtonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                next();
            }
        });

        Button ButtonPrevious = view.findViewById(R.id.buttonPrevious);
        ButtonPrevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                previous();
            }
        });

        Button ButtonDelete = view.findViewById(R.id.buttonDelete);
        ButtonDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                todoViewModel.setToDo(todo);
                DeleteFragment deleteFragment = DeleteFragment.newInstance();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, deleteFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button ButtonShare = view.findViewById(R.id.buttonShare);
        ButtonShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType(mimeType)
                    .setChooserTitle("Share to-do: ")
                    .setText(todo.getDescription())
                    .startChooser();
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
        UpdateItem();
        if (todoRepo.isLast(todo)) {
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
            txtDueDate.setText(todo.getDueDate().getDueDateText());
        }
    }

    private void previous(){
        UpdateItem();
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
            txtDueDate.setText(todo.getDueDate().getDueDateText());
        }
    }
}

