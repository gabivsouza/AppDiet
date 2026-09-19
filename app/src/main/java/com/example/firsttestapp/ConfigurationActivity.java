package com.example.firsttestapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigurationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText;
    private RadioGroup g_sex;
    private RadioButton b_male, b_female;
    private CheckBox b_lowfat, b_lowglyc, b_glutenfree;
    private Button b_confirm, b_cancel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        editText = findViewById(R.id.textname_text);

        g_sex = findViewById(R.id.rg_sex);
        b_male = findViewById(R.id.rb_male);
        b_female = findViewById(R.id.rb_female);

        b_lowfat = findViewById(R.id.cb_lowfat);
        b_lowglyc = findViewById(R.id.cb_lowglyc);
        b_glutenfree = findViewById(R.id.cb_glutenfree);

        b_confirm = findViewById(R.id.button_confirm);
        b_confirm.setOnClickListener(this);
        b_cancel = findViewById(R.id.button_cancel);
        b_cancel.setOnClickListener(this);

        loadPreferences();
    }

    private void loadPreferences(){
        editText.setText(sharedPreferences.getString("name", "Admin"));

        String sex = sharedPreferences.getString("sex", "M");
        if (sex.equals("F")){
            b_female.setChecked(true);
        }else{
            b_male.setChecked(true);
        }

        b_lowfat.setChecked(sharedPreferences.getBoolean("low_fat", false));
        b_lowglyc.setChecked(sharedPreferences.getBoolean("low_glycemic", false));
        b_glutenfree.setChecked(sharedPreferences.getBoolean("gluten_free", false));
    }

    private void savePreferences(){
        sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putString("name", editText.getText().toString());

        int sexOptionId = g_sex.getCheckedRadioButtonId();
        if (sexOptionId==R.id.rb_female){
            sharedPreferencesEditor.putString("sex","F");
        }else{
            sharedPreferencesEditor.putString("sex","M");
        }
        sharedPreferencesEditor.putBoolean("low_fat", b_lowfat.isChecked());
        sharedPreferencesEditor.putBoolean("low_glycemic", b_lowglyc.isChecked());
        sharedPreferencesEditor.putBoolean("gluten_free", b_glutenfree.isChecked());

        sharedPreferencesEditor.commit();
    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.button_confirm){
            savePreferences();
            finish();
        } else if (view.getId()==R.id.button_cancel) {
            finish();
        }
    }
}