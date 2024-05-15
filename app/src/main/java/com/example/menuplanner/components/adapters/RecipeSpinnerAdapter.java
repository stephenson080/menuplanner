package com.example.menuplanner.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.menuplanner.entities.Recipe;

import java.util.List;

public class RecipeSpinnerAdapter extends ArrayAdapter<Recipe> {

    private List<Recipe> recipes;
    private LayoutInflater inflater;

    public RecipeSpinnerAdapter(Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.recipes = recipes;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            holder = new ViewHolder();
            holder.recipeNameTextView = convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Recipe recipe = recipes.get(position);
        if (recipe != null) {
            holder.recipeNameTextView.setText(recipe.getTitle());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent); // Use the same view for dropdown items
    }

    private static class ViewHolder {
        TextView recipeNameTextView;
    }
}
