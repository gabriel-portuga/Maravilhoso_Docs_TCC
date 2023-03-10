package com.gosys.maravilhoso_docs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gosys.maravilhoso_docs.R;

public class Artigos extends AppCompatActivity {
    private Button button_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artigos);

        IniciarComponentes();

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Artigos.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void IniciarComponentes(){
        button_voltar = findViewById(R.id.button_voltar);
    }

}