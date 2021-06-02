package com.myapplicationdev.android.finalminibucketlist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {


    //TODO: Declare Objects
    ImageView imageViewAddImage;
    EditText editTextAddTitle, editTextAddDescription;
    Button buttonSave;
    RatingBar ratingBarAdd;
    Bitmap selectedImage, scaledImage;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Bucket List Ideas");
        setContentView(R.layout.activity_add);

        //TODO: Declare Objects
        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        editTextAddDescription = findViewById(R.id.editTextAddDescription);
        ratingBarAdd = findViewById(R.id.ratingBarAdd);
        buttonSave = findViewById(R.id.buttonSave);

        //TODO: Add image
        imageViewAddImage.setOnClickListener(v -> {

            //TODO:  Determine whether I have been granted a particular permission in the AndroidManifest.xml
            if (ContextCompat.checkSelfPermission(AddActivity.this
                    , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Permission check result:
                // this is returned by checkPermission
                // if the permission has been granted to the given package.
                ActivityCompat.requestPermissions(AddActivity.this
                        , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                Log.d(String.valueOf(imageViewAddImage), "onCreate: test");
            } else {

                Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent, 2);

                Log.d(String.valueOf(imageIntent), "onCreate: test");
            }

        });

        //TODO: save action
        buttonSave.setOnClickListener(v -> {

            //TODO: check user inputs
            if (selectedImage == null || ratingBarAdd == null || editTextAddTitle == null || editTextAddDescription == null) {
                Toast.makeText(AddActivity.this, "Please ensure that every field is completed.", Toast.LENGTH_SHORT).show();

            } else {

                //TODO: get user inputs
                String title = editTextAddTitle.getText().toString();
                String description = editTextAddDescription.getText().toString();
                String rating = String.valueOf(ratingBarAdd.getRating());

                // Todo: This object implements an output stream in which the data is written into a byte array.
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                scaledImage = makeSmall(selectedImage, 300);

                // Todo: Specifies the known formats the can scaledImage be compressed into
                scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);

                // Todo: Creates a newly allocated byte array
                byte[] image = outputStream.toByteArray();

                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("rating", rating);
                intent.putExtra("image", image);

                // Todo:Standard activity result:
                //  the operation was successful, and the data was then transferred to the main page.
                setResult(RESULT_OK, intent);
                finish();

                //Displaying Toast with Hello Javatpoint message
                Toast.makeText(getApplicationContext(), "The data was successfully added.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // Activity Action: Pick an item from the data, returning what was selected.
            // Permission check result: this is returned by checkPermission if the permission has been granted to the given package.
            Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(imageIntent, 2);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    // Todo: This object implements for decoding images as Bitmaps or Drawables.
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), data.getData());

                    selectedImage = ImageDecoder.decodeBitmap(source);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                }

                imageViewAddImage.setImageBitmap(selectedImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap makeSmall(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float ratio = (float) width / (float) height;

        if (ratio > 1) {
            width = maxSize;
            height = (int) (width / ratio);

        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}