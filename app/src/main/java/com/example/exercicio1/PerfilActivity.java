package com.example.exercicio1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btEditarPerfil;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        btEditarPerfil = findViewById(R.id.btEditarPerfil);

        btEditarPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btEditarPerfil) {
            Intent intent = new Intent(PerfilActivity.this, EditarPerfilActivity.class);

            startActivity(intent);
        }
    }
}
