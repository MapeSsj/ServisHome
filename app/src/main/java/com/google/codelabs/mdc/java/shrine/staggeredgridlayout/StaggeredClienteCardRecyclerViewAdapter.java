package com.google.codelabs.mdc.java.shrine.staggeredgridlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.codelabs.mdc.java.shrine.network.ClienteEntry;
import com.google.codelabs.mdc.java.shrine.network.ImageRequester;

import java.util.List;

/**
 * Adapter used to show an asymmetric grid of products, with 2 items in the first column, and 1
 * item in the second column, and so on.
 */
public class StaggeredClienteCardRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredClienteCardViewHolder> {

    private List<ClienteEntry> clienteList;
    private ImageRequester imageRequester;

    public StaggeredClienteCardRecyclerViewAdapter(List<ClienteEntry> clienteList) {
        this.clienteList = clienteList;
        imageRequester = ImageRequester.getInstance();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @NonNull
    @Override
    public StaggeredClienteCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.shr_staggered_cliente_card_first;
        if (viewType == 1) {
            layoutId = R.layout.shr_staggered_cliente_card_second;
        } else if (viewType == 2) {
            layoutId = R.layout.shr_staggered_cliente_card_third;
        }

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new StaggeredClienteCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredClienteCardViewHolder holder, int position) {
        if (clienteList != null && position < clienteList.size()) {
            ClienteEntry cliente = clienteList.get(position);
            holder.clienteTitle.setText(cliente.nombre);
            holder.clientePrice.setText(cliente.num_factura.toString());
            holder.clienteMonto.setText(cliente.monto_factura.toString());

            imageRequester.setImageFromUrl(holder.clienteImage, cliente.url);
        }
    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }
}
