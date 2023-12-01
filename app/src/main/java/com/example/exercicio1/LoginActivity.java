package com.example.exercicio1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private PopupMenu menu;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btEntrar;
    private Button btCadastrar;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private String userId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btEntrar = findViewById(R.id.btEntrar);
        btCadastrar = findViewById(R.id.btCadastrar);

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        btEntrar.setOnClickListener(this);
        btCadastrar.setOnClickListener(this);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_login, menu.getMenu());

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            onBackPressed();

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == btEntrar) {
            usuarioDAO.getAllUsuarios(this, new UsuarioDAO.UsuariosCallback() {
                @Override
                public void onCallback(List<Usuario> usuarios) {
                    if (usuarios != null) {
                        boolean usuarioEncontrado = false;

                        for (Usuario usuario : usuarios) {
                            if (usuario.getEmail().equals(edtEmail.getText().toString()) && usuario.getSenha().equals(edtSenha.getText().toString())) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userId", usuario.getId());

                                startActivity(intent);
                                usuarioEncontrado = true;
                                userId = usuario.getId();
                                break;
                            }
                        }

                        if (!usuarioEncontrado) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("ERRO");
                            builder.setMessage("Email ou senha inválidos!");
                            builder.setPositiveButton("OK", null);
                            builder.create().show();
                        }

                    } else {
                        Log.e("MainActivity", "Erro ao obter a lista de pets");
                        Toast.makeText(LoginActivity.this, "Erro ao obter a lista de usuarios", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (view == btCadastrar) {
            Intent intent = new Intent(LoginActivity.this, CadastroUsuraioActivity.class);

            startActivity(intent);

        }
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(LoginActivity.this, CadastroUsuraioActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        }
        return false;
    }
}
