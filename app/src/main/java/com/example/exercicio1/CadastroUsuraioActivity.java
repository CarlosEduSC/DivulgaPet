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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastroUsuraioActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private PopupMenu menu;
    private EditText edtNome;
    private TextView txtData;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtConfirmarSenha;
    private Button btCadastro;
    private  UsuarioDAO usuarioDAO = new UsuarioDAO();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);
        edtNome  =findViewById(R.id.edtNome);
        txtData = findViewById(R.id.txtData);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha);
        btCadastro = findViewById(R.id.btCadastro);

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        txtData.setOnClickListener(this);
        btCadastro.setOnClickListener(this);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_cadastrar, menu.getMenu());

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
            onBackPressed();

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == txtData) {
            showDatePickerDialog();

        } else if (view == btCadastro) {
            if (!edtSenha.getText().toString().equals("") && !edtConfirmarSenha.getText().toString().equals("") && !edtNome.getText().toString().equals("") && !txtData.getText().toString().equals("dd/mm/aa") && !edtTelefone.getText().toString().equals("") && !edtEmail.getText().toString().equals("")) {
                if (edtSenha.getText().toString().equals(edtConfirmarSenha.getText().toString())) {
                    Usuario usuario = new Usuario(edtNome.getText().toString(), txtData.getText().toString(), edtTelefone.getText().toString(), edtEmail.getText().toString(), edtSenha.getText().toString());

                    usuarioDAO.getAllUsuarios(this, new UsuarioDAO.UsuariosCallback() {
                        @Override
                        public void onCallback(List<Usuario> usuarios) {
                            for (Usuario user : usuarios) {
                                if (!user.getId().equals(usuario.getId())) {
                                    usuarioDAO.addUsuario(usuario, CadastroUsuraioActivity.this);

                                    Intent intent = new Intent(CadastroUsuraioActivity.this, LoginActivity.class);

                                    startActivity(intent);
                                }
                            }
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroUsuraioActivity.this);
                    builder.setTitle("ERRO");
                    builder.setMessage("As senhas não coincidem!");
                    builder.setPositiveButton("OK", null);
                    builder.create().show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroUsuraioActivity.this);
                builder.setTitle("ERRO");
                builder.setMessage("Um ou mais campos estão incorretos!");
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.login) {
            Intent intent = new Intent(CadastroUsuraioActivity.this, LoginActivity.class);

            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(CadastroUsuraioActivity.this, MainActivity.class);

            startActivity(intent);
        }
        return false;
    }
}
