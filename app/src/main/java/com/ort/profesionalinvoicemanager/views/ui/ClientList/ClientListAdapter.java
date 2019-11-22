package com.ort.profesionalinvoicemanager.views.ui.ClientList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ort.profesionalinvoicemanager.DAO.ClientDAO;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductInspection;

import java.util.ArrayList;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClienListHolder> {
    //private String[] mDataset;
    private ArrayList<Client> clientList;
    private Context mCtx;

    public ClientListAdapter(ArrayList<Client> clientList, Context context ){
        this.clientList = clientList;
        this.mCtx = context;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ClienListHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout rowClientItem;
        public ImageView iconOptions;
        public ClienListHolder(RelativeLayout rowItem) {
            super(rowItem);
            rowClientItem = rowItem;
            iconOptions = rowItem.findViewById(R.id.icon_imageview);
        }

        //Este metodo agrega el salto a la pantalla de inspeccion
        public void bind(final String oid) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ClientInspection.class);
                    intent.putExtra("productOid",oid);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

/*
    // Provide a suitable constructor (depends on the kind of dataset)
    public ClientListAdapter(String[] myDataset) {
        mDataset = myDataset;
    }
*/

    // Create new views (invoked by the layout manager)
    @Override
    public ClienListHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        RelativeLayout rowView = (RelativeLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        ClienListHolder vh = new ClienListHolder(rowView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ClienListHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(clientList.get(position) == null){
            return;
        }
        TextView clientNameTV = holder.rowClientItem.findViewById(R.id.title_textview);
        TextView clientDescriptionTV = holder.rowClientItem.findViewById(R.id.subtitle_textview);
        Client client = clientList.get(position);
        clientNameTV.setText(client.getName());
        //TextView clientNameTV = holder.rowClientItem.findViewById(R.id.title_textview);
        ImageButton btnEdit = holder.rowClientItem.findViewById(R.id.client_btn_edit);
        ImageButton btnDelete = holder.rowClientItem.findViewById(R.id.client_btn_delete);
        ImageButton btnActive = holder.rowClientItem.findViewById(R.id.client_btn_Active);

        if (!client.isActive()) {
            btnDelete.setVisibility(View.GONE);
            btnActive.setVisibility(View.VISIBLE);
            clientNameTV.setTextColor(mCtx.getResources().getColor(R.color.material_on_primary_disabled));
            clientDescriptionTV.setTextColor(mCtx.getResources().getColor(R.color.material_on_primary_disabled));
        } else {
            btnActive.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            clientNameTV.setTextColor(mCtx.getResources().getColor(R.color.primary_text));
            clientDescriptionTV.setTextColor(mCtx.getResources().getColor(R.color.primary_text));
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client client = clientList.get(position);
                Intent intent = new Intent(v.getContext(), ProductInspection.class);
                intent.putExtra("product",client);
                v.getContext().startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client client = clientList.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(v.getContext())
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClientDAO.getInstance().delete(client);

                                clientList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, clientList.size());
                                Toast.makeText(v.getContext(), "Cliente Eliminado : " + client.getName(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar el cliene " + client.getName() + "?")
                        .create();
                dialog.show();
            }
        });

        btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client client = clientList.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(v.getContext())
                        .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Client result = ClientDAO.getInstance().activeClient(client);
                                notifyDataSetChanged();
                                Toast.makeText(v.getContext(), "Cliente Activado : " + client.getName(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Activar el cliente " + client.getName() + "?")
                        .create();
                dialog.show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void changeWholeData(ArrayList<Client> list) {
        this.clientList.clear();
        notifyDataSetChanged();
        this.clientList.addAll(list);
        notifyDataSetChanged();
    }
}