package com.todolist.exception;

/**
 * This is a general exception of the todolist app. 
 */

@SuppressWarnings("serial")
public class TodoException extends Exception {
 
 public TodoException(String s) {
        super(s);
    }
}