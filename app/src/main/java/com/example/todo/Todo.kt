package com.example.todo

data class ToDo (
    val title: String,
    val desc: String? = null,
    var isDone: Boolean = false
)