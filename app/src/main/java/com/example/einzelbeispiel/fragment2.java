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
                String matrikelnummer = editMatrikelnummer.getText().toString();//get Input from EditText
                if(matrikelnummer.length() != 8){//check if Matrikelnummer is valid
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

    /**
     * Initialize button, editMatrikelnummer and textView
     * @param view
     */
    private void init(View view){
        button = view.findViewById(R.id.btnCalculate);
        editMatrikelnummer = view.findViewById(R.id.inputMatrikelnummerFragment2);
        textView = view.findViewById(R.id.textViewCalculated);
    }

    /**
     * Every second number is switch with ASCII chars
     * @param string
     */
    private void calc(String string){
        StringBuilder stringBuilder = new StringBuilder();//new StringBuilder

        for(int i = 0; i < string.length(); i++){//loop through String
            if(i % 2 != 0){//every second number
                /*
                int to Char --> Typecasting
                string.chatAt(i)--> char at position i
                String.valueOf --> convert different Types into string
                Integer.parseInt --> convert string into Integer
                Why 96 because the Alphabet (lowerCase) starts with 96
                * */

                int temp = Integer.parseInt(String.valueOf(string.charAt(i))) + 96;
                char temp2 = (char)temp;//parse int into char
                stringBuilder.append(temp2);//apendString
            }else{
                stringBuilder.append(string.charAt(i));//apendString
            }
        }
        textView.setText(stringBuilder.toString());//set text (stringbuilder) into textView
    }
}