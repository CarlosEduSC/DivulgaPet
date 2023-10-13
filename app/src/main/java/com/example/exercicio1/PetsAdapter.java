package com.example.exercicio1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        img.setImageResource(R.drawable.maltes);
        nome.setText(pets.get(i).getNome());
        tipo.setText(pets.get(i).getTipo());
        raca.setText(pets.get(i).getRaca());
        faixaEtaria.setText(pets.get(i).getFaixaEtaria());
        sexo.setText(pets.get(i).getSexo());

        return v;
    }
}
