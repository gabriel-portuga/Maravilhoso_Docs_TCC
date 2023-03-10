package com.gosys.maravilhoso_docs;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Livros extends AppCompatActivity {
    private EditText edit_search;
    private Button button_cadastrar;
    private Button button_buscar;
    private Button button_voltar;
    private String search; // teste*
    private TextView textTeste;

    FirebaseFirestore bd = FirebaseFirestore.getInstance(); // teste*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();

        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // teste
                search = edit_search.getText().toString();
                DocumentReference docRef = bd.collection("Livros").document(search);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            textTeste.setText("funcionou");
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
               // Fim teste
            }
        });

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Livros.this, LivrosCadastro.class);
                startActivity(intent);
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Livros.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void IniciarComponentes(){
        edit_search = findViewById(R.id.edit_search);
        button_buscar = findViewById(R.id.button_buscar);
        button_cadastrar = findViewById(R.id.button_cadastrar);
        button_voltar = findViewById(R.id.button_voltar);
        textTeste = findViewById(R.id.textTeste);
    }

}