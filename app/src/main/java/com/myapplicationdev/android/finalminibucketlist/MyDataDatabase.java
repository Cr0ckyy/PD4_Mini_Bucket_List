package com.myapplicationdev.android.finalminibucketlist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MyData.class, version = 2)
// Abstract class: is a restricted class that cannot be used to create objects
// (to access it, it must be inherited from another class).
public abstract class MyDataDatabase extends RoomDatabase {

    static MyDataDatabase instance;

    abstract MyDataDAO myDataDAO();

    // Abstract method: can only be used in an abstract class, and it does not have a body.
    // The body is provided by the subclass (inherited from).
    public static synchronized MyDataDatabase getInstance(Context context) {
        if (instance == null) {

            // necessary database will be created if theres no database
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , MyDataDatabase.class, "my_data_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
