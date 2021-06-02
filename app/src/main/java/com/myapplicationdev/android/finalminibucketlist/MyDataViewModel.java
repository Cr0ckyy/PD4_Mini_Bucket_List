package com.myapplicationdev.android.finalminibucketlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// Create a class that holds all of the data needed for the UI
public class MyDataViewModel extends AndroidViewModel {

    // TODO: create relevant objects
    com.myapplicationdev.android.finalminibucketlist.MyDataRepository repository;// creating object from the myDataDAO class
    // a series from the model class that will be under the supervision of the LiveData
    // in this case , it would be the ArrayList that contains all the data
    LiveData<List<com.myapplicationdev.android.finalminibucketlist.MyData>> imagesList;


    // TODO: create constructor that would have the necessary assignments to the repository & data
    public MyDataViewModel(@NonNull Application application) {
        super(application);

        repository = new com.myapplicationdev.android.finalminibucketlist.MyDataRepository(application);
        imagesList = repository.getAllImages();

    }

    // TODO: define relevant database CRUD operations for the class

    // TODO: create Method
    public void insert(com.myapplicationdev.android.finalminibucketlist.MyData myData) {
        repository.insert(myData);
    }

    // TODO: read Method
    public LiveData<List<com.myapplicationdev.android.finalminibucketlist.MyData>> getAllImages() {
        return imagesList;
    }

    // TODO: update Method
    public void update(com.myapplicationdev.android.finalminibucketlist.MyData myData) {
        repository.update(myData);
    }

    // TODO: delete Method
    public void delete(com.myapplicationdev.android.finalminibucketlist.MyData myData) {
        repository.delete(myData);
    }


}
