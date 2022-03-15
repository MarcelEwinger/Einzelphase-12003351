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
import java.io.IOException;
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
    /*
    Disposable is used to dispose the subscription when an
     Observer no longer wants to listen to Observable. In android
     disposable are very useful in avoiding memory leaks.
    * */

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
        disposables.clear();//unsubscribe the Observer
    }

    private void init(View view){
        editText = view.findViewById(R.id.inputMatrikelnummerFragment1);
        button = view.findViewById(R.id.buttonSendToServer);
        textView = view.findViewById(R.id.txtShowAnswer);
    }

    /**
     * Create an Observer that listen to the Observable
     * @param matrikelnummer
     */
    void onRunSchedulerButtonClicked(String matrikelnummer) {
        disposables.add(networkObservable(matrikelnummer)
                // This tell the Observable to run the task on a background thread.
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeWith(new DisposableObserver<String>() {
                    /**
                     * When an Observable completes the emission of all the items, onComplete() will be called.
                     */
                    @Override public void onComplete() {
                        Log.d(TAG, "onComplete()");
                    }

                    /**
                     * In case of any error, onError() method will be called.
                     * @param e
                     */
                    @Override public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    /**
                     * This method will be called when Observable starts emitting the data.
                     * @param string
                     */
                    @Override public void onNext(String string) {
                        textView.setText(string);
                    }
                }));
    }

    /**
     * Create an Observable
     * @param matrikelnummer
     * @return new Observable
     */
    Observable<String> networkObservable(String matrikelnummer){
        return Observable.defer(() -> {
            try {
           socket = new Socket(server, port);//open new Socket (Communicationendpoint)
           dataOutputStream = new DataOutputStream(socket.getOutputStream());
           //A data output stream lets an application write primitive Java data types to an output stream in a portable way
           dataOutputStream.writeBytes(matrikelnummer  + "\n");
           //Writes out the string to the underlying output stream as a sequence of bytes.
           dataOutputStream.flush();
           //Flushes this data output stream. This forces any buffered output bytes to be written out to the stream.
           bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           //Reads text from a character-input stream,
           String newLine = bufferedReader.readLine();
           /*
           Reads a line of text. A line is considered to be
           terminated by any one of a line feed ('\n')
           */

           socket.close();
           dataOutputStream.close();
           bufferedReader.close();
            return Observable.just(newLine);
            } catch (IOException e) {
                return Observable.just(e.getMessage());
            }
        });
    }
}