package com.example.exercicio1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PetsAdapter extends BaseAdapter {

    private final Context context;
    private List<Pet> pets;

    public PetsAdapter(Context context, List<Pet> pets) {
        this.context = context;
        this.pets = pets;
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
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_pets, viewGroup, false);

        ImageView img = (ImageView) v.findViewById(R.id.imgPet);
        TextView nome = (TextView) v.findViewById(R.id.txtNomePet);
        TextView tipo = (TextView) v.findViewById(R.id.txtTipo);
        TextView raca = (TextView) v.findViewById(R.id.txtRaca);
        TextView faixaEtaria = (TextView) v.findViewById(R.id.txtFaixaEtaria);
        TextView sexo = (TextView) v.findViewById(R.id.txtSexo);



        Pet currentPet = pets.get(i);
        nome.setText(currentPet.getNome());
        tipo.setText(currentPet.getTipo());
        raca.setText(currentPet.getRaca());
        faixaEtaria.setText(currentPet.getFaixaEtaria());
        sexo.setText(currentPet.getSexo());

        Glide.with(context).load(currentPet.getFoto()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(img);

        return v;

    }
}
