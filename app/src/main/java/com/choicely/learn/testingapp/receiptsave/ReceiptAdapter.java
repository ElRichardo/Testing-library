package com.choicely.learn.testingapp.receiptsave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.learn.testingapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {

    private static final String TAG = "ReceiptAdapter";

    private Context context;
    private final List<ReceiptData> list = new ArrayList<>();

    public ReceiptAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiptViewHolder(LayoutInflater.from(context).inflate(R.layout.receipt_list_row, parent, false));
    }

    /**
     * what is shown on one row
     */
    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        ReceiptData receipt = list.get(position);

        holder.textView.setText("Title: " + receipt.getTitle() + "\n" + " Date: " + receipt.getDate());

        Uri photoUri = Uri.parse(receipt.getPhotoUri());
        holder.imageView.setImageURI(photoUri);
    }

    public void add(ReceiptData receipt) { list.add(receipt); }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public class ReceiptViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.receipt_list_row_text);
            imageView = itemView.findViewById(R.id.receipt_list_row_photo);
        }
    }
}
