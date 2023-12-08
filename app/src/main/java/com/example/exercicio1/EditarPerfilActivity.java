package com.example.exercicio1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class EditarPerfilActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private PopupMenu menu;
    private EditText edtNome;
    private EditText edtCPF;
    private TextView txtData;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtCEP;
    private EditText edtRua;
    private EditText edtNumero;
    private EditText edtBairro;
    private EditText edtCidade;
    private EditText edtComplemento;
    private EditText edtSenha;
    private EditText edtConfirmarSenha;
    private Button btAtualizar;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private String userId;
    private Usuario doador = new Usuario("","","","","");
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            userId = intent.getStringExtra("userId");
        }

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);
        edtNome = findViewById(R.id.edtNome);
        edtCPF = findViewById(R.id.edtCPF);
        txtData = findViewById(R.id.txtData);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);
        edtCEP = findViewById(R.id.edtCEP);
        edtRua = findViewById(R.id.edtRua);
        edtNumero = findViewById(R.id.edtNumero);
        edtBairro = findViewById(R.id.edtBairro);
        edtCidade = findViewById(R.id.edtCidade);
        edtComplemento = findViewById(R.id.edtComplemento);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha);
        btAtualizar = findViewById(R.id.btAtualizar);

        usuarioDAO.getUsuarioById(userId, EditarPerfilActivity.this, new UsuarioDAO.GetUsuarioCallback() {
            @Override
            public void onCallback(Usuario usuario) {
                doador = usuario;
                String numero = "" + doador.getNumero();
                edtNome.setText(doador.getNome());
                edtCPF.setText(doador.getCpf());
                txtData.setText(doador.getDataNascimento());
                edtTelefone.setText(doador.getTelefone());
                edtEmail.setText(doador.getEmail());
                edtCEP.setText(doador.getCep());
                edtRua.setText(doador.getRua());
                edtNumero.setText(numero);
                edtBairro.setText(doador.getBairro());
                edtCidade.setText(doador.getCidade());
                edtComplemento.setText(doador.getComplemento());
                edtSenha.setText(doador.getSenha());
                edtConfirmarSenha.setText(doador.getSenha());
            }
        });

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        txtData.setOnClickListener(this);
        btAtualizar.setOnClickListener(this);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_logado, menu.getMenu());

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        txtData.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            Intent intent = new Intent(EditarPerfilActivity.this, PerfilActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == txtData) {
            showDatePickerDialog();

        } else if (view == btAtualizar) {
            if (!edtSenha.getText().toString().equals("") && !edtNome.getText().toString().equals("") && !txtData.getText().toString().equals("dd/mm/aa") && !edtTelefone.getText().toString().equals("") && !edtEmail.getText().toString().equals("")) {
                if (edtSenha.getText().toString().equals(edtConfirmarSenha.getText().toString())) {
                    Usuario usuario = new Usuario(edtNome.getText().toString(), txtData.getText().toString(), edtTelefone.getText().toString(), edtEmail.getText().toString(), edtSenha.getText().toString());
                    usuario.setId(userId);
                    usuario.setCep(edtCEP.getText().toString());
                    usuario.setRua(edtRua.getText().toString());
                    usuario.setNumero(Integer.parseInt(edtNumero.getText().toString()));
                    usuario.setBairro(edtBairro.getText().toString());
                    usuario.setCidade(edtCidade.getText().toString());
                    usuario.setComplemento(edtComplemento.getText().toString());
                    usuarioDAO.getAllUsuarios(this, new UsuarioDAO.UsuariosCallback() {
                        @Override
                        public void onCallback(List<Usuario> usuarios) {
                            boolean cadastrado = false;
                            for (Usuario user : usuarios) {
                                if (user.getEmail().equals(usuario.getEmail()) && !user.getId().equals(usuario.getId())) {
                                    cadastrado = true;
                                    break;
                                }
                            }

                            if (cadastrado == false) {
                                usuarioDAO.editUsuario(usuario, EditarPerfilActivity.this);

                                Intent intent = new Intent(EditarPerfilActivity.this, PerfilActivity.class);

                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPerfilActivity.this);
                                builder.setTitle("ERRO");
                                builder.setMessage("Email já cadastrado!");
                                builder.setPositiveButton("OK", null);
                                builder.create().show();
                            }
                        }
                    });
                }  else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditarPerfilActivity.this);
                    builder.setTitle("ERRO");
                    builder.setMessage("As senhas não coincidem!");
                    builder.setPositiveButton("OK", null);
                    builder.create().show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPerfilActivity.this);
                builder.setTitle("ERRO");
                builder.setMessage("Um ou mais campos estão incorretos!");
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(EditarPerfilActivity.this, CadastroPetActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(EditarPerfilActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.desconectar) {
            Intent intent = new Intent(EditarPerfilActivity.this, LoginActivity.class);

            startActivity(intent);
        }
        return false;
    }
}
