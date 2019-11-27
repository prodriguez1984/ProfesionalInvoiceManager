package com.ort.profesionalinvoicemanager.views.ui.invoice;

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
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import com.ort.profesionalinvoicemanager.model.invoice.InvoiceVO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductCreate;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductInspection;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceListHolder> {
    private final ArrayList<InvoiceVO> list;
    private Context context;
    private boolean addOrEdit=false;


    public interface OnItemClickListener {
        void onItemClick(Product item);
    }

    public InvoiceAdapter(Context context, ArrayList<InvoiceVO> list) {
        this.context = context;
        this.list = list;
    }

    public static class InvoiceListHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public RelativeLayout rowClientItem;

        public InvoiceListHolder(RelativeLayout rowItem) {
            super(rowItem);
            rowClientItem = rowItem;
        }
        //Este metodo agrega el salto a la pantalla de inspeccion
        public void bind(final String invoiceOid) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), InvoiceInspect.class);
                    intent.putExtra("invoiceOid",invoiceOid);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public InvoiceListHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        // create a new view
        RelativeLayout rowView = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item, parent, false);
        InvoiceListHolder vh = new InvoiceListHolder(rowView);

        return vh;
    }

    @Override
    public void onBindViewHolder(InvoiceListHolder holder, final int position) {
        if (list.get(position) == null) {
            return;
        }
        TextView invoiceNumber = holder.rowClientItem.findViewById(R.id.invoice_docNumber);
        TextView customer = holder.rowClientItem.findViewById(R.id.invoice_customer);
        TextView price = holder.rowClientItem.findViewById(R.id.invoice_price);

        InvoiceVO invoice = list.get(position);
        holder.bind(invoice.getOid());

        invoiceNumber.setText(invoice.getInvoiceNumber());
        customer.setText(invoice.getCustomer());
        price.setText(invoice.getAmount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void changeWholeData(ArrayList<InvoiceVO> list) {
        this.list.clear();
        notifyDataSetChanged();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public boolean isAddOrEdit() {
        return addOrEdit;
    }

    public void setAddOrEdit(boolean addOrEdit) {
        this.addOrEdit = addOrEdit;
    }
}
