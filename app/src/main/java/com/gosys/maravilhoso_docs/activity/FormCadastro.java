package com.gosys.maravilhoso_docs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gosys.maravilhoso_docs.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FormCadastro extends AppCompatActivity {
    private EditText edit_nome, edit_email, edit_senha;
    private Button button_cadastrar, button_voltar;
    String[] mensagens_advertencia = {"Preencha todos os campos!", "Cadastro realizado com sucesso!", ""};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        Objects.requireNonNull(getSupportActionBar()).hide();
        IniciarComponentes();

        button_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                hideKeyboard();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, mensagens_advertencia[0], Toast.LENGTH_SHORT);
                    toast.show();
                } else{
                    CadastrarUsuario();
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

    private void CadastrarUsuario(){
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    SalvarDadosUsuario();

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, mensagens_advertencia[1], Toast.LENGTH_SHORT);
                    toast.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha com no mínimo 6 caracteres!";
                    }catch(FirebaseAuthUserCollisionException e){
                        erro = "Está conta já foi utilizada!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        erro = "Email inválido!";
                    }catch(Exception e){
                        erro = "Erro ao cadastrar usuario!";
                    }

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, erro, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void SalvarDadosUsuario(){
        String nome = edit_nome.getText().toString();

        FirebaseFirestore db_maravilhoso = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db_maravilhoso.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao salvar os dados" + e.toString());
            }
        });
    }
    private void IniciarComponentes(){
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        button_cadastrar = findViewById(R.id.button_Cadastrar);
        button_voltar = findViewById(R.id.button_voltarLogin);
    }
    public void hideKeyboard(){
        InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(edit_email.getApplicationWindowToken(), 0);
    }
}