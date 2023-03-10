package com.gosys.maravilhoso_docs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {
    private TextView textDeslogar;
    private TextView textSubTitle;
    private Button button_Livros, button_Artigos, button_Imagens, button_Referencias;

    FirebaseFirestore bd = FirebaseFirestore.getInstance();
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();

        textDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });

        button_Livros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, Livros.class);
                startActivity(intent);
                finish();
            }
        });

        button_Artigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, Artigos.class);
                startActivity(intent);
                finish();
            }
        });
        // Botões em construção
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, " Em construção!! ", Toast.LENGTH_SHORT);

        button_Imagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
            }
        });
        button_Referencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.show();
            }
        });
        // Fim botões em construção
    }

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

    private void IniciarComponentes() {

        textDeslogar = findViewById(R.id.textDeslogar);

        textSubTitle = findViewById(R.id.textSubTitle);

        button_Livros = findViewById(R.id.button_Livros);
        button_Artigos = findViewById(R.id.button_Artigos);
        button_Imagens = findViewById(R.id.button_Imagens);
        button_Referencias = findViewById(R.id.button_Referencias);
    }
}