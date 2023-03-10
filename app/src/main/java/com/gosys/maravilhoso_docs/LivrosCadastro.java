package com.gosys.maravilhoso_docs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class LivrosCadastro extends AppCompatActivity {

    private EditText edit_title;
    private EditText edit_link;
    private EditText edit_description;
    private Button button_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros_cadastro);

        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();

        button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edit_title.getText().toString().trim();
                String link = edit_link.getText().toString().trim().toLowerCase();
                String description = edit_description.getText().toString().trim();
                String search = edit_title.getText().toString().trim();

                if (title.isEmpty() || link.isEmpty() || description.isEmpty()){
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    CadastrarLivro(title, link, description, search);
                }
            }
        });

    }

    private void CadastrarLivro(String title, String link, String description, String search) {
        String id = UUID.randomUUID().toString();
        Context context = getApplicationContext();

        FirebaseFirestore db_livros = FirebaseFirestore.getInstance();

        Map<String, Object> livros = new HashMap<>();
        livros.put("id", id);
        livros.put("title", title);
        livros.put("link", link);
        livros.put("description", description);
        livros.put("search", search);

        DocumentReference documentReference = db_livros.collection("Livros").document(id);
        documentReference.set(livros).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");

                Toast toast = Toast.makeText(context, "Dados salvos com sucesso!", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(LivrosCadastro.this, Livros.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao salvar os dados" + e.toString());

                Toast toast = Toast.makeText(context, "Erro ao salvar no banco de dados!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void IniciarComponentes(){
        edit_title = findViewById(R.id.edit_title);
        edit_link = findViewById(R.id.edit_link);
        edit_description = findViewById(R.id.edit_description);
        button_salvar = findViewById(R.id.button_salvar);
    }
}