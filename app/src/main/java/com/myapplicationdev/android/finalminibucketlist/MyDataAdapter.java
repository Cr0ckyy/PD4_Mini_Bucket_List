package com.myapplicationdev.android.finalminibucketlist;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Todo: By deriving the Adapter class from the RecyclerView,
//  you can create an Adapter class that provides a
//  binding from an app-specific data set to views
//  displayed within a RecyclerView. Adapter class
public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.myDataHolder> {

    // Todo: create relevant objects
    List<MyData> dataList = new ArrayList<>();
    onImageClickListener listener;

    public void setListener(onImageClickListener listener) {
        this.listener = listener;
    }

    public void setdataList(List<MyData> dataList) {
        this.dataList = dataList;

        // Notify any registered observers that the data set has changed, regardless of whether the data has changed or not.
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public myDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Todo: create object from the View Class
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new myDataHolder(view);
    }

    public class myDataHolder extends RecyclerView.ViewHolder {

        // TODO: define relevant UI elements from image_card.xml
        ImageView imageView;
        TextView textViewTitle, textViewDescription, textViewRating;

        public myDataHolder(@NonNull View itemView) {
            super(itemView);

            // TODO: bind relevant UI elements from image_card.xml
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewRating = itemView.findViewById(R.id.textViewRating);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();


                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onImageClick(dataList.get(position));
                }
            });
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myDataHolder holder, int position) {

        MyData myData = dataList.get(position);

        // TODO: setting data for the main screen
        holder.textViewTitle.setText(myData.getTitle());
        holder.textViewDescription.setText(myData.getDescription());
        holder.textViewRating.setText(myData.getRating() + " star");

        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(myData.getImage()
                , 0, myData.getImage().length));

    }

    public interface onImageClickListener {
        void onImageClick(MyData myData);
    }

    public MyData getPosition(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
