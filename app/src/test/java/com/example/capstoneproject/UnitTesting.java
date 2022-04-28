package com.example.capstoneproject;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UnitTesting {
    @Test
    public void testIsSameDay() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(2021, 8, 23, 16, 34);
        calendar2.set(2021, 8, 23, 2, 9);

        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();

        assertEquals(true, PlannerMain.isSameDay(date1, date2), "Determine if two dates fall on same day");
    }

    @Test
    public void testArePasswordsTheSame(){
        String password1 = "testingPassword";
        String password2 = "testingPassword2";

        assertEquals(false, CreateAccount.arePasswordsTheSame(password1, password2), "Determine if two passwords match");
    }

    @Test
    public void testUserHasEnoughPoints(){
        int cost = 20;
        Login.points = 23;

        assertEquals(true, UnlockReward.userHasEnoughPoints(cost), "Determine if current user has enough points");
    }
}