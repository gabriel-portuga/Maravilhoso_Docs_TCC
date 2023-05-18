package com.gosys.maravilhoso_docs.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gosys.maravilhoso_docs.adapter.ItemArtigosAdapter;
import com.gosys.maravilhoso_docs.model.ItemArtigos;
import com.gosys.maravilhoso_docs.model.ItemLivros;
import com.gosys.maravilhoso_docs.adapter.ItemLivrosAdapter;
import com.gosys.maravilhoso_docs.R;

import java.util.ArrayList;
import java.util.Objects;

public class Livros_Artigos extends AppCompatActivity {
    private EditText edit_search;
    private Button button_cadastrar, button_buscar, button_voltar;
    private String search;
    private boolean firstTime, livro_artigo;

    ArrayList<ItemLivros> livrosArrayList;
    ArrayList<ItemArtigos> artigosArrayList;
    ItemLivrosAdapter itemLivrosAdapter;
    ItemArtigosAdapter itemArtigosAdapter;
    FirebaseFirestore bd;
    ProgressDialog progressDialog;
    RecyclerView  recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        firstTime = true;
        livro_artigo = getIntent().getExtras().getBoolean("livro_artigo");

        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando o banco de dados");
        progressDialog.show();

        bd = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recycle_viewLivros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        if(firstTime){EventChangeListener();}

        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = edit_search.getText().toString().trim();
                hideKeyboard();
                if (search.equals("")){
                    Toast.makeText(getApplicationContext(), "Preencha o campo buscar para localizar o documento!", Toast.LENGTH_SHORT).show();
                } else {
                    buscaLivros(search);
                }
            }
        });
        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Livros_Artigos.this, LivrosEdit.class);
                intent.putExtra("titleActivity", 1);
                if (livro_artigo){
                    intent.putExtra("livro_artigo", true);
                }else if (!livro_artigo){
                    intent.putExtra("livro_artigo", false);
                }
                startActivity(intent);
            }
        });
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Livros_Artigos.this, TelaPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        firstTime = false;
        EventChangeListener();
    }
    private void EventChangeListener() {
        if (livro_artigo){
            livrosArrayList = new ArrayList<ItemLivros>();
            itemLivrosAdapter = new ItemLivrosAdapter(Livros_Artigos.this, livrosArrayList);
            recyclerView.setAdapter(itemLivrosAdapter);
            bd.collection("Livros").orderBy("title", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            if (error != null){
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                Log.e("Erro no FireStore", error.getMessage());
                                return;
                            }
                            livrosArrayList.clear();
                            assert value != null;
                            for (DocumentChange dc : value.getDocumentChanges()){
                                // Aqui onde alimenta o recyclerview com os itens
                                if (dc.getType() == DocumentChange.Type.ADDED){
                                    livrosArrayList.add(dc.getDocument().toObject(ItemLivros.class));
                                }
                                itemLivrosAdapter.notifyDataSetChanged();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        }
                    });
        } else if (!livro_artigo){
            artigosArrayList = new ArrayList<ItemArtigos>();
            itemArtigosAdapter = new ItemArtigosAdapter(Livros_Artigos.this, artigosArrayList);
            recyclerView.setAdapter(itemArtigosAdapter);
            bd.collection("Artigos").orderBy("title", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            if (error != null){
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                Log.e("Erro no FireStore", error.getMessage());
                                return;
                            }
                            artigosArrayList.clear();
                            assert value != null;
                            for (DocumentChange dc : value.getDocumentChanges()){
                                // Aqui onde alimenta o recyclerview com os itens
                                if (dc.getType() == DocumentChange.Type.ADDED){
                                    artigosArrayList.add(dc.getDocument().toObject(ItemArtigos.class));
                                }
                                itemArtigosAdapter.notifyDataSetChanged();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        }
                    });
        }

    }

    private void IniciarComponentes(){
        edit_search = findViewById(R.id.edit_search);
        button_buscar = findViewById(R.id.button_buscar);
        button_cadastrar = findViewById(R.id.button_cadastrar);
        button_voltar = findViewById(R.id.button_voltar);
    }
    private void buscaLivros(String search){
        bd.collection("Livros").whereEqualTo("author", search).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                }    else {
                    Toast.makeText(getApplicationContext(), "Nenhum item encontrado com essa descrição!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void hideKeyboard(){
        InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(edit_search.getApplicationWindowToken(), 0);
    }
}