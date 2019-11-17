package com.ort.profesionalinvoicemanager.views.ui.products;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;
import com.ort.profesionalinvoicemanager.views.ui.ClientList.ClientListAdapter;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductListHolder>  {
    private ArrayList<Product> list;

    public ProductAdapter(ArrayList<Product> list){
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
    public void onBindViewHolder(ProductListHolder holder, int position) {
        if(list.get(position) == null){
            return;
        }
        TextView productName = holder.rowClientItem.findViewById(R.id.productName_textview);
        TextView productDesc = holder.rowClientItem.findViewById(R.id.productDescription_textview);
        TextView productPrice = holder.rowClientItem.findViewById(R.id.price_textview);

        Product product = list.get(position);
        productName.setText(product.getName());
        productDesc.setText(product.getDescription());
        productPrice.setText(product.getPrice().toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
