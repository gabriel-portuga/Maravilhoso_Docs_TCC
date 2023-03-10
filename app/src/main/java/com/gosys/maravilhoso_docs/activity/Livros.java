package com.gosys.maravilhoso_docs.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.gosys.maravilhoso_docs.ItemLivros;
import com.gosys.maravilhoso_docs.ItemLivrosAdapter;
import com.gosys.maravilhoso_docs.R;

import java.util.Objects;

public class Livros extends AppCompatActivity {
    private EditText edit_search;
    private Button button_cadastrar;
    private Button button_buscar;
    private Button button_voltar;
    private String search;


    private ItemLivrosAdapter adapter;

    private FirebaseFirestore bd;
    private CollectionReference livrosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();

        bd = FirebaseFirestore.getInstance();
        livrosRef = bd.collection("Livros");

        Query query = livrosRef.orderBy("title");

        FirestoreRecyclerOptions<ItemLivros> options = new FirestoreRecyclerOptions.Builder<ItemLivros>()
                .setQuery(query, ItemLivros.class)
                .build();

        adapter = new ItemLivrosAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycle_viewLivros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

// NÃO ESTÁ FUNCIONANDO AINDA... CONSTRUIR!
        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // teste NÃO ESTÁ FUNCIONANDO!!!!!!!!*****************************************
                search = edit_search.getText().toString();
                DocumentReference docRef = bd.collection("Livros").document(search);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
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
               // Fim teste ******************************************************************
            }
        });
// CONSTRUIR A TELA CADASTRO / DIRECIONAMENTO OK
        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Livros.this, LivrosCadastro.class);
                startActivity(intent);
            }
        });
// OK
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
    }

}