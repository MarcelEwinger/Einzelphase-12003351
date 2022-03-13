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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class fragment1 extends Fragment {

    private EditText editText;
    private Button button;
    private TextView textView;

    final private String server = "se2-isys.aau.at";
    final private int port = 53212;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private BufferedReader bufferedReader;

    private static final String TAG = "RxAndroidSamples";

    private final CompositeDisposable disposables = new CompositeDisposable();

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
                    onRunSchedulerButtonClicked(matrikelnummer);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private void init(View view){
        editText = view.findViewById(R.id.inputMatrikelnummerFragment1);
        button = view.findViewById(R.id.buttonSendToServer);
        textView = view.findViewById(R.id.txtShowAnswer);
    }
    void onRunSchedulerButtonClicked(String matrikelnummer) {
        disposables.add(networkObservable(matrikelnummer)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override public void onComplete() {
                        Log.d(TAG, "onComplete()");
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override public void onNext(String string) {
                        textView.setText(string);
                    }
                }));
    }

    Observable<String> networkObservable(String matrikelnummer){
        return Observable.defer(() -> {
           socket = new Socket(server, port);
           dataOutputStream = new DataOutputStream(socket.getOutputStream());
           dataOutputStream.writeBytes(matrikelnummer  + "\n");
           dataOutputStream.flush();
           bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           String newLine = bufferedReader.readLine();
            return Observable.just(newLine);
        });
    }
}