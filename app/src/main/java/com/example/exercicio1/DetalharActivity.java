package com.example.exercicio1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetalharActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgPet;
    private TextView txtNome;
    private TextView txtRaca;
    private TextView txtTipo;
    private TextView txtFaixaEtaria;
    private TextView txtSexo;
    private TextView txtVacinas;
    private TextView txtCastrado;
    private Spinner spDescricao;
    private Button btTenhoInteresse;
    private ArrayAdapter<String> adapterDescricao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar);

        String texto = "O animal está saudavel e tem as vacinas em dia. Procuro um lar carinhoso e que vai cuidar bem dele, pois não tenho mais condições de mante-lo";

        imgPet = findViewById(R.id.imgPet);
        txtNome = findViewById(R.id.txtNome);
        txtRaca = findViewById(R.id.txtRaca);
        txtTipo = findViewById(R.id.txtTipo);
        txtFaixaEtaria = findViewById(R.id.txtFaixaEtaria);
        txtSexo = findViewById(R.id.txtSexo);
        txtVacinas = findViewById(R.id.txtVacinas);
        txtCastrado = findViewById(R.id.txtCastracao);
        btTenhoInteresse = findViewById(R.id.btTenhoInteresse);

        Pet bolaDeNeve = new Pet("Bola de Neve", Pet.Tipo.Cachorro.toString(), Pet.FaixaEtaria.anos35.getDescricao(), Pet.RacaCachorro.poodle.getDescricao(), Pet.Sexo.Macho.toString());
        bolaDeNeve.setDescricao("O animal está saudavel e tem as vacinas em dia. Procuro um lar carinhoso e que vai cuidar bem dele, pois não tenho mais condições de mante-lo");
//
////        adapterDescricao = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
////        adapterDescricao.add("Descrição");
////        adapterDescricao.add(bolaDeNeve.getDescricao());
////        spDescricao.setAdapter(adapterDescricao);
////        spDescricao.setEnabled(false);
////        spDescricao.setOnClickListener(this);
//
        imgPet.setImageResource(R.drawable.maltes);
        txtNome.setText(bolaDeNeve.getNome());
        txtRaca.setText(bolaDeNeve.getRaca());
        txtTipo.setText(bolaDeNeve.getTipo());
        txtFaixaEtaria.setText(bolaDeNeve.getFaixaEtaria());
        txtSexo.setText(bolaDeNeve.getSexo());
        txtVacinas.setText("Raiva Canina, Leishmaniose");
        txtCastrado.setText("Sim");
        btTenhoInteresse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        if (view == spDescricao) {
////            if (adapterDescricao.getView(0, null, null).getVisibility() == View.VISIBLE) {
////                adapterDescricao.getView(0, null, null).setVisibility(View.GONE);
////
////            } else {
////                adapterDescricao.getView(0, null, null).setVisibility(View.VISIBLE);
////            }
//        } else if (view == btTenhoInteresse) {
            Intent intent = new Intent(DetalharActivity.this, LoginActivity.class);

            startActivity(intent);
//        }
    }
}
