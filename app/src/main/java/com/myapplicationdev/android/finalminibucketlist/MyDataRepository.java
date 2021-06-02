package com.myapplicationdev.android.finalminibucketlist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// Todo: To do: create a temporary storage repository for the app's data,
//  as well as relevant API methods for the UI to communicate with.
public class MyDataRepository {

    // TODO: create relevant objects
    MyDataDAO myDataDAO; // creating object from the myDataDAO class
    // a series from the model class that will be under the supervision of the LiveData
    LiveData<List<MyData>> dataList;

    // TODO: create constructor to initialize relevant objects
    public MyDataRepository(Application application) {
        com.myapplicationdev.android.finalminibucketlist.MyDataDatabase database = com.myapplicationdev.android.finalminibucketlist.MyDataDatabase.getInstance(application);
        myDataDAO = database.myDataDAO();
        dataList = myDataDAO.getAllData();
    }


    // TODO: create Method
    public void insert(MyData myData) {

      /* AsyncTask prevents the application from performing important operations such as writing files,
        reading files, and downloading from the internet while it is running,
                and then allows it to perform all of these functions synchronously in the background*/

        // To prevent the application from running slowly as a result of its ongoing operations,
        // Android OS will simply run an application in one thread, which is the main thread.

        // This main thread is used by all of the application's components,
        // including the activities and the provider.
        new InsertImageAsyncTask(myDataDAO).execute(myData);
    }

    // TODO: read Method
    public LiveData<List<MyData>> getAllImages() {
        return dataList;
    }

    // TODO: update Method
    public void update(MyData myData) {
        new UpdateImageAsyncTask(myDataDAO).execute(myData);
    }

    // TODO: delete Method
    public void delete(MyData myData) {
        new DeleteImageAsyncTask(myDataDAO).execute(myData);
    }

    public static class InsertImageAsyncTask extends AsyncTask<MyData, Void, Void> {

        MyDataDAO MyDataDAO;

        public InsertImageAsyncTask(MyDataDAO MyDataDAO) {
            this.MyDataDAO = MyDataDAO;
        }

        @Override
        protected Void doInBackground(MyData... myData) {

            MyDataDAO.Insert(myData[0]);
            return null;
        }
    }

    //    AsyncTask was intended to enable proper and easy use of the UI thread.
//        However, the most common use case was for integrating into UI,
//    and that would cause Context leaks, missed callbacks,
//    or crashes on configuration changes.
//    It also has inconsistent behavior on different versions of the platform,
//    swallows exceptions from doInBackground,
//    and does not provide much utility over using Executors directly.
//
//    AsyncTask is designed to be a helper class around
//            Thread and Handler and does not constitute a generic threading framework.
//    AsyncTasks should ideally be used for short operations (a few seconds at the most.)
//    If you need to keep threads running for long periods of time,
//    it is highly recommended you use the various APIs provided by the java.util.concurrent package
//    such as Executor, ThreadPoolExecutor and FutureTask.
    public static class DeleteImageAsyncTask extends AsyncTask<MyData, Void, Void> {

        MyDataDAO myDataDAO;

        public DeleteImageAsyncTask(MyDataDAO MyDataDAO) {
            this.myDataDAO = MyDataDAO;
        }

        @Override
        protected Void doInBackground(MyData... myData) {

            myDataDAO.Delete(myData[0]);
            return null;
        }
    }

    public static class UpdateImageAsyncTask extends AsyncTask<MyData, Void, Void> {

        MyDataDAO myDataDAO;

        public UpdateImageAsyncTask(MyDataDAO MyDataDAO) {
            this.myDataDAO = MyDataDAO;
        }

        @Override
        protected Void doInBackground(MyData... myData) {

            myDataDAO.Update(myData[0]);
            return null;
        }
    }

}
