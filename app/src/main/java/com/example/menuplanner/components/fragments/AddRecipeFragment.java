package com.example.menuplanner.components.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.menuplanner.R;
import com.example.menuplanner.entities.Recipe;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;

public class AddRecipeFragment extends Fragment {

    private final User currentUser;
    private final Db dbManager;

    private EditText name, des, ingredients;

    private Switch isPrivate;

    private Button addButton;

    private Spinner spinner;

    private final Recipe newRecipe;


    private  final String TAG = "Add_Recipe_Tag";

    public AddRecipeFragment(User currentUser, Db dbManager) {
        Log.i(TAG, currentUser.getUsername());
        this.currentUser = currentUser;
        this.dbManager = dbManager;
        this.newRecipe = new Recipe();
        this.newRecipe.setType("");
        this.newRecipe.setUsername(currentUser.getUsername());
        this.newRecipe.setIsPrivate(false);
        this.newRecipe.setTitle("");
        this.newRecipe.setDescription("");
        this.newRecipe.setIngredients("");
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

//        recipeViewModel.getRecipe().setUsername(currentUser.getUsername());

        name = view.findViewById(R.id.add_recipe_name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newRecipe.setTitle(name.getText().toString());
            }
        });

        des = view.findViewById(R.id.add_recipe_des);
        des.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newRecipe.setDescription(des.getText().toString());
            }
        });
        ingredients = view.findViewById(R.id.add_recipe_ingredient);
        ingredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newRecipe.setIngredients(ingredients.getText().toString());
            }
        });

        isPrivate = view.findViewById(R.id.add_recipe_is_private);
        isPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                newRecipe.setIsPrivate(isChecked);
            }


        });

        spinner = view.findViewById(R.id.add_recipe_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem;

                switch (position){
                    case 0:
                        selectedItem = "Breakfast";
                        break;

                    case 1:
                        selectedItem = "lunch";
                        break;

                    case 2:
                        selectedItem = "dinner";
                        break;

                    default:
                        selectedItem = "Breakfast";
                        break;

                }
                newRecipe.setType(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addButton = view.findViewById(R.id.add_recipe_btn);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (dbManager.addRecipe(newRecipe)) {
                    Toast.makeText(getContext(), newRecipe.getTitle() + "Recipe Added", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}