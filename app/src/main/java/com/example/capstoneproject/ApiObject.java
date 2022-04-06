package com.example.capstoneproject;


import java.util.Calendar;
import java.util.Date;

class Authortization{
    public String token;
}

class ApiResponse {
    public String text;
    public int code;
    public int userid;
}

class UserLogin{
    public String username;
    public String password;
    public String email;
}

class PlannerEvent{
    public int eventId;
    public int userId;
    public String title;
    public String description;
    public Calendar dateTime;
    public String location;
    public Calendar reminder;
    public int difficulty;
    public String fromUser;
    public String toUser;
    public Boolean completed;
}
