package com.example.exercicio1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PerfilDoadorActivity extends AppCompatActivity {

    private ImageView imgPet;
    private TextView txtNomePet;
    private TextView txtTipo;
    private TextView txtRaca;
    private TextView txtFaixaEtaria;
    private TextView txtSexo;
    private TextView txtNomeDoador;
    private TextView txtDataNascimento;
    private TextView txtTelefone;
    private TextView txtEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_doador);

        imgPet = findViewById(R.id.imgPet);
        txtNomePet = findViewById(R.id.txtNomePet);
        txtTipo = findViewById(R.id.txtTipo);
        txtRaca = findViewById(R.id.txtRaca);
        txtFaixaEtaria = findViewById(R.id.txtFaixaEtaria);
        txtSexo = findViewById(R.id.txtSexo);
        txtNomeDoador = findViewById(R.id.txtNomeDoador);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);

        Pet bolaDeNeve = new Pet("Bola de Neve", Pet.Tipo.Cachorro.toString(), Pet.FaixaEtaria.anos35.getDescricao(), Pet.RacaCachorro.poodle.getDescricao(), Pet.Sexo.Macho.toString());
        Usuario carlos = new Usuario("Carlos","07/02/2001","045999232323","carlos@gmail.com","1234");

        imgPet.setImageResource(R.drawable.maltes);
        txtNomePet.setText(bolaDeNeve.getNome());
        txtTipo.setText(bolaDeNeve.getTipo());
        txtRaca.setText(bolaDeNeve.getRaca());
        txtFaixaEtaria.setText(bolaDeNeve.getFaixaEtaria());
        txtSexo.setText(bolaDeNeve.getSexo());
        txtNomeDoador.setText(carlos.getNome());
        txtDataNascimento.setText(carlos.getDataNascimento());
        txtTelefone.setText(carlos.getTelefone());
        txtEmail.setText(carlos.getEmail());
    }
}
