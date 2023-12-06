package com.example.exercicio1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InteressadosAdapter extends BaseAdapter {
    private final Context context;
    private List<Usuario> interessados;
    private String petId;

    public InteressadosAdapter(Context context, List<Usuario> interessados) {
        this.context = context;
        this.interessados = interessados;
    }
    @Override
    public int getCount() {
        return interessados.size();
    }

    @Override
    public Object getItem(int i) {
        return interessados.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_interessados, viewGroup, false);

        TextView nome = (TextView) v.findViewById(R.id.txtNomePet);
        TextView telefone = (TextView) v.findViewById(R.id.txtTelefone);
        TextView email = (TextView) v.findViewById(R.id.txtEmail);

        nome.setText(interessados.get(i).getNome());
        telefone.setText(interessados.get(i).getTelefone());
        email.setText(interessados.get(i).getEmail());

        return v;
    }
}
