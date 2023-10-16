package com.example.exercicio1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btEntrar;
    private Button btCadastrar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btEntrar = findViewById(R.id.btEntrar);
        btCadastrar = findViewById(R.id.btCadastrar);

        btEntrar.setOnClickListener(this);
        btCadastrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btEntrar) {
            Intent intent = new Intent(LoginActivity.this, PerfilDoadorActivity.class);

            startActivity(intent);

        } else if (view == btCadastrar) {
            Intent intent = new Intent(LoginActivity.this, CadastroUsuraioActivity.class);

            startActivity(intent);

        }
    }
}
