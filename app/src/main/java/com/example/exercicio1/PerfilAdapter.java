package com.example.exercicio1;

import android.content.Context;
import android.content.Intent;
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

public class PerfilAdapter extends BaseAdapter implements View.OnClickListener {
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
        p = currentPet;
        nome.setText(currentPet.getNome());
        tipo.setText(currentPet.getTipo());
        raca.setText(currentPet.getRaca());
        faixaEtaria.setText(currentPet.getFaixaEtaria());
        sexo.setText(currentPet.getSexo());
        userId = p.getIdUsuario();

        Glide.with(context).load(p.getFoto()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(img);

        interessados.setOnClickListener(this);
        editarPet.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View view) {
            if (view == interessados) {
                Intent intent = new Intent(context, ListaInteressadosActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("petId", p.getId());


                context.startActivity(intent);

            } else if (view == editarPet) {
                Intent intent = new Intent(context, EditarPetActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("petId", p.getId());


                context.startActivity(intent);
            }
    }
}
