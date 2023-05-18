package com.gosys.maravilhoso_docs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gosys.maravilhoso_docs.R;
import com.gosys.maravilhoso_docs.activity.Detalhes;
import com.gosys.maravilhoso_docs.model.ItemArtigos;

import java.util.ArrayList;

public class ItemArtigosAdapter extends RecyclerView.Adapter<ItemArtigosAdapter.ItemArtigosViewHolder> {

    public ItemArtigosAdapter(Context context, ArrayList<ItemArtigos> artigosArrayList) {
        this.context = context;
        this.artigosArrayList = artigosArrayList;
    }
    Context context;
    ArrayList<ItemArtigos> artigosArrayList;

    @NonNull
    @Override
    public ItemArtigosAdapter.ItemArtigosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item_livros_artigos, parent, false);
        return new ItemArtigosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemArtigosAdapter.ItemArtigosViewHolder holder, @SuppressLint("RecyclerView") int position){

        ItemArtigos itemArtigos = artigosArrayList.get(position);

        holder.author.setText(itemArtigos.getAuthor());
        holder.title.setText(itemArtigos.getTitle());
        holder.year.setText(itemArtigos.getYear());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirDetalhes(itemArtigos);
            }
        });
    }

    @Override
    public int getItemCount(){
        return artigosArrayList.size();
    }

    public static class ItemArtigosViewHolder extends RecyclerView.ViewHolder{
        public TextView author, id, title, year;

        public ItemArtigosViewHolder(View itemView){
            super(itemView);

            author = itemView.findViewById(R.id.textAutorRV);
            title = itemView.findViewById(R.id.textTituloRV);
            year = itemView.findViewById(R.id.textAnoRV);
        }
    }
    private void AbrirDetalhes(ItemArtigos itemArtigos){
        Intent intent = new Intent(context, Detalhes.class);
        intent.putExtra("author", itemArtigos.getAuthor());
        intent.putExtra("description", itemArtigos.getDescription());
        intent.putExtra("id", itemArtigos.getId());
        intent.putExtra("link", itemArtigos.getLink());
        intent.putExtra("title", itemArtigos.getTitle());
        intent.putExtra("year", itemArtigos.getYear());

        context.startActivity(intent);
    }
}

