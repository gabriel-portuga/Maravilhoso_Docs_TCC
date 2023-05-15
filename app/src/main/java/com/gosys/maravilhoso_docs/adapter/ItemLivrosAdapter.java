package com.gosys.maravilhoso_docs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gosys.maravilhoso_docs.activity.Detalhes;
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

        holder.author.setText(itemLivros.getAuthor());
        holder.title.setText(itemLivros.getTitle());
        holder.year.setText(itemLivros.getYear());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detalhes.class);
                intent.putExtra("title", itemLivros.getTitle());
                intent.putExtra("author", itemLivros.getAuthor());
                intent.putExtra("description", itemLivros.getDescription());

                context.startActivity(intent);
            }
        });
        // Fim teste do click curto

    }

    @Override
    public int getItemCount(){
        return livrosArrayList.size();
    }

    public static class ItemLivrosViewHolder extends RecyclerView.ViewHolder{
        public TextView author, id, title, year;

        public ItemLivrosViewHolder(View itemView){
            super(itemView);

            author = itemView.findViewById(R.id.textAutorRV);
            title = itemView.findViewById(R.id.textTituloRV);
            year = itemView.findViewById(R.id.textAnoRV);

        }
    }

}

