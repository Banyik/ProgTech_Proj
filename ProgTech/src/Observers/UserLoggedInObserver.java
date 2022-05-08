package Observers;

import BaseClasses.Users;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserLoggedInObserver extends Observer{
    public UserLoggedInObserver (Users users){
        this.users = users;
    }
    @Override
    public void update() {
        File myObj = new File("log.txt");
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        FileWriter fW = null;
        try {
            fW = new FileWriter("log.txt",true);
            fW.write("Logged in: " + users.toString() + formatter.format(date)+"\n");
            fW.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Logged in: " + users.toString() + formatter.format(date));
    }
}