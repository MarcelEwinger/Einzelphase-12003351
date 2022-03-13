package com.example.einzelbeispiel;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class fragment2 extends Fragment {


    private Button button;
    private EditText editMatrikelnummer;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        init(view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Second Fragment", Toast.LENGTH_LONG).show();
                String matrikelnummer = editMatrikelnummer.getText().toString();
                if(matrikelnummer.length() != 8){
                    Toast.makeText(getActivity(), "Falsche Matrikelnummer", Toast.LENGTH_LONG).show();
                    textView.setText("Falsche Matrikelnummer");
                }else{
                    calc(matrikelnummer);
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
        button = view.findViewById(R.id.btnMod);
        editMatrikelnummer = view.findViewById(R.id.editTextNumberFragment2);
        textView = view.findViewById(R.id.textViewCalculated);
    }

    private void calc(String string){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < string.length(); i++){
            if(i % 2 != 0){
                int temp = Integer.parseInt(String.valueOf(string.charAt(i))) + 96;
                char temp2 = (char)temp;
                stringBuilder.append(temp2);
            }else{
                stringBuilder.append(string.charAt(i));
            }
        }
        //Toast.makeText(getActivity(), stringBuilder.toString(), Toast.LENGTH_LONG).show();
        textView.setText(stringBuilder.toString());


    }
}