package com.ort.profesionalinvoicemanager.views.ui.ClientList;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.views.ClientFragment;
import com.ort.profesionalinvoicemanager.views.R;

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
        Client client = clientList.get(position);
        clientNameTV.setText(client.getName());
        //TextView clientNameTV = holder.rowClientItem.findViewById(R.id.title_textview);
        ImageView iconOptions = holder.rowClientItem.findViewById(R.id.icon_imageview);
        iconOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                Context wrapper = new ContextThemeWrapper(mCtx, R.style.MyPopupMenu);
                PopupMenu popup = new PopupMenu (wrapper, holder.iconOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.row_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popUpMenu_inspect:
                                //handle menu1 click
                                Fragment clientFragment = new ClientFragment();
                                ((FragmentActivity)mCtx).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.nav_host_fragment, clientFragment)
                                        .commit();
                                break;
                            case R.id.popUpMenu_edit:
                                //handle menu2 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return clientList.size();
    }
}