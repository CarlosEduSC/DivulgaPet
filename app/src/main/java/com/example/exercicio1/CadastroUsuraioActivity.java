package com.example.exercicio1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CadastroUsuraioActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtData;
    private Button btCadastro;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtData = findViewById(R.id.txtData);
        btCadastro = findViewById(R.id.btCadastro);

        txtData.setOnClickListener(this);
        btCadastro.setOnClickListener(this);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        txtData.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == txtData) {
            showDatePickerDialog();

        } else if (view == btCadastro) {
            Intent intent = new Intent(CadastroUsuraioActivity.this, LoginActivity.class);

            startActivity(intent);
        }
    }
}
