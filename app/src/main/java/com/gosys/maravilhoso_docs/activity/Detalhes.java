package com.gosys.maravilhoso_docs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gosys.maravilhoso_docs.R;

public class Detalhes extends AppCompatActivity {

    private TextView textTtile, textAuthor, textDescription;
    private Button buttonAbrir, buttonEditar, buttonVoltar;
    private String title, author, description, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        IniciarComponentes();
        CarregarInformacao();

        buttonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void IniciarComponentes() {
        textTtile = findViewById(R.id.textTitle);
        textAuthor = findViewById(R.id.textAuthor);
        textDescription = findViewById(R.id.textDescription);

        buttonAbrir = findViewById(R.id.buttonAbrir);
        buttonEditar = findViewById(R.id.buttonEditar);
        buttonVoltar = findViewById(R.id.buttonVoltar);

    }
    private void CarregarInformacao() {
        title = getIntent().getExtras().getString("title");
        author = getIntent().getExtras().getString("author");
        description = getIntent().getExtras().getString("description");
        id = getIntent().getExtras().getString("id");

        textTtile.setText(title);
        textAuthor.setText("Escrito por: " + author);
        textDescription.setText(description);

    }

}