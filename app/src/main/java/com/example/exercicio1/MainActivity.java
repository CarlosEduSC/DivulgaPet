package com.example.exercicio1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private PopupMenu menu;
    private ImageView imgMenu;
    private Spinner spTipo;
    private Spinner spRaca;
    private Spinner spFaixaEtaria;
    private Spinner spSexo;
    private ListView listaPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgMenu = findViewById(R.id.imgMenu);
        spTipo = findViewById(R.id.spTipo);
        spRaca = findViewById(R.id.spRaca);
        spFaixaEtaria = findViewById(R.id.spFaixaEtaria);
        spSexo = findViewById(R.id.spSexo);
        listaPets = findViewById(R.id.lsPets);

        Pet bolaDeNeve = new Pet("Bola de Neve", Pet.Tipo.Cachorro.toString(), Pet.FaixaEtaria.anos35.getDescricao(), Pet.RacaCachorro.poodle.getDescricao(), Pet.Sexo.Macho.toString());

        List<Pet> pets = new ArrayList<Pet>();

        pets.add(bolaDeNeve);

        menu.setOnMenuItemClickListener(this);
        imgMenu.setOnClickListener(this);
        PetsAdapter petsAdapter = new PetsAdapter(this, pets);
        listaPets.setAdapter(petsAdapter);

        listaPets.setOnItemClickListener(this);

        opcoesSpinners();
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_options, menu.getMenu());

        menu.show();
    }

    private void opcoesSpinners() {
        String[] opcoes_tipo = getResources().getStringArray(R.array.tipo);
        String[] opcoes_raca_cachorro = getResources().getStringArray(R.array.raca_cachorro);
        String[] opcoes_raca_gato = getResources().getStringArray(R.array.raca_gato);
        String[] opcoes_faixa_etaria = getResources().getStringArray(R.array.faixa_etaria);
        String[] opcoes_sexo = getResources().getStringArray(R.array.sexo);


        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_tipo);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapterTipo);


        ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_raca_cachorro);
        adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRaca.setAdapter(adapterRaca);

        ArrayAdapter<String> adapterFaixaEtaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_faixa_etaria);
        adapterFaixaEtaria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFaixaEtaria.setAdapter(adapterFaixaEtaria);

        ArrayAdapter<String> adapterSexo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_sexo);
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapterSexo);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(MainActivity.this, DetalharActivity.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == imgMenu) {
            OpenMenu(view);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(MainActivity.this, PerfilActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(MainActivity.this, CadastroPetAcitivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);

            startActivity(intent);
        }
        return false;
    }
}