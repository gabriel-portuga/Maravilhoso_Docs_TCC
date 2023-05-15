package com.gosys.maravilhoso_docs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gosys.maravilhoso_docs.model.ItemLivros;
import com.gosys.maravilhoso_docs.R;

import java.util.ArrayList;

public class ItemLivrosAdapter extends RecyclerView.Adapter<ItemLivrosAdapter.ItemLivrosViewHolder> {

    public ItemLivrosAdapter(Context context, ArrayList<ItemLivros> livrosArrayList) {
        this.context = context;
        this.livrosArrayList = livrosArrayList;
    }

    Context context;
    ArrayList<ItemLivros> livrosArrayList;

    @NonNull
    @Override
    public ItemLivrosAdapter.ItemLivrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_livros, parent, false);
        return new ItemLivrosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemLivrosAdapter.ItemLivrosViewHolder holder, @SuppressLint("RecyclerView") int position){

        ItemLivros itemLivros = livrosArrayList.get(position);

        holder.title.setText(itemLivros.getTitle());
        holder.author.setText(itemLivros.getAuthor());
        holder.year.setText(itemLivros.getYear());
        //holder.description.setText(itemLivros.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clique", "clique curto no Autor: " + itemLivros.getAuthor());
            }
        });

    }

    @Override
    public int getItemCount(){
        return livrosArrayList.size();
    }

    public static class ItemLivrosViewHolder extends RecyclerView.ViewHolder{
        public TextView title, author, year, description;

        public ItemLivrosViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.textTituloRV);
            author = itemView.findViewById(R.id.textAutorRV);
            year = itemView.findViewById(R.id.textAnoRV);
            //description = itemView.findViewById(R.id.textResumoRV);
        }
    }

}

