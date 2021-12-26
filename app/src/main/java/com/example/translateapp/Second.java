package com.example.translateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.translateapp.model.Model;
import com.example.translateapp.request.ServiceGenerator;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Second extends AppCompatActivity {
    private PowerSpinnerView powerSpinnerView1;
    private PowerSpinnerView powerSpinnerView2;
    private Button btnTranslate;
    private String fromLanguage = "", toLanguage = "";
    private EditText edtSearch;
    private List<String> spinnerItems = new ArrayList<>();
    private ImageView imgSwap;
    private ConstraintLayout constLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
        setListeners();
    }
    private void init() {
        spinnerItems.add("English");
        spinnerItems.add("Arabic");
        spinnerItems.add("Persian");
        powerSpinnerView1 = findViewById(R.id.spinner1);
        powerSpinnerView2 = findViewById(R.id.spinner2);
        edtSearch = findViewById(R.id.edtSearch);

        powerSpinnerView1.setItems(spinnerItems);
        powerSpinnerView2.setItems(spinnerItems);
        btnTranslate = findViewById(R.id.btnTranslate);
        imgSwap = findViewById(R.id.imgSwap);
        constLayout = findViewById(R.id.constLayout);
    }
    private void setListeners() {
        powerSpinnerView1.setOnClickListener(view -> powerSpinnerView1.show());
        powerSpinnerView2.setOnClickListener(view -> powerSpinnerView2.show());
        powerSpinnerView1.setOnSpinnerItemSelectedListener(
                (OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                    fromLanguage = newItem;

                });
        powerSpinnerView2.setOnSpinnerItemSelectedListener(
                (OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                    toLanguage = newItem;
                    if (fromLanguage.equals("Persian") && toLanguage.equals("English")){
                        makeToast("dehkhoda");
                    }
                });
        imgSwap.setOnClickListener(view -> {
            powerSpinnerView1.dismiss();
            powerSpinnerView2.dismiss();

            String temp;
            temp = fromLanguage;
            fromLanguage = toLanguage;
            toLanguage = temp;
            System.out.println(fromLanguage +"__1111111__"+toLanguage);
            powerSpinnerView1.notifyItemSelected(spinnerItems.indexOf(fromLanguage) , fromLanguage);
            powerSpinnerView2.notifyItemSelected(spinnerItems.indexOf(toLanguage) , toLanguage);
            System.out.println(fromLanguage +"__2222222__"+toLanguage);
        });
        edtSearch.setOnClickListener(view -> {
            powerSpinnerView1.dismiss();
            powerSpinnerView2.dismiss();
        });
        constLayout.setOnClickListener(view -> {
            powerSpinnerView1.dismiss();
            powerSpinnerView2.dismiss();
        });
        btnTranslate.setOnClickListener(view -> {
            if (fromLanguage == null || toLanguage == null || edtSearch.getText().toString().trim().equals("")) {
                makeToast("fill the blanks");
                return;
            }
            Intent third = new Intent(Second.this,Third.class);
            getDataObservable().enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    int code = response.body().getResponse().getCode();
                    if (code == 200 && response.body().getData().getResults() != null && response.body().getData().getResults().size() != 0) {
                        String text = response.body().getData().getResults().get(0).getText();
                        String titleEn = response.body().getData().getResults().get(0).getTitle_en();
                        String source = response.body().getData().getResults().get(0).getSource();
                        third.putExtra("text", text);
                        third.putExtra("title", edtSearch.getText().toString());
                        third.putExtra("titleEn", titleEn);
                        third.putExtra("source", source);
                        startActivity(third);
                    } else {
                        makeToast("wrong request");
                    }
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    makeToast("wrong request");
                    call.cancel();
                }
            });
        });
    }

    private String findDB() {
        switch (fromLanguage) {
            case "Persian": {
                return setToLanguage("fa");
            }
            case "Arabic": {
                return setToLanguage("ar");
            }
            case "English": {
                return setToLanguage("en");
            }
        }
        return "";
    }

    private String setToLanguage(String from) {
        switch (toLanguage) {
            case "Persian": {
                return from+"2fa";
            }
            case "Arabic": {
                return from + "2ar";
            }
            case "English": {
                return  "dehkhoda";
            }
        }
        return null;
    }

    private Call<Model> getDataObservable() {
        return ServiceGenerator.getRequestApi().getTranslation("68352.1Cs6T7Ll3VWDrPPFO0dn1FPZVx1bQZNrbG8PqZcB", edtSearch.getText().toString().trim(), "exact", findDB());
    }

    private void makeToast(String st) {
        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
    }
        }