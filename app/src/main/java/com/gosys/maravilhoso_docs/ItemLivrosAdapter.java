package com.gosys.maravilhoso_docs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ItemLivrosAdapter extends FirestoreRecyclerAdapter<ItemLivros, ItemLivrosAdapter.ItemLivrosViewHolder>{

    public ItemLivrosAdapter(@NonNull FirestoreRecyclerOptions<ItemLivros> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemLivrosViewHolder holder, int position, @NonNull ItemLivros model){
        holder.textTituloRV.setText(model.getTitle());
        holder.textAutorRV.setText(model.getAuthor());
        holder.textAnoRV.setText(model.getYear());
        holder.textResumoRV.setText(model.getDescription());
    }

    @NonNull
    @Override
    public ItemLivrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livros, parent, false);
        return new ItemLivrosViewHolder(v);
    }

    public static class ItemLivrosViewHolder extends RecyclerView.ViewHolder{
        public TextView textTituloRV;
        public TextView textAutorRV;
        public TextView textAnoRV;
        public TextView textResumoRV;

        public ItemLivrosViewHolder(View itemView){
            super(itemView);
            textTituloRV = itemView.findViewById(R.id.textTituloRV);
            textAutorRV = itemView.findViewById(R.id.textAutorRV);
            textAnoRV = itemView.findViewById(R.id.textAnoRV);
            textResumoRV = itemView.findViewById(R.id.textResumoRV);
        }
    }

}

