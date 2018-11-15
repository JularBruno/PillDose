package com.example.brunojular.pilldose;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<PillVO> listDatos;

    public AdapterDatos(ArrayList<PillVO> listDatos) {
        this.listDatos = listDatos;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pill_list,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.modulo.setText(listDatos.get(position).getModulo());
        holder.pill.setText(listDatos.get(position).getPill());
        holder.horario.setText(listDatos.get(position).getHorario());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView modulo, pill, horario;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            modulo = (TextView) itemView.findViewById(R.id.idModulo);
            pill = (TextView) itemView.findViewById(R.id.idPastilla);
            horario = (TextView) itemView.findViewById(R.id.idHorario);

        }

    }
}
