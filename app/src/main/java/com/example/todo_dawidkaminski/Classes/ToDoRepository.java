package com.example.todo_dawidkaminski.Classes;

import java.util.ArrayList;
import java.util.Collections;

public class ToDoRepository {

    private static ToDoRepository toDoRepo;
    public ArrayList<ToDo> ToDoList = new ArrayList<>();

    public static ToDoRepository getInstance() {
        if (toDoRepo == null) {
            toDoRepo = new ToDoRepository();
        }
        return toDoRepo;
    }

    public ArrayList<ToDo> GetAll(){
        return ToDoList;
    }

    public void AddNewItem(String title, String description, Boolean status){
        ToDoList.add(new ToDo(1, title, description,status));
    }

    public ToDo GetFirstItem(){
        return ToDoList.get(0);
    }
}
