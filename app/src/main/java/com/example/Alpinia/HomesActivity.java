package com.example.Alpinia;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;


public class HomesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Home> homesList;

    Button addHomeBttn;
    EditText newHomeName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addHomeBttn = findViewById(R.id.btnAddHome);
        newHomeName = findViewById(R.id.newHome);
        recyclerView = findViewById(R.id.homeList);
        getHomes();
        if(homesList != null){
            //Toast.makeText(this,"YAY",Toast.LENGTH_LONG ).show();
            Toast.makeText(this ,homesList.toString(),Toast.LENGTH_LONG);
            HomesAdapter myAdapter = new HomesAdapter(this,homesList);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));}
    }

    private void getHomes() {
        ApiClient.getInstance().getHomes(new Callback<Result<List<Home>>>() {
            @Override
            public void onResponse(@NonNull Call<Result<List<Home>>> call, @NonNull Response<Result<List<Home>>> response) {
                if (response.isSuccessful()) {
                    Result<List<Home>> result = response.body();
                    homesList = result.getResult();
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<List<Home>>> call, @NonNull Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private <T> void handleError(Response<T> response) {
        Error error = ApiClient.getInstance().getError(response.errorBody());
        String text = getResources().getString(R.string.error_message, error.getDescription().get(0), error.getCode());
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void handleUnexpectedError(Throwable t) {
        String LOG_TAG = "App";
        Log.e(LOG_TAG, t.toString());
    }
}
