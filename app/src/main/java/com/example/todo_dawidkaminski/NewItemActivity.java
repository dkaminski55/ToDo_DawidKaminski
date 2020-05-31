package com.example.todo_dawidkaminski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.todo_dawidkaminski.Classes.ToDo;
import com.example.todo_dawidkaminski.Classes.ToDoRepository;

import java.util.ArrayList;
import java.util.List;

public class NewItemActivity extends AppCompatActivity {

    private ToDo newToDoItem;
    private EditText itemTitle;
    private EditText itemDescription;
    private Switch itemStatus;
    private ToDoRepository toDoRepo = ToDoRepository.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        Intent intent = getIntent();

        itemTitle = findViewById(R.id.addItemTitle);
        itemDescription = findViewById(R.id.addItemDescription);
        itemStatus = findViewById(R.id.addItemStatus);

        Button create = findViewById(R.id.btnCreateItem);
        create.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                SaveNewItem(itemTitle.getText().toString(), itemDescription.getText().toString(), itemStatus.isChecked());
            }
        });
    }

    private void SaveNewItem(String title, String description, Boolean completed){
        if(ValidNewItem(title, description)){
            toDoRepo.AddNewItem(title, description, completed);
            finish();
        }
        else{
            Toast toast = Toast.makeText(
                    this,
                    "Title and Description are required fields!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private boolean ValidNewItem(String title, String description){
        if("".equals(title)){
            return false;
        }
        else if("".equals(description)){
            return false;
        }
        return true;
    }
}
