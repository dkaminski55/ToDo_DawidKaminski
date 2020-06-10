package com.example.todo_dawidkaminski;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.todo_dawidkaminski.Classes.ToDoViewModel;
import com.example.todo_dawidkaminski.Fragments.ToDoFragment;

public class DetailsActivity extends AppCompatActivity {

    static final String EXTRA_TODO_ID = "todo_ID";
    private static Context mainContext;

    public static Intent newIntent(Context packageContext, int todoId){
        mainContext = packageContext;
        Intent intent = new Intent(packageContext, DetailsActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ToDoFragment.newInstance(mainContext))
                    .commitNow();
        }
    }
}
