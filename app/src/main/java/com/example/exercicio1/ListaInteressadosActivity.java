package com.example.exercicio1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ListaInteressadosActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private PopupMenu menu;
    private ImageView imgPet;
    private TextView txtNome;
    private TextView txtRaca;
    private TextView txtTipo;
    private TextView txtFaixaEtaria;
    private TextView txtSexo;
    private ListView lsInteressados;
    private String petId;
    private String userId;

    private PetDAO petDAO;

    private Pet animal = new Pet("", "", "", "", "", "", "");
    private ArrayList<Usuario> usuariosInteressados = new ArrayList<Usuario>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_interessados);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("petId")) {
            petId = intent.getStringExtra("petId");
        }

        if (intent != null && intent.hasExtra("userId")) {
            userId = intent.getStringExtra("userId");
        }

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);
        imgPet = findViewById(R.id.imgPet);
        txtNome = findViewById(R.id.txtNomePet);
        txtRaca = findViewById(R.id.txtRaca);
        txtTipo = findViewById(R.id.txtTipo);
        txtFaixaEtaria = findViewById(R.id.txtFaixaEtaria);
        txtSexo = findViewById(R.id.txtSexo);
        lsInteressados = findViewById(R.id.lsInteressados);

        petDAO = new PetDAO();

        petDAO.getPetById(petId, this, new PetDAO.GetPetCallback() {
            @Override
            public void onCallback(Pet pet) {
                if (pet != null) {
                    animal = pet;
                    txtNome.setText(animal.getNome());
                    txtRaca.setText(animal.getRaca());
                    txtTipo.setText(animal.getTipo());
                    txtFaixaEtaria.setText(animal.getFaixaEtaria());
                    txtSexo.setText(animal.getSexo());
                    Glide.with(ListaInteressadosActivity.this).load(pet.getFoto()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(imgPet);

                } else {
                    Log.e("DetalharActivity", "Erro ao obter pet");
                    Toast.makeText(ListaInteressadosActivity.this, "Erro ao obter pet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        InteressadosAdapter interessadosAdapter = new InteressadosAdapter(this, usuariosInteressados);
        lsInteressados.setAdapter(interessadosAdapter);

        petDAO.getPetInteressados(petId, this, new PetDAO.GetInteressadosCallback() {
            @Override
            public void onCallback(List<Usuario> interessados) {
                if (interessados != null) {
                    usuariosInteressados.clear();
                    usuariosInteressados.addAll(interessados);
                    interessadosAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(ListaInteressadosActivity.this, "Erro os usuarios interessados!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_logado, menu.getMenu());

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            onBackPressed();

        } else if (view == imgMenu) {
            OpenMenu(view);

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(ListaInteressadosActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(ListaInteressadosActivity.this, PerfilActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(ListaInteressadosActivity.this, CadastroPetActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        }
        return false;
    }
}
