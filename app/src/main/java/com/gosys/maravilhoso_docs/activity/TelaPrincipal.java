package com.gosys.maravilhoso_docs.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.gosys.maravilhoso_docs.R;

public class TelaPrincipal extends AppCompatActivity {
    private TextView textDeslogar;
    private TextView textSubTitle;
    private Button button_Livros, button_Artigos;
    // Instancia o banco de dadods
    FirebaseFirestore bd = FirebaseFirestore.getInstance();
    // Atributo para armazenar o Id do usuario e buscar o seu nome
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();
        // Para deslogar
        textDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        // Acessar o CRUD de Livros
        button_Livros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, Livros.class);
                startActivity(intent);
                finish();
            }
        });
        // Acessar o CRUD de Artigos
        button_Artigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, Artigos.class);
                startActivity(intent);
                finish();
            }
        });

    }
    // Se o usuário abrir o app e já tiver logado, iniciará o app direto da tela principal mostrando o seu nome
    @Override
    protected void onStart() {
        super.onStart();
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = bd.collection("Usuarios").document(usuarioId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                     if (documentSnapshot != null){
                         textSubTitle.setText("Bem-vinde " + documentSnapshot.getString("nome"));
                     }
            }
        });
    }
    // Metodo para iniciar todos os componentes da view
    private void IniciarComponentes() {

        textDeslogar = findViewById(R.id.textDeslogar);
        textSubTitle = findViewById(R.id.textSubTitle);
        button_Livros = findViewById(R.id.button_Livros);
        button_Artigos = findViewById(R.id.button_Cadastrar);

    }
}