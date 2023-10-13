package com.example.exercicio1;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class ToolBar extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgVoltar;
    private ImageView imgMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_bar);

        imgVoltar = findViewById(R.id.imgVoltar);
        imgMenu = findViewById(R.id.imgMenu);

        imgVoltar.setOnClickListener(this);
    }

    private void OpenMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.menu_options, menu.getMenu());

        menu.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imgVoltar) {
            onBackPressed();

        } else if (view == imgMenu) {
            OpenMenu(view);
        }
    }
}
