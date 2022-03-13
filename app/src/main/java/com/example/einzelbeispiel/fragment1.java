package com.example.einzelbeispiel;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class fragment1 extends Fragment {

    private EditText editText;
    private Button button;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        init(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matrikelnummer = editText.getText().toString();
                if(matrikelnummer.length() != 8){
                    Toast.makeText(getActivity(), "Falsche Matrikelnummer", Toast.LENGTH_LONG).show();
                    textView.setText("Falsche Matrikelnummer");
                }else{

                }
            }
        });

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init(View view){
        editText = view.findViewById(R.id.editTextNumber);
        button = view.findViewById(R.id.buttonSendToServer);
        textView = view.findViewById(R.id.txtShowAnswer);
    }
}