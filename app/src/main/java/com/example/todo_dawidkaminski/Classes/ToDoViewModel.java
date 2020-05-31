package com.example.todo_dawidkaminski.Classes;

import androidx.lifecycle.ViewModel;

public class ToDoViewModel extends ViewModel {
    private ToDo ToDo;

    public ToDo getToDo() {
        return ToDo;
    }

    public void setToDo(ToDo todo) {
        this.ToDo = todo;
    }
}
