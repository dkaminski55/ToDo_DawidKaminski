package com.example.todo_dawidkaminski;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo_dawidkaminski.Classes.ToDo;
import com.example.todo_dawidkaminski.Classes.ToDoDueDate;
import com.example.todo_dawidkaminski.Classes.ToDoRepository;
import com.example.todo_dawidkaminski.Fragments.DatePickerFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewItemActivity extends AppCompatActivity {

    private ToDo newToDoItem;
    private EditText itemTitle;
    private EditText itemDescription;
    private Switch itemStatus;
    private TextView txtDueDate;
    private ToDoDueDate dueDate = new ToDoDueDate();
    private ToDoRepository toDoRepo = ToDoRepository.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        Intent intent = getIntent();

        itemTitle = findViewById(R.id.addItemTitle);
        itemDescription = findViewById(R.id.addItemDescription);
        itemStatus = findViewById(R.id.addItemStatus);
        txtDueDate = findViewById(R.id.txtDueDate);

        Button create = findViewById(R.id.btnCreateItem);
        create.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                SaveNewItem(itemTitle.getText().toString(), itemDescription.getText().toString(), itemStatus.isChecked(), dueDate);
            }
        });

        Button datePicker = findViewById(R.id.btnSetDate);
        datePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDatePicker(v);
            }
        });
    }

    private void SaveNewItem(String title, String description, Boolean completed, ToDoDueDate date){

        String validityStatus = ValidNewItem(title,description, date);
        if("success".equals(validityStatus)){
            toDoRepo.AddNewItem(title, description, completed, date);
            finish();
        }
        else{
            Toast toast = Toast.makeText(
                    this,
                    validityStatus,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private String ValidNewItem(String title, String description, ToDoDueDate date){
        if("".equals(title)){
            return "Please provide a title!";
        }
        else if("".equals(description)){
            return "Please provide a description!";
        }
        else if(date.day < 1){
            return "Please set the due date!";
        }
        return "success";
    }

    public void showDatePicker(View view){
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), getString(R.string.datePicker));
    }

    public void processDatePickerResult(int day, int month, int year){
        dueDate.setDueDate(day, month, year);
        txtDueDate.setText(day + "/" + month + "/" + year);
    }
}
