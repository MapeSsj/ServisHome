package com.google.codelabs.mdc.java.shrine.staggeredgridlayout;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.codelabs.mdc.java.shrine.R;

public class StaggeredClienteCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView clienteImage;
    public TextView clienteTitle;
    public TextView clientePrice;

    public TextView clienteMonto;

    StaggeredClienteCardViewHolder(@NonNull View itemView) {
        super(itemView);
        clienteImage = itemView.findViewById(R.id.cliente_image);
        clienteTitle = itemView.findViewById(R.id.cliente_title);
        clientePrice = itemView.findViewById(R.id.cliente_price);
        clienteMonto = itemView.findViewById(R.id.cliente_monto);
    }
}
