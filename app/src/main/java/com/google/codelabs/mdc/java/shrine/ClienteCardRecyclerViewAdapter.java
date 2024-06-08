package com.google.codelabs.mdc.java.shrine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.codelabs.mdc.java.shrine.network.ImageRequester;
import com.google.codelabs.mdc.java.shrine.network.ClienteEntry;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class ClienteCardRecyclerViewAdapter extends RecyclerView.Adapter<ClienteCardViewHolder> {

    private List<ClienteEntry> ClienteList;
    private ImageRequester imageRequester;

    ClienteCardRecyclerViewAdapter(List<ClienteEntry> productList) {
        this.ClienteList = ClienteList;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public ClienteCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shr_cliente_card, parent, false);
        return new ClienteCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteCardViewHolder holder, int position) {
        // TODO: Put ViewHolder binding code here in MDC-102
        if (ClienteList != null && position < ClienteList.size()) {
            ClienteEntry cliente = ClienteList.get(position);

            holder.clienteNombre.setText(cliente.nombre);
            holder.clienteNumero.setText(cliente.num_factura);
            holder.clienteMonto.setText(cliente.monto_factura.toString());
            imageRequester.setImageFromUrl(holder.clienteImage, cliente.url);
        }
    }

    @Override
    public int getItemCount() {
        return ClienteList.size();
    }
}
