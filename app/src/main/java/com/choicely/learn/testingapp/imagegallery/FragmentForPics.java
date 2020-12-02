package com.choicely.learn.testingapp.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.choicely.learn.testingapp.R;
import com.choicely.learn.testingapp.db.RealmHelper;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class FragmentForPics extends Fragment {

    private View view;
    private TextView urlTextTesting;
    private TextView imageName;
    private ImageView imageView;
    private ImageButton trashCan;
    private String url;
    ImageGalleryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_for_pics, container, false);

        updateContent();
        return view;
    }

    private void updateContent() {
        Bundle data = getArguments();

        url = data.getString("pictureUrl", "");
        imageView = view.findViewById(R.id.fragment_for_pics_url_image);
        Picasso.get().load(url).into(imageView);

        urlTextTesting = view.findViewById(R.id.fragment_for_pics_url_text_testing);
        urlTextTesting.setText(url);

        String urlName = data.getString("UrlName", "");
        imageName = view.findViewById(R.id.fragment_for_pics_url_name);
        imageName.setText(urlName);

        trashCan = view.findViewById(R.id.fragment_for_pics_remove_url);

        trashCan.setOnClickListener(v -> {
            //removeImage();
        });
    }

//    private void removeImage() {
//        ShowGalleryPicsActivity showGalleryPicsActivity = new ShowGalleryPicsActivity();
//
//        RealmHelper helper = RealmHelper.getInstance();
//        Realm realm = helper.getRealm();
//
//        realm.executeTransaction(realm1 -> {
//            realm.where(ImageGalleryData.class).equalTo("pictureUrl", url).findAll().deleteAllFromRealm();
//        });
//
//        //TODO: tee tää paremmin
//        Intent intent = new Intent(getActivity(), ImageGalleryActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
}
