package com.example.ecommerceapp;

import android.widget.Filter;

import com.example.ecommerceapp.adapters.AdapterProductSeller;
import com.example.ecommerceapp.models.ModelProduct;

import java.util.ArrayList;

public class FilterProductseller extends Filter {
    private ArrayList<ModelProduct> filterList;
    private AdapterProductSeller adapter;

    public FilterProductseller(ArrayList<ModelProduct> filterList, AdapterProductSeller adapter) {
        this.filterList = filterList;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        //validate data for search query
        if(constraint != null && constraint.length()>0){
            //search filed not empty

            //change to uppercase to make case insensitive
            constraint = constraint.toString().toUpperCase();
            //store our filtered list
            ArrayList<ModelProduct> filterModels = new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                //check, search by title and category
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint) ||
                filterList.get(i).getCategory().toUpperCase().contains(constraint)){
                    //add filtered data to list
                    filterModels.add(filterList.get(i));
                }
            }
            filterResults.count = filterModels.size();
            filterResults.values = filterModels;


        }else{
            //search filed empty, not searching, return all original list
            filterResults.count = filterList.size();
            filterResults.values = filterList;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.productList =(ArrayList<ModelProduct>) results.values;
        //refresh adapter
        adapter.notifyDataSetChanged();
    }
}
