package com.example.menuplanner.activities.meal_plan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.MainActivity;
import com.example.menuplanner.components.adapters.RecipeSpinnerAdapter;
import com.example.menuplanner.entities.MealPlan;
import com.example.menuplanner.entities.Recipe;
import com.example.menuplanner.utils.Db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMealPlanActivity extends AppCompatActivity {

    private TextView selectedDateField ;
    private TextView selectedRecipe;

    private Switch isPrivateSwitch;
    private Spinner mealTypeSpinner;
    private Spinner recipesSpinner;

    private Calendar calendar;

    private MealPlan newMealPlan;

    private Button backBtn;
    private Button addMealBtn;

    private Button selecteddateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_plan);

        newMealPlan = new MealPlan();
        newMealPlan.setIsPrivate(false);

        selectedDateField = findViewById(R.id.add_meal_selected_date);
        selectedRecipe = findViewById(R.id.add_meal_recipe);

        initFormControls();
        registerRecipesSpinner();


        addMealBtn = findViewById(R.id.add_meal_btn);

        backBtn = findViewById(R.id.add_meal_back_btn);

        isPrivateSwitch = findViewById(R.id.add_meal_is_private);

        mealTypeSpinner = findViewById(R.id.add_meal_meal_type);

        mealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) parent.getItemAtPosition(position);

                newMealPlan.setMealType(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        isPrivateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newMealPlan.setIsPrivate(isChecked);
            }
        });

        addMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newMealPlan.getMealType() == null || newMealPlan.getRecipe() == null || newMealPlan.getDate() == null){
                    Toast.makeText(AddMealPlanActivity.this, "Fill all form fields", Toast.LENGTH_SHORT).show();
                }else {
                    Db db = new Db(getApplicationContext());
                    boolean success = db.addMealPlan(newMealPlan);
                    if (success){
                        Toast.makeText(AddMealPlanActivity.this, "New Meal Plan Added!", Toast.LENGTH_SHORT).show();
                        if (mealPlanAddedListener != null) {
                            mealPlanAddedListener.onMealPlanAdded();
                        }
                        finish();
                    }
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMealPlanActivity.this.finish();
            }
        });

    }

    private void initFormControls(){
        this.selecteddateBtn = findViewById(R.id.add_meal_select_date_btn);

        calendar = Calendar.getInstance();

        this.selecteddateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddMealPlanActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Note: monthOfYear is zero-based, so January is month 0.
                                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                LocalDate selectedDateObj = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    selectedDateObj = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                                }
                                newMealPlan.setDate(selectedDateObj);
                                selectedDateField.setText(selectedDate);
                            }
                        },
                        year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
    }

    private void registerRecipesSpinner(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(MainActivity.DEFAULT_USER_LOGIN, 0);
        String activeUser = sharedPreferences.getString(MainActivity.DEFAULT_USERNAME, MainActivity.SP_ANON_USER);
        Db db = new Db(getApplicationContext());
        ArrayList<Recipe> recipes = db.getRecipesByUsername(activeUser);

        RecipeSpinnerAdapter adapter = new RecipeSpinnerAdapter(this, android.R.layout.simple_spinner_item, recipes);
        this.recipesSpinner = findViewById(R.id.add_meal_recipe_spinner);
        this.selectedRecipe = findViewById(R.id.add_meal_recipe);

        try{
            this.recipesSpinner.setAdapter(adapter);

            this.recipesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Recipe selectedItem = (Recipe) parent.getItemAtPosition(position);
                    selectedRecipe.setText(selectedItem.getTitle());
                    newMealPlan.setRecipe(selectedItem);
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

    private static MealPlanAddedListener mealPlanAddedListener;

    public static void setMealPlanAddedListener(MealPlanAddedListener listener) {
        mealPlanAddedListener = listener;
    }

    public interface MealPlanAddedListener {
        void onMealPlanAdded();
    }
}