package com.gosys.maravilhoso_docs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gosys.maravilhoso_docs.R;

import java.util.Objects;

public class Detalhes extends AppCompatActivity {

    private TextView textTtile, textAuthor, textDescription;
    private Button buttonAbrir, buttonEditar, buttonVoltar;
    private String title, author, description, id, link, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Objects.requireNonNull(getSupportActionBar()).hide();

        IniciarComponentes();
        CarregarInformacao();
        buttonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirUri();
            }
        });
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InjetarInformacao();
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
        author = getIntent().getExtras().getString("author");
        description = getIntent().getExtras().getString("description");
        id = getIntent().getExtras().getString("id");
        link = getIntent().getExtras().getString("link");
        title = getIntent().getExtras().getString("title");
        year = getIntent().getExtras().getString("year");

        textTtile.setText(title);
        textAuthor.setText("Escrito por: " + author);
        textDescription.setText(description);

    }
    private void InjetarInformacao(){
        Intent intent = new Intent(Detalhes.this, LivrosEdit.class);
        intent.putExtra("author", author);
        intent.putExtra("description", description);
        intent.putExtra("id", id);
        intent.putExtra("link", link);
        intent.putExtra("title", title);
        intent.putExtra("year", year);
        intent.putExtra("titleActivity", 2);
        startActivity(intent);
        finish();
    }
    private void AbrirUri(){
        Uri linkLivro = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, linkLivro);
        startActivity(intent);
    }


}