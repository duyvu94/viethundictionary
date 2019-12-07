package com.duyvu.viethundictionary.ui.quiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.duyvu.viethundictionary.R;
import com.duyvu.viethundictionary.adapter.DictionaryAdapter;
import com.duyvu.viethundictionary.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment {

    View root;

    List<Word> wordList = DictionaryAdapter.getInstance().getItems();

    RadioGroup radioGroup;
    Button confirmBtn;
    TextView textWord;
    RadioButton b1, b2, b3, b4, correctButton;
    Boolean quizOnGoing = true;


    private List<Word> getFourRandomWords(){

        int[] indices = new int[4];
        List<Word> result = new ArrayList<>();

        for(int i = 0; i < 4; i++){

            boolean duplication = true;

            while (duplication){
                duplication = false;
                indices[i] = new Random().nextInt(wordList.size());

                for (int j = 0; j < i; j++){
                    if (indices[i] == indices[j])
                        duplication = true;
                }
            }

        }
        result.add(wordList.get(indices[0]));
        result.add(wordList.get(indices[1]));
        result.add(wordList.get(indices[2]));
        result.add(wordList.get(indices[3]));

        return result;
    }

    private void resetView(){
        List<Word> words = getFourRandomWords();

        int correctIndex = new Random().nextInt(4);

        radioGroup.clearCheck();

        b1.setTextColor(Color.BLACK);
        b2.setTextColor(Color.BLACK);
        b3.setTextColor(Color.BLACK);
        b4.setTextColor(Color.BLACK);

        b1.setText(words.get(0).description);
        b2.setText(words.get(1).description);
        b3.setText(words.get(2).description);
        b4.setText(words.get(3).description);

        switch (correctIndex){
            case 0:
                correctButton = b1; break;
            case 1:
                correctButton = b2; break;
            case 2:
                correctButton = b3; break;
            default:
                correctButton = b4; break;
        }

        textWord.setText(words.get(correctIndex).word);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_quiz, container, false);

        radioGroup = root.findViewById(R.id.radio_group);
        textWord = root.findViewById(R.id.text_word);
        confirmBtn = root.findViewById(R.id.btn_confirm);
        b1 = root.findViewById(R.id.radioA1);
        b2 = root.findViewById(R.id.radioA2);
        b3 = root.findViewById(R.id.radioA3);
        b4 = root.findViewById(R.id.radioA4);

        resetView();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == -1)
                    return;

                RadioButton checkedButton = root.findViewById(checkedRadioButtonId);

                if (quizOnGoing){
                    correctButton.setTextColor(Color.GREEN);
                    Toast toast;

                    if (checkedButton != correctButton){
                        checkedButton.setTextColor(Color.RED);
                        toast = Toast.makeText(getActivity(), getResources().getString(R.string.wrong), Toast.LENGTH_SHORT);
                        TextView toastText = toast.getView().findViewById(android.R.id.message);
                        toastText.setTextColor(Color.RED);
                    }
                    else {
                        toast = Toast.makeText(getActivity(), getResources().getString(R.string.correct), Toast.LENGTH_SHORT);
                        TextView toastText = toast.getView().findViewById(android.R.id.message);
                        toastText.setTextColor(Color.GREEN);
                    }
                    toast.show();

                    confirmBtn.setText(getResources().getString(R.string.continue_text));
                    quizOnGoing = false;
                }
                else{
                    resetView();
                    confirmBtn.setText(getResources().getString(R.string.confirm));
                    quizOnGoing = true;
                }
            }
        });

        return root;
    }
}