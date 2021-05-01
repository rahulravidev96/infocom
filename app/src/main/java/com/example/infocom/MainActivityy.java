package com.example.infocom;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivityy  extends AppCompatActivity {

    private EditText emailEdt, mobileEdt;
    private Button submitBtn;
    private RecyclerView ListRV;
    private Adapter adapter;
    private ArrayList<UserModal> userModalArrayList;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEdt = findViewById(R.id.idEdtEmail);
        mobileEdt = findViewById(R.id.idEdtMobile);
        submitBtn = findViewById(R.id.idBtnAdd);
        ListRV = findViewById(R.id.idRVList);

        loadData();

        buildRecyclerView();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    alreadyExist = "";
                    if(!userModalArrayList.isEmpty()){
                        for(int i = 0; i<userModalArrayList.size(); i++){
                            if(emailEdt.getText().toString().trim().equalsIgnoreCase(userModalArrayList.get(i).getEmail())){
                                alreadyExist = "mailExist";
                                if(mobileEdt.getText().toString().trim().equalsIgnoreCase(userModalArrayList.get(i).getNumber())){
                                    alreadyExist = "bothExist";
                                }
                            }else if(mobileEdt.getText().toString().trim().equalsIgnoreCase(userModalArrayList.get(i).getNumber())){
                                alreadyExist = "numExist";
                            }
                        }
                    }

                    // check Already Exist

                    if(alreadyExist.equalsIgnoreCase("")){
                        saveData();
                    }else if(alreadyExist.equalsIgnoreCase("mailExist")){
                        emailEdt.setError("Email Already Exist");
                        emailEdt.requestFocus();
                    }else if(alreadyExist.equalsIgnoreCase("bothExist")){
                        emailEdt.setError("Email & Number Already Exist");
                        emailEdt.requestFocus();
                    }else if(alreadyExist.equalsIgnoreCase("numExist")){
                        mobileEdt.setError("Number Already Exist");
                        mobileEdt.requestFocus();
                    }
                }

            }
        });
    }

    private boolean validation(){
        if(emailEdt.getText().toString().trim().equalsIgnoreCase("")){
            emailEdt.setError("This field is required");
            emailEdt.requestFocus();
            return false;
        }else if(!emailEdt.getText().toString().trim().matches(emailPattern)){
            emailEdt.setError("Please enter valid email");
            emailEdt.requestFocus();
            return false;
        }else if(mobileEdt.getText().toString().trim().equalsIgnoreCase("")){
            mobileEdt.setError("This field is required");
            mobileEdt.requestFocus();
            return false;
        }else if(mobileEdt.getText().toString().trim().length() < 10){
            mobileEdt.setError("Please enter valid number");
            mobileEdt.requestFocus();
            return false;
        }
        return true;
    }

    String alreadyExist = "";


    private void buildRecyclerView() {

        adapter = new Adapter(userModalArrayList, MainActivityy.this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListRV.setHasFixedSize(true);
        ListRV.setLayoutManager(manager);
        ListRV.setAdapter(adapter);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("lists", null);
        Type type = new TypeToken<ArrayList<UserModal>>() {}.getType();

        userModalArrayList = gson.fromJson(json, type);
        if (userModalArrayList == null) {
            userModalArrayList = new ArrayList<>();
        }
    }

    private void saveData() {

        userModalArrayList.add(new UserModal(emailEdt.getText().toString().trim(), mobileEdt.getText().toString().trim()));
        adapter.notifyItemInserted(userModalArrayList.size());

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(userModalArrayList);
        editor.putString("lists", json);
        editor.commit();
        editor.apply();


    }
}
