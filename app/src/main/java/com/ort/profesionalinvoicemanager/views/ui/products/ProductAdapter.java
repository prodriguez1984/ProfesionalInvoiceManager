package com.ort.profesionalinvoicemanager.views.ui.products;

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

import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.MainActivity;
import com.ort.profesionalinvoicemanager.views.R;
import com.ort.profesionalinvoicemanager.views.SignUpActivity;
import com.ort.profesionalinvoicemanager.views.ui.ClientList.ClientListAdapter;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductListHolder>  {
    private ArrayList<Product> list;
    private Context context;

    public ProductAdapter(Context context,ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    public static class ProductListHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout rowClientItem;
        public ProductListHolder(RelativeLayout rowItem) {
            super(rowItem);
            rowClientItem = rowItem;
        }
    }

    @Override
    public ProductListHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        RelativeLayout rowView = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductListHolder vh = new ProductListHolder(rowView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductListHolder holder, final int position) {
        if(list.get(position) == null){
            return;
        }
        ImageView icon = holder.rowClientItem.findViewById(R.id.product_icon);
        TextView productName = holder.rowClientItem.findViewById(R.id.productName_textview);
        TextView productDesc = holder.rowClientItem.findViewById(R.id.productDescription_textview);
        TextView productPrice = holder.rowClientItem.findViewById(R.id.price_textview);
        ImageButton btnEdit= holder.rowClientItem.findViewById(R.id.product_btn_edit);
        ImageButton btnDetele= holder.rowClientItem.findViewById(R.id.product_btn_delete);

        Product product = list.get(position);
        if (Product.IDENTIFICATOR_PRODUCT.equals(product.getProductType())){
            icon.setImageResource(R.drawable.ic_product);
        }else{
            icon.setImageResource(R.drawable.ic_service);
        }
        productName.setText(product.getName());
        productDesc.setText(product.getDescription());
        productPrice.setText(product.getPrice().toString());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductCreate.class);
                v.getContext().startActivity(intent);
            }
        });

        btnDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = list.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(v.getContext())
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProductDAO.getInstance().deleteProduct(product);

                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,list.size());
                                Toast.makeText(v.getContext(),"Producto Eliminado : " + product.getName(),Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar el producto " + product.getName() + "?")
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
    }


}
