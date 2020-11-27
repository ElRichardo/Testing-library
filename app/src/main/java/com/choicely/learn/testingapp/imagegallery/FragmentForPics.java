package com.choicely.learn.testingapp.imagegallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.choicely.learn.testingapp.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class FragmentForPics extends Fragment {

    private View view;
    private TextView imageName;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_for_pics, container, false);

        updateContent();
        return view;
    }

    private void updateContent() {
        Bundle data = getArguments();

        imageView = view.findViewById(R.id.fragment_for_pics_url_image);
        String url = data.getString("pictureUrl", "");
        Picasso.get().load(url).into(imageView);

        imageName = view.findViewById(R.id.fragment_for_pics_url_name);
        String urlName = data.getString("UrlName", "");
        imageName.setText(urlName);
    }
}
