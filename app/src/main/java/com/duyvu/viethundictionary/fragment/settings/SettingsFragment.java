package com.duyvu.viethundictionary.fragment.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.duyvu.viethundictionary.R;

public class SettingsFragment extends Fragment {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String language;
    String chosenLanguage;

    Button applyButton;
    RadioGroup radioGroup;

    private String convertToLanguage(int resource){
        switch (resource) {
            case R.id.radioEn:
                return "en";
            case R.id.radioHu:
                return "hu";
            default:
                return "vi";
        }
    }

    private int convertToResource(String language){
        switch (language) {
            case "en":
                return R.id.radioEn;
            case "hu":
                return R.id.radioHu;
            default:
                return R.id.radioVi;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        language = sharedPref.getString("language", null);
        chosenLanguage = language;

        radioGroup = root.findViewById(R.id.radioGroup);
        radioGroup.check(convertToResource(language));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                chosenLanguage = convertToLanguage(checkedId);
            }
        });


        applyButton = root.findViewById(R.id.btnApply);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenLanguage != language){
                    editor.putString("language", chosenLanguage);
                    editor.apply();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
                    getActivity().finish();
                    getActivity().startActivity(intent);
                }

            }
        });
        return root;
    }
}