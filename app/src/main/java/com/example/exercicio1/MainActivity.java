package com.example.exercicio1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.google.firebase.ktx.Firebase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener {
    private PopupMenu menu;
    private ImageView imgMenu;
    private Spinner spTipo;
    private Spinner spRaca;
    private Spinner spFaixaEtaria;
    private Spinner spSexo;
    private ListView listaPets;
    private String[] opcoes_tipo;
    private String[] opcoes_raca_cachorro;
    private String[] opcoes_raca_gato;
    private String[] opcoes_faixa_etaria;
    private String[] opcoes_sexo;
    private PetDAO petDAO;

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

        opcoes_tipo = getResources().getStringArray(R.array.tipo);
        opcoes_raca_cachorro = getResources().getStringArray(R.array.raca_cachorro);
        opcoes_raca_gato = getResources().getStringArray(R.array.raca_gato);
        opcoes_faixa_etaria = getResources().getStringArray(R.array.faixa_etaria);
        opcoes_sexo = getResources().getStringArray(R.array.sexo);

        petDAO = new PetDAO();

        Pet pet1 = new Pet("Bola de Neve", Pet.Tipo.Cachorro.toString(), Pet.FaixaEtaria.anos35.getDescricao(), Pet.RacaCachorro.poodle.getDescricao(), Pet.Sexo.Macho.toString());

        petDAO.addPet(pet1, this);

        List<Pet> pets = new ArrayList<Pet>();
        pets.add(pet1);

        imgMenu.setOnClickListener(this);
        PetsAdapter petsAdapter = new PetsAdapter(this, pets);
        listaPets.setAdapter(petsAdapter);

        listaPets.setOnItemClickListener(this);

        Spinners();

        spTipo.setOnItemSelectedListener(this);
        spRaca.setOnItemSelectedListener(this);
        spFaixaEtaria.setOnItemSelectedListener(this);
        spSexo.setOnItemSelectedListener(this);
    }

    private void Spinners() {
            ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_tipo);
            adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTipo.setAdapter(adapterTipo);

            ArrayList<String> raca = new ArrayList<String>();
            raca.add("Raça");
            raca.add("Selecione o tipo do animal primeiro!");

            ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, raca);
            adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRaca.setAdapter(adapterRaca);

            ArrayAdapter<String> adapterFaixaEtaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_faixa_etaria);
            adapterFaixaEtaria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spFaixaEtaria.setAdapter(adapterFaixaEtaria);

            ArrayAdapter<String> adapterSexo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_sexo);
            adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSexo.setAdapter(adapterSexo);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_options, menu.getMenu());

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (view == listaPets) {
            Intent intent = new Intent(MainActivity.this, DetalharActivity.class);

            startActivity(intent);

        } else if (view == spTipo) {
            if (spTipo.getSelectedItem() == opcoes_tipo[1]) {
                ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_raca_cachorro);
                adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRaca.setAdapter(adapterRaca);

            } else if (spTipo.getSelectedItem() == opcoes_tipo[2]) {
                ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_raca_gato);
                adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRaca.setAdapter(adapterRaca);

            }
        }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id ==R.id.spTipo) {
            if (spTipo.getSelectedItem() == opcoes_tipo[1]) {
                ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_raca_cachorro);
                adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRaca.setAdapter(adapterRaca);

            } else if (spTipo.getSelectedItem() == opcoes_tipo[2]) {
                ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_raca_gato);
                adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRaca.setAdapter(adapterRaca);

            }

        } else if (id ==R.id.spRaca) {

        } else if (id ==R.id.spFaixaEtaria) {

        } else if (id ==R.id.spSexo) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}