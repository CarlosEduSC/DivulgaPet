package com.example.exercicio1;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaInteressadosActivity extends AppCompatActivity {

    private ListView lsInteressados;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_interessados);

        Usuario usuario = new Usuario("Carlos","02/07/2001","45999123456","email@gmail.com","1234");

        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.add(usuario);

        lsInteressados = findViewById(R.id.lsInteressados);

        InteressadosAdapter interessadosAdapter = new InteressadosAdapter(this, usuarios);
        lsInteressados.setAdapter(interessadosAdapter);
    }
}
