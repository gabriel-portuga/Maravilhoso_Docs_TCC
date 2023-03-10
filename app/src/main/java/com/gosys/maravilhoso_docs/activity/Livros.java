package com.gosys.maravilhoso_docs.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.gosys.maravilhoso_docs.model.ItemLivros;
import com.gosys.maravilhoso_docs.adapter.ItemLivrosAdapter;
import com.gosys.maravilhoso_docs.R;

import java.util.ArrayList;
import java.util.Objects;

public class Livros extends AppCompatActivity {
    private EditText edit_search;
    private Button button_cadastrar;
    private Button button_buscar;
    private Button button_voltar;
    private String search;

    ArrayList<ItemLivros> livrosArrayList;
    ItemLivrosAdapter itemLivrosAdapter;
    FirebaseFirestore bd;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando o banco de dados");
        progressDialog.show();

        bd = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.recycle_viewLivros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        livrosArrayList = new ArrayList<ItemLivros>();
        itemLivrosAdapter = new ItemLivrosAdapter(Livros.this, livrosArrayList);
        recyclerView.setAdapter(itemLivrosAdapter);

        EventChangeListener();


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
// OK
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

    private void EventChangeListener() {
        bd.collection("Livros").orderBy("title", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Erro no FireStore", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        livrosArrayList.add(dc.getDocument().toObject(ItemLivros.class));

                    }
                    itemLivrosAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
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