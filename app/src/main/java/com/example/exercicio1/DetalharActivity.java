package com.example.exercicio1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetalharActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private PopupMenu menu;
    private TextView txtDescricao;
    private ImageView imgPet;
    private TextView txtNome;
    private TextView txtRaca;
    private TextView txtTipo;
    private TextView txtFaixaEtaria;
    private TextView txtSexo;
    private TextView txtVacinas;
    private TextView txtCastrado;
    private Button btTenhoInteresse;
    private ArrayAdapter<String> adapterDescricao;

    String texto = "O animal está saudavel e tem as vacinas em dia. Procuro um lar carinhoso e que vai cuidar bem dele, pois não tenho mais condições de mante-lo";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar);

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);
        txtDescricao = findViewById(R.id.txtDescricao);
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

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        txtDescricao.setOnClickListener(this);
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

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_options, menu.getMenu());

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            onBackPressed();

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == txtDescricao) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetalharActivity.this);
            builder.setMessage(texto).setCancelable(true);

            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.filtro_background);
            alertDialog.show();

            TextView alertTextView = alertDialog.findViewById(android.R.id.message);
            alertTextView.setMovementMethod(LinkMovementMethod.getInstance());

        } else if (view == btTenhoInteresse) {
            Intent intent = new Intent(DetalharActivity.this, LoginActivity.class);

            startActivity(intent);
        }
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(DetalharActivity.this, PerfilActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(DetalharActivity.this, CadastroPetAcitivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(DetalharActivity.this, MainActivity.class);

            startActivity(intent);
        }
        return false;
    }
}
