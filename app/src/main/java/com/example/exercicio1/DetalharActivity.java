package com.example.exercicio1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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
    private String petId;
    private String userId;

    private PetDAO petDAO;

    private Pet animal = new Pet("", "", "", "", "", "", "");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("petId")) {
            petId = intent.getStringExtra("petId");
        }

        if (intent != null && intent.hasExtra("userId")) {
            userId = intent.getStringExtra("userId");
        }

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
                    txtCastrado.setText(animal.getCatracao());

                    byte[] decodedBytes = Base64.decode(animal.getFoto(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                    imgPet.setImageBitmap(bitmap);

                    if (animal.getVacinas().size() == 1) {
                        txtVacinas.setText(animal.getVacinas().get(0));

                    } else {
                        String vacinas = "";
                        for (int i=0; i <= animal.getVacinas().size(); i++) {
                            if (i == 0) {
                                vacinas += animal.getVacinas().get(i);

                            } else {
                                vacinas += ", " + animal.getVacinas().get(i);
                            }
                        }
                    }
                } else {
                    Log.e("DetalharActivity", "Erro ao obter pet");
                    Toast.makeText(DetalharActivity.this, "Erro ao obter pet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        txtDescricao.setOnClickListener(this);
        btTenhoInteresse.setOnClickListener(this);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        if (userId != null) {
            inflater.inflate(R.menu.menu_logado, menu.getMenu());

        } else {
            inflater.inflate(R.menu.menu_detalhar, menu.getMenu());
        }

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            Intent intent = new Intent(DetalharActivity.this, MainActivity.class);
            if (userId != null) {
                intent.putExtra("userId", userId);
            }

            startActivity(intent);

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == txtDescricao) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetalharActivity.this);
            builder.setMessage(animal.getDescricao()).setCancelable(true);

            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.filtro_background);
            alertDialog.show();

            TextView alertTextView = alertDialog.findViewById(android.R.id.message);
            alertTextView.setMovementMethod(LinkMovementMethod.getInstance());

        } else if (view == btTenhoInteresse) {
            if (userId != null) {
                petDAO.addInteressadoById(petId, userId, this);

                Intent intent = new Intent(DetalharActivity.this, PerfilDoadorActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("petId", petId);

                startActivity(intent);

            } else {
                Intent intent = new Intent(DetalharActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        }
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.login) {
            Intent intent = new Intent(DetalharActivity.this, LoginActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.cadastro) {
            Intent intent = new Intent(DetalharActivity.this, CadastroUsuraioActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(DetalharActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(DetalharActivity.this, PerfilActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(DetalharActivity.this, CadastroPetActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.desconectar) {
            Intent intent = new Intent(DetalharActivity.this, LoginActivity.class);

            startActivity(intent);
        }

        return false;
    }
}
