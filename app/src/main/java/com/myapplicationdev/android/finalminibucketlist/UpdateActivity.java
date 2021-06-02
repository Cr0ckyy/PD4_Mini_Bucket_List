package com.myapplicationdev.android.finalminibucketlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateActivity extends AppCompatActivity {

    // TODO: Declare objects
    ImageView imageViewUpdateImage;
    EditText editTextUpdateTitle, editTextUpdateDescription;
    Button buttonUpdate;
    RatingBar ratingBarUpdate;
    String title, description, rating;
    byte[] image;
    int id;
    Bitmap selectedImage, scaledImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Data");
        setContentView(R.layout.activity_update);

        // TODO: Bind objects
        imageViewUpdateImage = findViewById(R.id.imageViewUpdateImage);
        editTextUpdateTitle = findViewById(R.id.editTextUpdateTitle);
        editTextUpdateDescription = findViewById(R.id.editTextUpdateDescription);
        ratingBarUpdate = findViewById(R.id.ratingBarUpdate);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // TODO: Return the intent that started the activity
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        rating = getIntent().getStringExtra("rating");
        image = getIntent().getByteArrayExtra("image");

        // TODO: Sets the text to be displayed on the screens
        editTextUpdateTitle.setText(title);
        editTextUpdateDescription.setText(description);
        ratingBarUpdate.setRating(Float.parseFloat(rating));

        // TODO: Sets a Bitmap as the content of the imageViewUpdateImage.
        imageViewUpdateImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));

        imageViewUpdateImage.setOnClickListener(v -> {

            // TODO: Pick an item from the data, returning what was selected.
            Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(imageIntent, 5);
        });

        buttonUpdate.setOnClickListener(v -> updateData());
    }

    public void updateData() {
        if (id == -1) { // -1 indicates that data could not be added to database.
            Toast.makeText(UpdateActivity.this, "There is a problem!", Toast.LENGTH_SHORT).show();
        } else {
            // TODO: get user inputs
            String updateTitle = editTextUpdateTitle.getText().toString();
            String updateDescription = editTextUpdateDescription.getText().toString();
            String updateRating = String.valueOf(ratingBarUpdate.getRating());

            // TODO: set up intents
            Intent intent = new Intent();
            intent.putExtra("id", id);
            intent.putExtra("updateTitle", updateTitle);
            intent.putExtra("updateDescription", updateDescription);
            intent.putExtra("updateRating", updateRating);

            if (selectedImage == null) {
                intent.putExtra("image", image);
            } else {
                // TODO: This object implements an output stream in which the data is written into a byte array.
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                scaledImage = makeSmall(selectedImage, 300);

                // TODO:  Write a compressed version of the bitmap to the specified outputstream for the image
                scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);

                // TODO:  Creates a newly allocated byte array for the image
                byte[] image = outputStream.toByteArray();
                intent.putExtra("image", image);
            }

            // Todo:Standard activity result:
            //  the operation was successful, and the data was then transferred to the main page.
            setResult(RESULT_OK, intent);
            finish();

            //Displaying Toast with Hello Javatpoint message
            Toast.makeText(getApplicationContext(),"The data was successfully updated.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 5 && resultCode == RESULT_OK && data != null) {
            try {

                // TODO: The SDK version of the software currently running on this hardware device.
                if (Build.VERSION.SDK_INT >= 28) {

                    // TODO: an object for decoding images as Bitmaps or Drawables.
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), data.getData());

                    selectedImage = ImageDecoder.decodeBitmap(source);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                }

                imageViewUpdateImage.setImageBitmap(selectedImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // Todo: reducing the image's size
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

        // Todo: When possible, return a new bitmap that has been scaled from an existing bitmap.
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}