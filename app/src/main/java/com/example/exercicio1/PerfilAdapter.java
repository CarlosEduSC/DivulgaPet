package com.example.exercicio1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PerfilAdapter extends BaseAdapter {
    private final Context context;
    private List<Pet> pets;
    private Button interessados;
    private Button editarPet;
    private String userId;
    private String petId;
    private Pet p = new Pet("","","","","","", "");


    public PerfilAdapter(Context context, List<Pet> pets, String userId) {
        this.context = context;
        this.pets = pets;
        this.userId = userId;
    }
    @Override
    public int getCount() {
        return pets.size();
    }

    @Override
    public Object getItem(int i) {
        return pets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_perfil, viewGroup, false);

        ImageView img = (ImageView) v.findViewById(R.id.imgPet);
        TextView nome = (TextView) v.findViewById(R.id.txtNomePet);
        TextView tipo = (TextView) v.findViewById(R.id.txtTipo);
        TextView raca = (TextView) v.findViewById(R.id.txtRaca);
        TextView faixaEtaria = (TextView) v.findViewById(R.id.txtFaixaEtaria);
        TextView sexo = (TextView) v.findViewById(R.id.txtSexo);
        interessados = (Button) v.findViewById(R.id.btInteressados);
        editarPet = (Button) v.findViewById(R.id.btEditarPet);

        Pet currentPet = pets.get(i);

        nome.setText(currentPet.getNome());
        tipo.setText(currentPet.getTipo());
        raca.setText(currentPet.getRaca());
        faixaEtaria.setText(currentPet.getFaixaEtaria());
        sexo.setText(currentPet.getSexo());
        userId = currentPet.getIdUsuario();

        byte[] decodedBytes = Base64.decode(currentPet.getFoto(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        img.setImageBitmap(bitmap);

        interessados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListaInteressadosActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("petId", currentPet.getId());
                context.startActivity(intent);
            }
        });

        editarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditarPetActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("petId", currentPet.getId());
                context.startActivity(intent);
            }
        });

        return v;
    }
}
