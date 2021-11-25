package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

// This class is responsible for populating the store's inventory to the owner
public class SelectItemsAdapter extends
        RecyclerView.Adapter<SelectItemsAdapter.ViewHolder> {
    private Owner storeOwner;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final EditText editText;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.productDesc);
            editText = (EditText) view.findViewById(R.id.editQuantity);
            editText.setText("0");
        }

        public void setTextViewText(String newText) {
            textView.setText(newText);
        }

        public int getTextEditorValue() {
            try {
                return Integer.parseInt(this.editText.getText().toString());
            } catch (NumberFormatException exception) {
                return 0;
            }
        }
    }

    public SelectItemsAdapter(Owner storeOwner) {
        this.storeOwner = storeOwner;
    }

    @Override
    public SelectItemsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_items_products, viewGroup, false);
        return new SelectItemsAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SelectItemsAdapter.ViewHolder viewHolder, 
                                 final int position) {
        // Get element from your database at the position and replace the
        // contents of the view with that element
        if (this.storeOwner != null &&
                this.storeOwner.product_list != null) {
            Object[] products = this.storeOwner.product_list.toArray();
            Product selectedProduct = (Product) products[position];
            String text = "Name: " + selectedProduct.getName() +
                    "\nBrand: " + selectedProduct.getBrand() +
                    "\nPrice:" + selectedProduct.getPrice() +
                    "\\each";
            viewHolder.setTextViewText(text);
        }
    }

    // Return the number of items the store sells.
    @Override
    public int getItemCount() {
        return (this.storeOwner == null || this.storeOwner.product_list == null) ? -1 :
                this.storeOwner.product_list.size();
    }
}
