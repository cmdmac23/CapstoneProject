package com.example.capstoneproject;


class Authortization{
    public String token;
}

class ApiResponse {
    public String text;
    public int code;
    public int userid;
    public int points;
}

class UserLogin{
    public String username;
    public String password;
    public String email;
}

class UpdateUserLogin{
    public int userId;
    public String newUsername;
    public String password;
    public String newPassword;
    public String newEmail;
}

class PlannerEvent{
    public int eventId;
    public int userId;
    public String title;
    public String description;
    public String group;
    public String dateTime;
    public String location;
    public String reminder = null;
    public int difficulty;
    public String fromUser;
    public String toUser;
    public int completed;
}

class PlannerEventArray{
    public PlannerEvent[] entryArray;
}

class ToDoList {
    public int listId;
    public int userId;
    public String title;
    public String group;
    public String listItem;
    public String fromUser;
    public String toUser;
    public int completed;
    public ToDoListItem[] listItemArray;
}

class ToDoListItem {
    public int listItemId;
    public int listId;
    public String itemName;
    public int difficulty;
    public int completed;
}

class ToDoListArray {
    public ToDoList[] listArray;
}

class RewardItem{
    public int userId;
    public int plantId;
    public int points;
    public String label;
}

class RewardArray{
    public RewardItem[] rewardArray;
}
