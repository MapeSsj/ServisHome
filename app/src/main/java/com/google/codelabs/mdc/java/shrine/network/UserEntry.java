package com.google.codelabs.mdc.java.shrine.network;

import android.content.res.Resources;
import android.util.Log;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserEntry {

    private static final String TAG = UserEntry.class.getSimpleName();
    public String user;

    public String password;

    public UserEntry(
            String user, String password) {
        this.user = user;
        this.password = password;


    }

    public static List<UserEntry> initUserEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.users);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }

        }
        String jsonUsersString = writer.toString();
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<UserEntry>>() {
        }.getType();

        return gson.fromJson(jsonUsersString, userListType);
    }

}