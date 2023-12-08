package com.example.exercicio1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private PopupMenu menu;
    private TextView txtNome;
    private TextView txtCPF;
    private TextView txtDataNascimento;
    private TextView txtTelefone;
    private TextView txtEmail;
    private LinearLayout lsCEP;
    private TextView txtSenha;
    private Button btEditarPerfil;
    private ListView lsPets;
    private PerfilAdapter perfilAdapter;
    private List<Pet> petsLista = new ArrayList<Pet>();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private PetDAO petDAO = new PetDAO();
    private String userId;
    private Usuario doador = new Usuario("","","","","");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            userId = intent.getStringExtra("userId");
        }

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);
        txtNome = findViewById(R.id.txtNome);
        txtCPF = findViewById(R.id.txtCPF);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);
        lsCEP = findViewById(R.id.lsCEP);
        txtSenha = findViewById(R.id.txtSenha);
        btEditarPerfil = findViewById(R.id.btEditarPerfil);
        lsPets = findViewById(R.id.lsPets);

        perfilAdapter = new PerfilAdapter(this, petsLista, userId);
        lsPets.setAdapter(perfilAdapter);
        lsPets.setOnItemClickListener(this);

        petDAO.getAllPets(this, new PetDAO.PetCallback() {

            @Override
            public void onCallback(List<Pet> pets) {
                if (pets != null) {
                    petsLista.clear();
                    for (Pet pet : pets) {
                        if (pet.getIdUsuario().equals(userId.toString())) {
                            petsLista.add(pet);
                            perfilAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Log.e("PerfilActivity", "Erro ao obter a lista de pets");
                    Toast.makeText(PerfilActivity.this, "Erro ao obter a lista de pets", Toast.LENGTH_SHORT).show();
                }
            }
        });

        usuarioDAO.getUsuarioById(userId, PerfilActivity.this, new UsuarioDAO.GetUsuarioCallback() {
            @Override
            public void onCallback(Usuario usuario) {
                doador = usuario;
                txtNome.setText(doador.getNome());
                txtCPF.setText(doador.getCpf());
                txtDataNascimento.setText(doador.getDataNascimento());
                txtTelefone.setText(doador.getTelefone());
                txtEmail.setText(doador.getEmail());

                String senha = "";

                for (int i=1; i <= doador.getSenha().length(); i++) {
                    senha += "*";
                }

                txtSenha.setText(senha);
            }
        });

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        lsCEP.setOnClickListener(this);
        btEditarPerfil.setOnClickListener(this);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_perfil, menu.getMenu());

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    public void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_endereco, null);

        TextView txtCepNumero = view.findViewById(R.id.txtCepNumero);
        TextView txtRua = view.findViewById(R.id.txtRua);
        TextView txtNumero = view.findViewById(R.id.txtNumero);
        TextView txtBairro = view.findViewById(R.id.txtBairro);
        TextView txtCidade = view.findViewById(R.id.txtCidade);
        TextView txtComplemento = view.findViewById(R.id.txtComplemento);

        txtCepNumero.setText(doador.getCep());
        txtRua.setText(doador.getRua());
        String numero = "" + doador.getNumero();
        txtNumero.setText(numero);
        txtBairro.setText(doador.getBairro());
        txtCidade.setText(doador.getCidade());
        txtComplemento.setText(doador.getComplemento());

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            onBackPressed();

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == lsCEP) {
            showCustomDialog();

        } else if (view == btEditarPerfil) {
            Intent intent = new Intent(PerfilActivity.this, EditarPerfilActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(PerfilActivity.this, CadastroPetActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
