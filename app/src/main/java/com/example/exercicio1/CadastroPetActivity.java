package com.example.exercicio1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CadastroPetActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;
    private EditText edtNome;
    private RadioGroup rgTipo;
    private RadioButton rbCachorro;
    private RadioButton rbGato;
    private Spinner spRaca;
    private RadioGroup rgSexo;
    private RadioButton rbMacho;
    private RadioButton rbFemea;
    private Spinner spFaixaEtaria;
    private Switch swCastrado;
    private Spinner spPorte;
    private CheckBox cbRaiva;
    private CheckBox cbLeish;
    private EditText edtDescricao;
    private Button btFoto;
    private ImageView imgPet;
    private Button btCadastrar;
    private PopupMenu menu;
    private String[] opcoes_raca_cachorro;
    private String[] opcoes_raca_gato;
    private String[] opcoes_faixa_etaria;
    private String[] opcoes_porte;

    Pet pet = new Pet("","","","","","", "");
    private String userId;
    private PetDAO petDAO = new PetDAO();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String caminhoFotoAtual;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            userId = intent.getStringExtra("userId");
        }

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);
        edtNome = findViewById(R.id.edtNome);
        rgTipo = findViewById(R.id.rgTipo);
        rbCachorro = findViewById(R.id.rbCachorro);
        rbGato = findViewById(R.id.rbGato);
        spRaca = findViewById(R.id.spRaca);
        rgSexo = findViewById(R.id.rgSexo);
        rbMacho = findViewById(R.id.rbMacho);
        rbFemea = findViewById(R.id.rbFemea);
        spFaixaEtaria = findViewById(R.id.spFaixaEtaria);
        swCastrado = findViewById(R.id.swCastrado);
        spPorte = findViewById(R.id.spPorte);
        cbRaiva = findViewById(R.id.cbRaiva);
        cbLeish = findViewById(R.id.cbLeish);
        edtDescricao = findViewById(R.id.edtDescricao);
        btFoto = findViewById(R.id.btFoto);
        imgPet = findViewById(R.id.imgPet);
        btCadastrar = findViewById(R.id.btCadastrar);

        opcoes_raca_cachorro = getResources().getStringArray(R.array.raca_cachorro);
        opcoes_raca_gato = getResources().getStringArray(R.array.raca_gato);
        opcoes_faixa_etaria = getResources().getStringArray(R.array.faixa_etaria);
        opcoes_porte = getResources().getStringArray(R.array.porte);

        Spinners();

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        rgTipo.setOnCheckedChangeListener(this);
        spRaca.setOnItemSelectedListener(this);
        btFoto.setOnClickListener(this);
        btCadastrar.setOnClickListener(this);
    }

    private void Spinners() {
        ArrayList<String> raca = new ArrayList<String>();
        raca.add("Raça");
        raca.add("Selecione o tipo do animal primeiro!");

        ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, raca);
        adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRaca.setAdapter(adapterRaca);

        ArrayAdapter<String> adapterFaixaEtaria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_faixa_etaria);
        adapterFaixaEtaria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFaixaEtaria.setAdapter(adapterFaixaEtaria);

        ArrayAdapter<String> adapterPorte = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes_porte);
        adapterPorte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPorte.setAdapter(adapterPorte);
    }

    private void OpenMenu(View view) {
        menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_cadastro_pet, menu.getMenu());

        menu.setOnMenuItemClickListener(this);

        menu.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            onBackPressed();

        } else if (view == imgMenu) {
            OpenMenu(view);

        } else if (view == btCadastrar) {
            if (!edtNome.getText().equals("") && rgTipo.getCheckedRadioButtonId() != -1 && !spRaca.getSelectedItem().toString().equals("Raça") && rgSexo.getCheckedRadioButtonId() != -1 && !spFaixaEtaria.getSelectedItem().toString().equals("Faixa Etária") && imgPet.getDrawable() != null && userId != null) {
                int idtipo = rgTipo.getCheckedRadioButtonId();
                int idsexo = rgSexo.getCheckedRadioButtonId();
                pet.setIdUsuario(userId);

                pet.setNome(edtNome.getText().toString());

                if (idtipo == rbGato.getId()) {
                    pet.setTipo(Pet.Tipo.Gato.toString());
                } else if (idtipo == rbCachorro.getId()) {
                    pet.setTipo(Pet.Tipo.Cachorro.toString());
                }

                pet.setFaixaEtaria(spFaixaEtaria.getSelectedItem().toString());

                if (idsexo == rbMacho.getId()) {
                    pet.setSexo(Pet.Sexo.Macho.toString());
                } else if (idsexo == rbFemea.getId()) {
                    pet.setSexo(Pet.Sexo.Fêmea.toString());
                }

                if (swCastrado.isChecked()) {
                    pet.setCatracao("Sim");
                } else {
                    pet.setCatracao("Não");
                }

                ArrayList<String> vacinas = new ArrayList<String>();

                if (cbRaiva.isChecked()) {
                    vacinas.add("Raiva Canina");
                    if (cbLeish.isChecked()) {
                        vacinas.add(", Leishmaniose");
                    }
                } else if (cbLeish.isChecked()) {
                    vacinas.add("Leishmaniose");
                }
                if (!edtDescricao.getText().toString().equals("")) {
                    pet.setDescricao(edtDescricao.getText().toString());

                } else {
                    pet.setDescricao("Não informada");
                }

                petDAO.addPet(pet,this);

                Intent intent = new Intent(CadastroPetActivity.this, PerfilActivity.class);
                intent.putExtra("userId", userId);

                startActivity(intent);

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPetActivity.this);
                builder.setTitle("ERRO");
                builder.setMessage("Os campos nome, tipo, raça, sexo e faixa etária precisam estar preenchidos!");
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }

        } else if (view == btFoto) {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            showSaveOrDeleteDialog(imageBitmap);
        }
    }

    private void showSaveOrDeleteDialog(final Bitmap imageBitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salvar ou excluir?");
        builder.setMessage("Deseja salvar a foto ou excluí-la?");

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                savePhoto(imageBitmap);
            }
        });

        builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dispatchTakePictureIntent();
            }
        });

        builder.show();
    }

    private void savePhoto(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        pet.setFoto(Base64.encodeToString(imageData, Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        imgPet.setImageBitmap(bitmap);

        String imagePath = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                imageBitmap,
                "Foto_" + System.currentTimeMillis(),
                ""
        );

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.parse(imagePath);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(CadastroPetActivity.this, PerfilActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(CadastroPetActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.spRaca) {
            pet.setRaca(spRaca.getSelectedItem().toString());

        } else if (id == R.id.spFaixaEtaria) {
            pet.setFaixaEtaria(spFaixaEtaria.getSelectedItem().toString());

        } else if (id == R.id.spPorte) {
            if (!spPorte.getSelectedItem().toString().equals(opcoes_porte[0])) {
                pet.setPorte(spPorte.getSelectedItem().toString());

            } else {
                pet.setPorte("Não informado");
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup.getId() == rgTipo.getId()) {
            if (i == R.id.rbCachorro) {
                updateRacaSpinner(opcoes_raca_cachorro);
            } else if (i == R.id.rbGato) {
                updateRacaSpinner(opcoes_raca_gato);
            }
        }
    }

    private void updateRacaSpinner(String[] opcoesRaca) {
        ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoesRaca);
        adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRaca.setAdapter(adapterRaca);
    }
}
