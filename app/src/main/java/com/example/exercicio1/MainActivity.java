package com.example.exercicio1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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
    private String tipoSelecionado;
    private String racaSelecionado;
    private String faixaEtariaSelecionado;
    private String sexoSelecionado;
    private PetDAO petDAO;
    private PetsAdapter petsAdapter;
    private List<Pet> petsLista = new ArrayList<Pet>();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            userId = intent.getStringExtra("userId");
        }

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

        imgMenu.setOnClickListener(this);

        petsAdapter = new PetsAdapter(this, petsLista);
        listaPets.setAdapter(petsAdapter);
        listaPets.setOnItemClickListener(this);
        petDAO.getAllPets(this, new PetDAO.PetCallback() {
            @Override
            public void onCallback(List<Pet> pets) {
                if (pets != null) {
                    petsLista.clear();

                    for (Pet pet : pets) {
                        if (userId != null && !pet.getIdUsuario().equals(userId)) {
                            petsLista.add(pet);
                        }
                    }

                    petsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao obter a lista de pets", Toast.LENGTH_SHORT).show();
                }
            }
        });


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

        if (userId != null) {
            inflater.inflate(R.menu.menu_main_logado, menu.getMenu());

        } else {
            inflater.inflate(R.menu.menu_main, menu.getMenu());
        }

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == listaPets) {
            Pet petSelecionado = (Pet) petsAdapter.getItem(i);

            String petId = petSelecionado.getId();

            Intent intent = new Intent(MainActivity.this, DetalharActivity.class);
            intent.putExtra("petId", petId);
            intent.putExtra("userId", userId);

            startActivity(intent);

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
        if (menuItem.getItemId() == R.id.login) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.cadastro) {
            Intent intent = new Intent(MainActivity.this, CadastroUsuraioActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(MainActivity.this, CadastroPetActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.desconectar) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            startActivity(intent);
        }

        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.spTipo) {
            if (spTipo.getSelectedItem() != opcoes_tipo[0]) {
                if (spTipo.getSelectedItem() == opcoes_tipo[1]) {
                    ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_raca_cachorro);
                    adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spRaca.setAdapter(adapterRaca);

                } else if (spTipo.getSelectedItem() == opcoes_tipo[2]) {
                    ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_raca_gato);
                    adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spRaca.setAdapter(adapterRaca);
                }
                tipoSelecionado = spTipo.getSelectedItem().toString();
                atualizarLista();

            } else {
                ArrayList<String> raca0 = new ArrayList<String>();
                raca0.add("Raça");
                raca0.add("Selecione o tipo do animal primeiro!");

                ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, raca0);
                adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spRaca.setAdapter(adapterRaca);
                tipoSelecionado = "";
                atualizarLista();
            }

        } else if (id == R.id.spRaca) {
            if (spTipo.getSelectedItem() == opcoes_tipo[1]) {
                if (spRaca.getSelectedItem() != opcoes_raca_cachorro[0]) {
                    racaSelecionado = spRaca.getSelectedItem().toString();
                    atualizarLista();
                } else {
                    racaSelecionado = "";
                    atualizarLista();
                }

            } else if (spTipo.getSelectedItem() == opcoes_tipo[2]) {
                if (spRaca.getSelectedItem() != opcoes_raca_gato[0]) {
                    racaSelecionado = spRaca.getSelectedItem().toString();
                    atualizarLista();
                }  else {
                    racaSelecionado = "";
                    atualizarLista();
                }

            }

        } else if (id == R.id.spFaixaEtaria) {
            if (spFaixaEtaria.getSelectedItem() != opcoes_faixa_etaria[0]) {
                faixaEtariaSelecionado = spFaixaEtaria.getSelectedItem().toString();
                atualizarLista();
            } else {
                faixaEtariaSelecionado = "";
                atualizarLista();
            }

        } else if (id == R.id.spSexo) {
            if (spSexo.getSelectedItem() != opcoes_sexo[0]) {
                sexoSelecionado = spSexo.getSelectedItem().toString();
                atualizarLista();
            } else {
                sexoSelecionado = "";
                atualizarLista();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void atualizarLista() {
        petDAO.getAllPets(this, new PetDAO.PetCallback() {
            @Override
            public void onCallback(List<Pet> pets) {
                if (pets != null) {
                    List<Pet> novaLista = new ArrayList<>();

                    for (Pet pet : pets) {
                        boolean tipoCorresponde = tipoSelecionado == null || tipoSelecionado.isEmpty() || tipoSelecionado.equals(pet.getTipo());
                        boolean racaCorresponde = racaSelecionado == null || racaSelecionado.isEmpty() || racaSelecionado.equals(pet.getRaca());
                        boolean faixaEtariaCorresponde = faixaEtariaSelecionado == null || faixaEtariaSelecionado.isEmpty() || faixaEtariaSelecionado.equals(pet.getFaixaEtaria());
                        boolean sexoCorresponde = sexoSelecionado == null || sexoSelecionado.isEmpty() || sexoSelecionado.equals(pet.getSexo());

                        if (tipoCorresponde && racaCorresponde && faixaEtariaCorresponde && sexoCorresponde && !pet.getIdUsuario().equals(userId)) {
                            novaLista.add(pet);
                        }
                    }

                    petsLista.clear();
                    petsLista.addAll(novaLista);
                    petsAdapter.notifyDataSetChanged();
                } else {
                    Log.e("MainActivity", "Erro ao obter a lista de pets");
                    Toast.makeText(MainActivity.this, "Erro ao obter a lista de pets", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}