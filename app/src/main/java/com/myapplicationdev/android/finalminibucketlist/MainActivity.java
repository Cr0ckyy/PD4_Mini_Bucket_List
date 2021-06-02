package com.myapplicationdev.android.finalminibucketlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // TODO: define relevant objects
    RecyclerView rv;
    FloatingActionButton fab;
    MyDataViewModel myDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: bind relevant objects
        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);

        // TODO: Creates ViewModelProvider, which will create MyDataViewModel
        //  via the given Factory and retain them
        //  in a store of the given MyDataViewModel.class
        myDataViewModel = new ViewModelProvider.
                AndroidViewModelFactory(getApplication())
                .create(MyDataViewModel.class);

        // TODO: Create an Adapter Class that binds
        //  an app-specific data set to views in a RecyclerView.
        final com.myapplicationdev.android.finalminibucketlist.MyDataAdapter adapter = new com.myapplicationdev.android.finalminibucketlist.MyDataAdapter();

        // TODO: set the MyDataAdapter
        rv.setAdapter(adapter);

        /*
        A LayoutManager is in charge of measuring and positioning item views within a RecyclerView,
        as well as deciding when to recycle item views that are no longer visible to the user.

        By changing the LayoutManager, a RecyclerView can be used to implement a
        standard vertically scrolling list, a uniform grid, staggered grids,
        horizontally scrolling collections, and more.
        There are a number of stock layout managers available to the public use.
        */

        // TODO: Create a LinearLayoutManager to manage the data
        //  display in the recycler view from top to bottom.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        // TODO: set the LinearLayoutManager
        rv.setLayoutManager(linearLayoutManager);


        // Todo: get all data method
        // Todo:The Observer method is a simple callback
        //  that can receive data from the LiveData data class.
        myDataViewModel.getAllImages().observe(MainActivity.this, adapter::setdataList);

        fab.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, com.myapplicationdev.android.finalminibucketlist.AddActivity.class);
            startActivityForResult(intent, 3);

        });

        // Todo:This method generates an ItemTouchHelper that will work with the given Callback.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                myDataViewModel.delete(adapter.getPosition(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(rv);

        adapter.setListener(myData -> {
            Intent intent = new Intent(MainActivity.this, com.myapplicationdev.android.finalminibucketlist.UpdateActivity.class);
            intent.putExtra("id", myData.getId());
            intent.putExtra("title", myData.getTitle());
            intent.putExtra("description", myData.getDescription());
            intent.putExtra("rating", myData.getRating());
            intent.putExtra("image", myData.getImage());
            startActivityForResult(intent, 4);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String rating = data.getStringExtra("rating");
            byte[] image = data.getByteArrayExtra("image");

            com.myapplicationdev.android.finalminibucketlist.MyData myData = new com.myapplicationdev.android.finalminibucketlist.MyData(title, description, rating, image);
            myDataViewModel.insert(myData);
        }

        if (requestCode == 4 && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("updateTitle");
            String description = data.getStringExtra("updateDescription");
            String rating = data.getStringExtra("updateRating");
            byte[] image = data.getByteArrayExtra("image");

            int id = data.getIntExtra("id", -1);

            com.myapplicationdev.android.finalminibucketlist.MyData myData = new com.myapplicationdev.android.finalminibucketlist.MyData(title, description, rating, image);
            myData.setId(id);
            myDataViewModel.update(myData);
        }
    }
}