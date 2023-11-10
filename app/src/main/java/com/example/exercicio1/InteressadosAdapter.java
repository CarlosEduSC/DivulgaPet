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
    private List<Usuario> usuarios;

    public InteressadosAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_interessados, viewGroup, false);

        TextView nome = (TextView) v.findViewById(R.id.txtNomePet);
        TextView telefone = (TextView) v.findViewById(R.id.txtTelefone);
        TextView email = (TextView) v.findViewById(R.id.txtEmail);

        nome.setText(usuarios.get(i).getNome());
        telefone.setText(usuarios.get(i).getTelefone());
        email.setText(usuarios.get(i).getEmail());

        return v;
    }
}
