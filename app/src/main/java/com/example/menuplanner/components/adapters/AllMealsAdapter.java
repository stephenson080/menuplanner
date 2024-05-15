package com.example.menuplanner.components.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.MainActivity;
import com.example.menuplanner.components.cards.MealPlanCard;
import com.example.menuplanner.entities.MealPlan;
import com.example.menuplanner.entities.Recipe;
import com.example.menuplanner.utils.Db;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class AllMealsAdapter extends RecyclerView.Adapter<MealPlanCard> {
    public AllMealsAdapter(Context context, ArrayList<MealPlan> mealPlans) {
        this.context = context;
        this.mealPlans = mealPlans;
    }

    Context context;
    ArrayList<MealPlan> mealPlans;

    LocalDate selectedDate;
    Recipe selectedRecipe;

    int mealPlanId;

    @NonNull
    @Override
    public MealPlanCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mealPlanCardWidget = layoutInflater.inflate(R.layout.meal_plan_card, parent, false);
        MealPlanCard mealPlanCard = new MealPlanCard(mealPlanCardWidget);
        return mealPlanCard;
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanCard card, int position) {
        DateTimeFormatter dtf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        MealPlan mealPlan = mealPlans.get(position);
        LocalDate date = mealPlan.getDate();
        String dateToDisplay = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateToDisplay = dtf.format(date);
        }


        card.selectedDate.setText(dateToDisplay);

        selectedDate = date;

        registerRecipesSpinner(card, mealPlan);

        initFormControls(card, mealPlan);


        card.mealType.setText(mealPlan.getMealType());
        card.mealtypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);

                card.mealType.setText(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        card.isPrivate.setChecked(mealPlan.getIsPrivate());
        card.isPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                card.isPrivate.setChecked(isChecked);
            }
        });

        card.editMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Db db = new Db(context);
                MealPlan editMeal = new MealPlan();
                editMeal.setIsPrivate(card.isPrivate.isChecked());
                editMeal.setMealType(card.mealType.getText().toString());
                editMeal.setRecipe(selectedRecipe);
                editMeal.setDate(selectedDate);
                editMeal.set_id(mealPlan.get_id());
                boolean success = db.editMealPlan(editMeal);
                if (success){
                    Toast.makeText(context, "Meal plan edited", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Operation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        card.deleteMealBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Db dbManager = new Db(context);
                boolean success = dbManager.deleteMealPlan(mealPlan.get_id());
                if ( !success ) {
                    Toast.makeText(context, "operation Failed!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "operation Successful!", Toast.LENGTH_SHORT).show();
                        for ( int i = 0; i < mealPlans.size(); i++ ) {
                            if ( mealPlans.get(i).get_id() == mealPlan.get_id() ) {
                                mealPlans.remove(i);
                                AllMealsAdapter.this.notifyDataSetChanged();
                                break;
                            }
                        }
                }



            }}
        );


    }

    @Override
    public int getItemCount() {
        return ( mealPlans == null ? 0 : mealPlans.size() );
    }

    private void registerRecipesSpinner(MealPlanCard card, MealPlan mealPlan){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(MainActivity.DEFAULT_USER_LOGIN, 0);
        String activeUser = sharedPreferences.getString(MainActivity.DEFAULT_USERNAME, MainActivity.SP_ANON_USER);
        Db db = new Db(this.context.getApplicationContext());
        ArrayList<Recipe> recipes = db.getRecipesByUsername(activeUser);

        RecipeSpinnerAdapter adapter = new RecipeSpinnerAdapter(this.context, android.R.layout.simple_spinner_item, recipes);

        card.selectedRecipe.setText(mealPlan.getRecipe().getTitle());
        selectedRecipe = mealPlan.getRecipe();
        try{
            card.recipesSpinner.setAdapter(adapter);

            card.recipesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Recipe selectedItem = (Recipe) parent.getItemAtPosition(position);
                    card.selectedRecipe.setText(selectedItem.getTitle());
                    selectedRecipe = selectedItem;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle case when nothing is selected
                }
            });
        }catch (Exception e){
            Log.e("Add meal", e.getMessage());
        }


    }

    private void initFormControls(MealPlanCard card, MealPlan mealPlan){

        Calendar calendar = Calendar.getInstance();

        card.selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                       AllMealsAdapter.this.context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DateTimeFormatter dtf = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                }
                                String dateToDisplay = null;
                                LocalDate date = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                                }
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    dateToDisplay = dtf.format(date);
                                }


                                card.selectedDate.setText(dateToDisplay);
                                selectedDate = date;
                            }
                        },
                        year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
    }

    public void updateData(ArrayList<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }
}
