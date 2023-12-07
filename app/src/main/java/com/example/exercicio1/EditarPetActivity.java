package com.example.exercicio1;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EditarPetActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
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
    private Button btEditar;
    private Button btExcluir;
    private PopupMenu menu;
    private String[] opcoes_raca_cachorro;
    private String[] opcoes_raca_gato;
    private String[] opcoes_faixa_etaria;
    private String[] opcoes_porte;

    private Pet animal = new Pet("","","","","","", "");

    private String userId;
    private String petId;
    private PetDAO petDAO = new PetDAO();
    private static final int REQUEST_IMAGE_PICK = 1;
    private String caminhoFotoAtual;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pet);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userId")) {
            userId = intent.getStringExtra("userId");
        }
        if (intent != null && intent.hasExtra("petId")) {
            petId = intent.getStringExtra("petId");
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
        imgPet = findViewById(R.id.imgPet);
        btFoto = findViewById(R.id.btFoto);
        btEditar = findViewById(R.id.btEditar);
        btExcluir = findViewById(R.id.btExcluir);

        opcoes_raca_cachorro = getResources().getStringArray(R.array.raca_cachorro);
        opcoes_raca_gato = getResources().getStringArray(R.array.raca_gato);
        opcoes_faixa_etaria = getResources().getStringArray(R.array.faixa_etaria);
        opcoes_porte = getResources().getStringArray(R.array.porte);

        Spinners();

        petDAO.getPetById(petId, this, new PetDAO.GetPetCallback() {
            @Override
            public void onCallback(Pet pet) {
                animal.setId(pet.getId());
                animal.setIdUsuario(pet.getIdUsuario());
                edtNome.setText(pet.getNome());
                if (pet.getTipo().equals("Cachorro")) {
                    rbCachorro.setChecked(true);

                } else if (pet.getTipo().equals("Gato")) {
                    rbGato.setChecked(true);
                }

                for (int i = 0; i < spRaca.getCount(); i++) {
                    if (spRaca.getItemAtPosition(i).toString().equals(pet.getRaca())) {
                        spRaca.setSelection(i);
                        break;
                    }
                }

                if (pet.getSexo().equals("Macho")) {
                    rbMacho.setChecked(true);

                } else if (pet.getSexo().equals("Fêmea")) {
                    rbFemea.setChecked(true);
                }

                for (int i = 0; i < spFaixaEtaria.getCount(); i++) {
                    if (spFaixaEtaria.getItemAtPosition(i).toString().equals(pet.getFaixaEtaria())) {
                        spFaixaEtaria.setSelection(i);
                        break;
                    }
                }

                if (pet.getCatracao().equals("Sim")) {
                    swCastrado.setChecked(true);

                }

                for (int i = 0; i < spPorte.getCount(); i++) {
                    if (spPorte.getItemAtPosition(i).toString().equals(pet.getPorte())) {
                        spPorte.setSelection(i);
                        break;
                    }
                }

                if (!pet.getVacinas().get(0).equals("Não informadas")) {
                    if (pet.getVacinas().get(0).equals("Raiva Canina")) {
                        cbRaiva.setChecked(true);
                    }

                    if (pet.getVacinas().get(0).equals("Leishmaniose") || pet.getVacinas().get(1).equals(", Leishmaniose")) {
                        cbLeish.setChecked(true);
                    }
                }

                edtDescricao.setText(pet.getDescricao());

                if (pet.getFoto() != null) {
                    Glide.with(EditarPetActivity.this).load(pet.getFoto()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(imgPet);
                }
            }
        });

        imgVoltar.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        rgTipo.setOnCheckedChangeListener(this);
        spRaca.setOnItemSelectedListener(this);
        btFoto.setOnClickListener(this);
        btEditar.setOnClickListener(this);
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

        } else if (view == btEditar) {
            if (!edtNome.getText().equals("") && rgTipo.getCheckedRadioButtonId() != -1 && !spRaca.getSelectedItem().toString().equals("Raça") && rgSexo.getCheckedRadioButtonId() != -1 && !spFaixaEtaria.getSelectedItem().toString().equals("Faixa Etária") && imgPet.getDrawable() != null && userId != null) {
                int idtipo = rgTipo.getCheckedRadioButtonId();
                int idsexo = rgSexo.getCheckedRadioButtonId();
                animal.setIdUsuario(userId);

                animal.setNome(edtNome.getText().toString());

                if (idtipo == rbGato.getId()) {
                    animal.setTipo(Pet.Tipo.Gato.toString());
                } else if (idtipo == rbCachorro.getId()) {
                    animal.setTipo(Pet.Tipo.Cachorro.toString());
                }

                animal.setFaixaEtaria(spFaixaEtaria.getSelectedItem().toString());

                if (idsexo == rbMacho.getId()) {
                    animal.setSexo(Pet.Sexo.Macho.toString());
                } else if (idsexo == rbFemea.getId()) {
                    animal.setSexo(Pet.Sexo.Fêmea.toString());
                }

                if (swCastrado.isChecked()) {
                    animal.setCatracao("Sim");
                } else {
                    animal.setCatracao("Não");
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
                    animal.setDescricao(edtDescricao.getText().toString());

                } else {
                    animal.setDescricao("Não informada");
                }

                petDAO.editePet(animal,this);

                Intent intent = new Intent(EditarPetActivity.this, PerfilActivity.class);
                intent.putExtra("userId", userId);

                startActivity(intent);

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPetActivity.this);
                builder.setTitle("ERRO");
                builder.setMessage("Os campos nome, tipo, raça, sexo e faixa etária precisam estar preenchidos!");
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        } else if (view == btFoto) {
            Intent escolherFotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            escolherFotoIntent.setType("image/*");

            Intent capturarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File fotoFile = null;
            try {
                fotoFile = criarArquivoImagem();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (fotoFile != null) {
                Uri fotoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", fotoFile);
                capturarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            }

            Intent chooserIntent = Intent.createChooser(escolherFotoIntent, "Escolha uma foto");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{capturarFotoIntent});

            startActivityForResult(chooserIntent, REQUEST_IMAGE_PICK);
        }
    }

    private File criarArquivoImagem() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String nomeArquivo = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File arquivoImagem = File.createTempFile(nomeArquivo, ".jpg", storageDir);
        caminhoFotoAtual = arquivoImagem.getAbsolutePath();
        return arquivoImagem;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri imagemSelecionadaUri = data.getData();
                animal.setFoto(imagemSelecionadaUri.toString());
            } else if (!TextUtils.isEmpty(caminhoFotoAtual)) {
                animal.setFoto(caminhoFotoAtual);
            }

            Glide.with(EditarPetActivity.this).load(animal.getFoto()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(imgPet);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.perfil) {
            Intent intent = new Intent(EditarPetActivity.this, PerfilActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.listaAnimais) {
            Intent intent = new Intent(EditarPetActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.cadastrarPet) {
            Intent intent = new Intent(EditarPetActivity.this, CadastroPetActivity.class);
            intent.putExtra("userId", userId);

            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.spRaca) {
            animal.setRaca(spRaca.getSelectedItem().toString());

        } else if (id == R.id.spFaixaEtaria) {
            animal.setFaixaEtaria(spFaixaEtaria.getSelectedItem().toString());

        } else if (id == R.id.spPorte) {
            if (!spPorte.getSelectedItem().toString().equals(opcoes_porte[0])) {
                animal.setPorte(spPorte.getSelectedItem().toString());

            } else {
                animal.setPorte("Não informado");
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
