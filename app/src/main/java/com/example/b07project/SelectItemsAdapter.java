package com.example.b07project;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

// This class is responsible for populating the store's inventory to the owner
public class SelectItemsAdapter extends
        RecyclerView.Adapter<SelectItemsAdapter.ViewHolder> {
    private final Owner storeOwner;
    private int[] quantities;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final EditText editText;
        private final int position;

        public ViewHolder(View view) {
            super(view);
            this.position = getLayoutPosition();
            textView = (TextView) view.findViewById(R.id.productDesc);
            editText = (EditText) view.findViewById(R.id.editQuantity);
            editText.addTextChangedListener(new TextWatcher() {
                private boolean textChanged;
                String previousText, currentText;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    this.currentText = charSequence.toString();
                    this.textChanged = false;
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    this.previousText = this.currentText;
                    this.currentText = charSequence.toString().replace(
                            "-", ""); // remove possibility of negative quantities

                    if (!(this.previousText.equals(this.currentText))) {
                        // attempt to update the quantities array
                        try {
                            quantities[position] = Integer.parseInt(this.currentText);
                            textChanged = true;
                        } catch (NumberFormatException exception) {
                            this.currentText = this.previousText;
                            quantities[position] = 0;
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (textChanged) {
                        textChanged = false;
                        editText.setText(currentText); // change the text
                    }
                }
            });
        }

        public void setTextViewText(String newText) {
            textView.setText(newText);
        }

        public void setTextEditorValue(int quantity) {
            this.editText.setText(String.valueOf(quantity));
        }
    }

    public SelectItemsAdapter(Owner storeOwner) {
        this.storeOwner = storeOwner;

        if (this.storeOwner != null &&
                this.storeOwner.product_list != null) {
            quantities = new int[this.storeOwner.product_list.size()];
        }
    }

    // inflating a layout from XML and returning the holder
    @Override
    public SelectItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_items_products, viewGroup, false);
        return new SelectItemsAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SelectItemsAdapter.ViewHolder viewHolder,
                                 final int position) {
        if (this.storeOwner != null &&
                this.storeOwner.product_list != null &&
                position < this.storeOwner.product_list.size()) {
            Object[] products = this.storeOwner.product_list.toArray();
            Product selectedProduct = (Product) products[position];
            String text = "Name: " + selectedProduct.getName() +
                    "\nBrand: " + selectedProduct.getBrand() +
                    "\nPrice:" + selectedProduct.getPrice() +
                    "\\each";
            viewHolder.setTextViewText(text);
            notifyItemChanged(position);
        }

        if (quantities != null) {
            viewHolder.setTextEditorValue(quantities[position]);
        }
    }

    // Return the number of items the store sells.
    @Override
    public int getItemCount() {
        return (this.storeOwner == null || this.storeOwner.product_list == null) ? -1 :
                this.storeOwner.product_list.size();
    }

    public int getQuantityAtPosition(final int position) {
        if (this.quantities != null && position < quantities.length
                && position >= 0) {
            return this.quantities[position];
        }
        return 0;
    }
}
