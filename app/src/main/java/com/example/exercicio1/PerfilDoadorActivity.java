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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class PerfilDoadorActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private PopupMenu menu;
    private TextView txtDescricao;
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
    private PetDAO petDAO;
    private UsuarioDAO usuarioDAO;

    private Pet animal = new Pet("", "", "", "", "", "", "");
    private Usuario doador = new Usuario("","","","","");
    private String petId;
    private String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_doador);

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
        txtNomePet = findViewById(R.id.txtNomePet);
        txtTipo = findViewById(R.id.txtTipo);
        txtRaca = findViewById(R.id.txtRaca);
        txtFaixaEtaria = findViewById(R.id.txtFaixaEtaria);
        txtSexo = findViewById(R.id.txtSexo);
        txtNomeDoador = findViewById(R.id.txtNomeDoador);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);

        petDAO = new PetDAO();
        usuarioDAO = new UsuarioDAO();

        petDAO.getPetById(petId, this, new PetDAO.GetPetCallback() {
            @Override
            public void onCallback(Pet pet) {
                if (pet != null) {
                    animal = pet;
                    txtNomePet.setText(animal.getNome());
                    txtRaca.setText(animal.getRaca());
                    txtTipo.setText(animal.getTipo());
                    txtFaixaEtaria.setText(animal.getFaixaEtaria());
                    txtSexo.setText(animal.getSexo());

                    byte[] decodedBytes = Base64.decode(animal.getFoto(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    imgPet.setImageBitmap(bitmap);

                    usuarioDAO.getUsuarioById(animal.getIdUsuario().toString(), PerfilDoadorActivity.this, new UsuarioDAO.GetUsuarioCallback() {
                        @Override
                        public void onCallback(Usuario usuario) {
                            doador = usuario;
                            txtNomeDoador.setText(doador.getNome());
                            txtDataNascimento.setText(doador.getDataNascimento());
                            txtTelefone.setText(doador.getTelefone());
                            txtEmail.setText(doador.getEmail());
                        }
                    });
                } else {
                    Log.e("DetalharActivity", "Erro ao obter pet");
                    Toast.makeText(PerfilDoadorActivity.this, "Erro ao obter pet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        txtDescricao.setOnClickListener(this);
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
            Intent intent = new Intent(PerfilDoadorActivity.this, DetalharActivity.class);
            intent.putExtra("userId", userId);
            intent.putExtra("petId", petId);

            startActivity(intent);

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == txtDescricao) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PerfilDoadorActivity.this);
            builder.setMessage(animal.getDescricao()).setCancelable(true);

            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.filtro_background);
            alertDialog.show();

            TextView alertTextView = alertDialog.findViewById(android.R.id.message);
            alertTextView.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(PerfilDoadorActivity.this, PerfilActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(PerfilDoadorActivity.this, CadastroPetActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(PerfilDoadorActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.desconectar) {
            Intent intent = new Intent(PerfilDoadorActivity.this, LoginActivity.class);

            startActivity(intent);
        }
        return false;
    }
}
