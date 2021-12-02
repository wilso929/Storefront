package com.example.b07project;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        private MyEditTextListener myEditTextListener;
        private final int position;

        public ViewHolder(View view, MyEditTextListener myEditTextListener) {
            super(view);
            this.position = getAdapterPosition();
            textView = (TextView) view.findViewById(R.id.productDesc);
            editText = (EditText) view.findViewById(R.id.editQuantity);
            this.myEditTextListener = myEditTextListener;
            editText.addTextChangedListener(myEditTextListener);
        }

        public void setTextViewText(String newText) {
            textView.setText(newText);
        }

        public void setTextEditorValue(int quantity) {
            this.editText.setText(String.valueOf(quantity));
        }
    }

    private class MyEditTextListener implements TextWatcher{
        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

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
                } catch (NullPointerException exception) {
                    System.out.println("NullPointerException occurred when attempting to " +
                            "update quantity. Null Quantities Array");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_items_products, viewGroup, false);
        return new ViewHolder(view, new MyEditTextListener());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,
                                 final int position) {
        viewHolder.myEditTextListener.updatePosition(viewHolder.getAdapterPosition());
        if (this.storeOwner != null &&
                this.storeOwner.product_list != null &&
                position < this.storeOwner.product_list.size() && position >= 0) {
            Object[] products = this.storeOwner.product_list.toArray();
            Product selectedProduct = (Product) products[position];
            String text = "Name: " + selectedProduct.getName() + "\nBrand: " +
                    selectedProduct.getBrand() + "\nPrice: " + selectedProduct.getPrice() +
                    " \\ each";
            viewHolder.setTextViewText(text);
            // notifyItemChanged(position);
        }

        if (quantities != null &&
                position < this.storeOwner.product_list.size() && position >= 0) {
            viewHolder.setTextEditorValue(quantities[position]);
            // notifyItemChanged(position);
        }
    }

    // Return the number of items the store sells.
    @Override
    public int getItemCount() {
        return (this.storeOwner == null || this.storeOwner.product_list == null) ? -1 :
                this.storeOwner.product_list.size();
    }

    public int getQuantityAtPosition(final int position) {
        if (this.quantities != null && position < quantities.length && position >= 0) {
            return this.quantities[position];
        }
        return 0;
    }
}
