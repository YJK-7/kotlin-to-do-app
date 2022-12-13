package com.example.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndivTaskViewModel: ViewModel() {//왜 필요한지는 잘 모르겠음
    var taskItems = MutableLiveData<MutableList<ToDo>>()

//    The primary constructor cannot contain any code.
//    Initialization code can be placed in initializer blocks
    init {
        taskItems.value = mutableListOf()
    }
// taskItems.value = [ToDo(title=asdfd, desc=dfdf, isDone=false), ToDo(title=dfsd, desc=null, isDone=false)]
    fun addItem(newTask: ToDo) {
        val list = taskItems.value //returns list of things in taskItem
        list!!.add(newTask) // add to existing list
        taskItems.postValue(list) // change taskItems list
    }
    fun getItem(position: Int): ToDo? {
        val list = taskItems.value //returns list of things in taskItem
        return list?.getOrNull(position)//check if item exists
    }

    fun updateItem(position: Int, updatedTask: ToDo){
        val list = taskItems.value //returns list of things in taskItem
        val clickItem: ToDo? = list?.getOrNull(position)//check if item exists

        if(clickItem != null) {
            list[position] = updatedTask
            taskItems.postValue(list)
//            println(taskItems.value)
        }
    }
    fun deleteItem() {
        val list = taskItems.value //returns list of things in taskItem
//        similar to array.filter. it will loop through all our list items and return
//        an instance for each item and depending on the logic will execute remove
        list?.removeAll{ toDo ->
//          if toDoItem.isDone is true remove it
            toDo.isDone
        }
        taskItems.postValue(list)
    }

}