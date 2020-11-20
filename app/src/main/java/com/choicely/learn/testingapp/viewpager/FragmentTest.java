package com.choicely.learn.testingapp.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choicely.learn.testingapp.R;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FragmentTest extends Fragment {

    public static final String POSITION_NUMBER = "position_number";
    private View view;
    TextView textField;

    @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_test, container, false);

       updateContent();
       return view;
   }

    private void updateContent() {
        Bundle data = getArguments();
        int position = data.getInt(POSITION_NUMBER, 0);
        int color;
        String text;

        switch (position) {
            default:
            case 0:
                color = Color.BLACK;
                text = "One";
                break;
            case 1:
                color = Color.BLUE;
                text = "Two";
                break;
            case 2:
                color = Color.WHITE;
                text = "Three";
                break;
            case 3:
                color = Color.RED;
                text = "Four";
                break;
            case 4:
                color = Color.GREEN;
                text = "Five";
                break;
        }
        view.setBackgroundColor(color);
        textField = view.findViewById(R.id.fragment_test_text);
        textField.setText(text);
    }

}