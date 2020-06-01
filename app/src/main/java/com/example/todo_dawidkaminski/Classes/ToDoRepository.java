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
        ToDoList.add(new ToDo(NextItemID(), title, description, status));
    }

    public ToDo GetFirstItem(){
        return ToDoList.get(0);
    }

    public int NextItemID(){
        //Set the item id
        return ToDoList.size() + 1;
    }

    public boolean isFirst(ToDo todo) {
        return ToDoList.indexOf(todo) == 0;
    }

    public boolean isLast(ToDo todo) {
        return ToDoList.indexOf(todo) >= ToDoList.size() - 1;
    }

    public ToDo getNext(ToDo todo) {

        int index = ToDoList.indexOf(todo);
        if (index == -1 || index == ToDoList.size()) {
            return todo;
        }
        if (index < ToDoList.size() - 1) {
            return ToDoList.get(index + 1);
        } else {
            return ToDoList.get(ToDoList.size() - 1);
        }
    }

    public ToDo getPrevious(ToDo todo) {

        int index = ToDoList.indexOf(todo);
        if ((index == -1) || (index == 0)) {
            return todo;
        }
        if (index < ToDoList.size()) {
            return ToDoList.get(index - 1);
        } else {
            return ToDoList.get(0);
        }
    }

}
