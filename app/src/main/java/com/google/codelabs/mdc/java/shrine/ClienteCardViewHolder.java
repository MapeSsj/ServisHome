package com.google.codelabs.mdc.java.shrine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

public class ClienteCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView clienteImage;
    public TextView clienteNombre;
    public TextView clienteDireccion;
    public TextView clienteDni;
    public TextView clienteNumero;
    public TextView clienteCantidad;
    public TextView clienteMonto;
    public TextView clienteEnlaceInfo;
    public TextView clienteEnlaceImag;

    public ClienteCardViewHolder(@NonNull View itemView) {
        super(itemView);
        //TODO: Find and store views from itemView
        clienteImage = itemView.findViewById(R.id.cliente_image);
        clienteNombre = itemView.findViewById(R.id.cliente_title);
        clienteNumero = itemView.findViewById(R.id.cliente_price);
        clienteMonto = itemView.findViewById(R.id.cliente_monto);
    }
}
