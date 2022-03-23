package com.example.capstoneproject;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class DatabaseManagement {

    public static void establishConnection(){
        String host = "jdbc:mysql://capstonedb.cjtbc1zx88lf.us-east-1.rds.amazonaws.com/applicationData";  //jdbc:mysql://
        String username = "admin";
        String password = "";

        Connection con = null;
        try {
            con = DriverManager.getConnection(host, username, password);
            /*
            try {
                String sql;
                sql = "SELECT * FROM Account";
                PreparedStatement prest = con.prepareStatement(sql);
                ResultSet rs = prest.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2));
                }
                con.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
