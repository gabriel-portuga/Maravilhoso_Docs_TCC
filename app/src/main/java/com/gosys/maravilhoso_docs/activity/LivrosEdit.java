package com.gosys.maravilhoso_docs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gosys.maravilhoso_docs.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class LivrosEdit extends AppCompatActivity {
    private TextView title_activity;
    private EditText edit_title, edit_link, edit_description, edit_author, edit_year;
    private Button button_salvar, button_voltar, button_excluir;
    private int titleActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros_edit);

        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();
        CarregarInformacao();

        button_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edit_title.getText().toString().trim();
                String author = edit_author.getText().toString().trim();
                String year = edit_year.getText().toString().trim();
                String link = edit_link.getText().toString().trim();
                String description = edit_description.getText().toString().trim();

                if (title.isEmpty() || link.isEmpty() || description.isEmpty() || author.isEmpty()
                        || year.isEmpty()){
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else if (titleActivity == 1){
                    CadastrarLivro(title, author, year, link, description);
                } else if (titleActivity == 2){
                    EditarLivro(title, author, year, link, description);
                }
            }
        });
        button_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleActivity == 2){
                    AlertDialog.Builder confirmaExclusao = new AlertDialog.Builder(LivrosEdit.this);
                    confirmaExclusao.setTitle(" Excluindo... ").setMessage(" Tem certeza que deseja excluir: " + getIntent().getExtras().getString("title") + " ? ")
                            .setCancelable(false).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExcluirLivro();
                                    Toast.makeText(LivrosEdit.this, "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    confirmaExclusao.create().show();
                }
            }
        });
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void CadastrarLivro(String title, String author, String year, String link, String description) {
        String id = UUID.randomUUID().toString();
        Context context = getApplicationContext();

        FirebaseFirestore db_livros = FirebaseFirestore.getInstance();

        Map<String, Object> livros = new HashMap<>();
        livros.put("id", id);
        livros.put("title", title);
        livros.put("author", author);
        livros.put("year", year);
        livros.put("link", link);
        livros.put("description", description);

        DocumentReference documentReference = db_livros.collection("Livros").document(id);
        documentReference.set(livros).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");

                Toast.makeText(context, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao salvar os dados" + e);
                Toast.makeText(context, "Erro ao salvar no banco de dados!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void EditarLivro(String title, String author, String year, String link, String description) {
        String id = getIntent().getExtras().getString("id");
        Context context = getApplicationContext();
        FirebaseFirestore db_livros = FirebaseFirestore.getInstance();

        Map<String, Object> livros = new HashMap<>();
        livros.put("id", id);
        livros.put("title", title);
        livros.put("author", author);
        livros.put("year", year);
        livros.put("link", link);
        livros.put("description", description);

        db_livros.collection("Livros").document(id).set(livros).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao editar os dados");

                Toast.makeText(context, "Dados editados com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao editar os dados" + e);
                Toast.makeText(context, "Erro ao editar no banco de dados!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ExcluirLivro(){
        String id = getIntent().getExtras().getString("id");
        FirebaseFirestore db_livros = FirebaseFirestore.getInstance();

        db_livros.collection("Livros").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Arquivo excluido do banco de dados!");
                Toast.makeText(LivrosEdit.this, "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao excluir do banco de dados!");
                Toast.makeText(LivrosEdit.this, "Erro ao excluir o arquivo!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void IniciarComponentes(){
        title_activity = findViewById(R.id.title_activity);
        edit_title = findViewById(R.id.edit_title);
        edit_link = findViewById(R.id.edit_link);
        edit_description = findViewById(R.id.edit_description);
        edit_author = findViewById(R.id.edit_author);
        edit_year = findViewById(R.id.edit_year);
        button_salvar = findViewById(R.id.button_salvar);
        button_voltar = findViewById(R.id.button_voltar);
        button_excluir = findViewById(R.id.button_excluir);
    }
    private void CarregarInformacao(){
        titleActivity = getIntent().getExtras().getInt("titleActivity");
        if (titleActivity == 1) {
            title_activity.setText("Cadastrar");
        } else if (titleActivity == 2) {
            title_activity.setText("Editar");
            button_excluir.setVisibility(View.VISIBLE);
            edit_author.setText(getIntent().getExtras().getString("author"));
            edit_description.setText(getIntent().getExtras().getString("description"));
            edit_link.setText(getIntent().getExtras().getString("link"));
            edit_title.setText(getIntent().getExtras().getString("title"));
            edit_year.setText(getIntent().getExtras().getString("year"));
        }
    }
}