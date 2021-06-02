package com.myapplicationdev.android.finalminibucketlist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
// DAO (Data Access Object) as it would manage local data for SQLite data source using objects
// DAO must always be an interface/abstract because when using room database  as only method names and required
// query operations are written

public interface MyDataDAO {
    // Todo:  An interface is a completely "abstract class" that is used
    //  to group related methods with empty bodies

    // Todo: Create Method
    @Insert
    void Insert(com.myapplicationdev.android.finalminibucketlist.MyData myData);

    // Todo: Delete Method
    @Delete
    void Delete(com.myapplicationdev.android.finalminibucketlist.MyData myData);

    // Todo: Update Method
    @Update
    void Update(com.myapplicationdev.android.finalminibucketlist.MyData myData);

    // Todo: Read Method
    @Query("SELECT * FROM my_data ORDER BY id ASC")
    LiveData<List<com.myapplicationdev.android.finalminibucketlist.MyData>> getAllData();
}
