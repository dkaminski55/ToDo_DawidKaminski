package com.example.todo_dawidkaminski;

import android.content.Intent;
import android.os.Bundle;

import com.example.todo_dawidkaminski.Classes.ToDo;
import com.example.todo_dawidkaminski.Classes.ToDoRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ToDoListAdapter adapter;
    private ToDoRepository toDoRepository = ToDoRepository.getInstance();
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UpdateUI();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewIntent = new Intent(MainActivity.this, NewItemActivity.class);
                MainActivity.this.startActivity(addNewIntent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
        UpdateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ArrayList<ToDo> ToDoList = toDoRepository.GetAll();
        if(ToDoList.size() > 0){
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
                return true;
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "There are currently no To-Do's.", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    public void UpdateUI(){
        ArrayList<ToDo> ToDoList = toDoRepository.GetAll();
        LinkedList<String> ToDoTitleList = new LinkedList<>();
        for (int i = 0;i < ToDoList.size();i++){
            ToDoTitleList.add(ToDoList.get(i).getTitle());
        }
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new ToDoListAdapter(this, ToDoTitleList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
