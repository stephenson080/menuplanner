package com.example.menuplanner.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.menuplanner.entities.Recipe;
import com.example.menuplanner.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Db extends SQLiteOpenHelper {
    public Db(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // DB-Related Variables and Constants

    public static final String DATABASE_NAME = "menuplannerdb";
    private static final int DATABASE_VERSION = 1;

    // SQL Statements

    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (username TEXT NOT NULL, first_name TEXT, middle_name TEXT, last_name TEXT, address TEXT, city TEXT, state TEXT, email TEXT, phone TEXT)";
    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS users";

    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE recipe (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT," +
            "description TEXT," +
            "username TEXT," +
            "ingredients TEXT," +
            "is_private TEXT," +
            "type TEXT" +
            ")";


    private static final String DROP_TABLE_RECIPE = "DROP TABLE IF EXISTS recipe";
    private static final String DROP_TABLE_INGREDIENT = "DROP TABLE IF EXISTS ingredient";

    private static final String CREATE_TABLE_ROLES = "CREATE TABLE roles (role_id TEXT NOT NULL, description TEXT NOT NULL)";
    private static final String DROP_TABLE_ROLES = "DROP TABLE IF EXISTS roles";

    private static final String CREATE_TABLE_USER_ROLES = "CREATE TABLE user_roles (username TEXT NOT NULL, role TEXT NOT NULL)";
    private static final String DROP_TABLE_USER_ROLES = "DROP TABLE IF EXISTS user_roles";

    private static final String CREATE_TABLE_ACCESS = "CREATE TABLE access (username TEXT NOT NULL, password TEXT NOT NULL)";
    private static final String DROP_TABLE_ACCESS = "DROP TABLE IF EXISTS access";

    private static final String INSERT_DEFAULT_ROLES = "INSERT INTO roles (role_id, description) VALUES ('super_admin', 'Administrator'), ('user', 'User')";
    private static final String INSERT_DEFAULT_USER_ACCOUNT = "INSERT INTO users (username, first_name, middle_name, last_name, address, city, state, email, phone) VALUES ('super_admin', 'Default', '---', 'User', '---', '---', '---', '---', '---')";
    private static final String INSERT_DEFAULT_USER_PASSWORD = "INSERT INTO access (username, password) VALUES ('super_admin', 'password1234')";
    private static final String INSERT_DEFAULT_USER_ROLE = "INSERT INTO user_roles (username, role) VALUES ('super_admin', 'super_admin')";

    // Variables and Constants

    private final Context context;

    private static final String TAG = "EntityManager";

    // Required Methods

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        I am not enforcing any relationships, but it is important that the order of creation
        respects the relationships between tables. Alternatively, we can first add all the tables
        and the modify them by adding constraints.
         */
        Log.i(TAG, "CreatingTables");
        ArrayList<String> createDefaulttables = new ArrayList<>();
        createDefaulttables.add(CREATE_TABLE_ROLES);
        createDefaulttables.add(CREATE_TABLE_USERS);
        createDefaulttables.add(CREATE_TABLE_ACCESS);
        createDefaulttables.add(CREATE_TABLE_USER_ROLES);

        createDefaulttables.add(CREATE_TABLE_RECIPE);

        // Creating the database
        Log.i(TAG, "onCreate: Creating the database");
        for ( String statement : createDefaulttables ) {
            Log.i(TAG, "onCreate: " + statement);
            db.execSQL(statement);
        }

        /*
        The order of INSERT statements also matters, if we are enforcing relationships.
         */
        ArrayList<String> initInsert = new ArrayList<>();
        initInsert.add(INSERT_DEFAULT_ROLES);
        initInsert.add(INSERT_DEFAULT_USER_ACCOUNT);
        initInsert.add(INSERT_DEFAULT_USER_PASSWORD);
        initInsert.add(INSERT_DEFAULT_USER_ROLE);

        Log.i(TAG, "onCreate: populating database");
        for ( String statement : initInsert ) {
            db.execSQL(statement);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        /*
        For the drops, we have to work backwards so any tables with FKs is deleted before
        we delete the tables that they reference.
         */
        ArrayList<String> dropStatements = new ArrayList<>();
        dropStatements.add(DROP_TABLE_USER_ROLES);
        dropStatements.add(DROP_TABLE_ACCESS);
        dropStatements.add(DROP_TABLE_USERS);
        dropStatements.add(DROP_TABLE_ROLES);

        dropStatements.add(DROP_TABLE_RECIPE);
        dropStatements.add(DROP_TABLE_INGREDIENT);

        Log.i(TAG, "onUpgrade: going from version " + oldVersion + " to version " + newVersion);
        // Dropping all tables
        for ( String statement : dropStatements ) {
            Log.i(TAG, "onUpgrade: " + statement);
            myDB.execSQL(statement);
        }
        onCreate(myDB);
    }

    // Custom Methods

    public String getUserPassword(String username) {
        if ( username == null )
            return null;

        username = username.trim();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT password FROM access WHERE username='"+username+"'";

        Cursor result = db.rawQuery(query, null);
        if ( result.getCount() != 1 ) {
            db.close();
            result.close();
            return null;
        }

        result.moveToFirst();

        int columnIndex = result.getColumnIndex("password");
        String password = result.getString(columnIndex);

        db.close();
        db.close();

        return password;

    }

    public Boolean login(String username, String password) {
            String dbPassword = this.getUserPassword(username);
            return dbPassword != null && dbPassword.equals(password);
    }



    public boolean setUserPassword(String username, String newPassword) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues updatedUserValues = new ContentValues();
        updatedUserValues.put("password", newPassword);

        int numRowsAffected = myDB.update("access", updatedUserValues, "username = ?", new String[]{username});

        return numRowsAffected == 1;
    }

    public boolean signup(String username, String password) {
        if ( username == null || password == null )
            return false;

        username = username.trim();
        password = password.trim();

        if ( getUserPassword(username) != null ) {
            Toast.makeText(context, "Username already exist", Toast.LENGTH_SHORT).show();
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newUser = new ContentValues();
        newUser.put("username", username);
        newUser.put("first_name", User.INFO_NOT_AVAILABLE);
        newUser.put("middle_name", User.INFO_NOT_AVAILABLE);
        newUser.put("last_name", User.INFO_NOT_AVAILABLE);
        newUser.put("address", User.INFO_NOT_AVAILABLE);
        newUser.put("city", User.INFO_NOT_AVAILABLE);
        newUser.put("state", User.INFO_NOT_AVAILABLE);
        newUser.put("zip", User.INFO_NOT_AVAILABLE);
        newUser.put("email", User.INFO_NOT_AVAILABLE);
        newUser.put("phone", User.INFO_NOT_AVAILABLE);
        db.insert("users", null, newUser);

        ContentValues newUserRoleRecord = new ContentValues();
        newUserRoleRecord.put("username", username);
        newUserRoleRecord.put("role", User.DEFAULT_ROLE);
        db.insert("user_roles", null, newUserRoleRecord);

        ContentValues newAccessRecord = new ContentValues();
        newAccessRecord.put("username", username);
        newAccessRecord.put("password", password);
        db.insert("access", null, newAccessRecord);


        db.close();
        return true;
    }

    public User getUser(String username) {

        String query = "SELECT * FROM users WHERE username = '"  + username + "'";

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor result = db.rawQuery(query, null);

        if ( result.getCount() == 0 || result.getCount() > 1 ) {
            db.close();
            return null;
        }

        result.moveToFirst();

        User user = new User();
        user.setUsername(username);

        int firstNameIndex = result.getColumnIndex("first_name");
        user.setFirstName(result.getString(firstNameIndex));

        int middleNameIndex = result.getColumnIndex("middle_name");
        user.setMiddleName(result.getString(middleNameIndex));

        int lastNameIndex = result.getColumnIndex("last_name");
        user.setLastName(result.getString(lastNameIndex));

        int addressIndex = result.getColumnIndex("address");
        user.setAddress(result.getString(addressIndex));

        int cityIndex = result.getColumnIndex("city");
        user.setCity(result.getString(cityIndex));

        int stateIndex = result.getColumnIndex("state");
        user.setState(result.getString(stateIndex));

        int emailIndex = result.getColumnIndex("email");
        user.setEmail(result.getString(emailIndex));

        int phoneIndex = result.getColumnIndex("phone");
        user.setPhone(result.getString(phoneIndex));

        db.close();
        return user;
    }

    public boolean updateUser(User updateuser) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues updateUserDto = new ContentValues();
        updateUserDto.put("username", updateuser.getUsername());
        updateUserDto.put("first_name", updateuser.getFirstName());
        updateUserDto.put("middle_name", updateuser.getMiddleName());
        updateUserDto.put("last_name", updateuser.getLastName());
        updateUserDto.put("address", updateuser.getAddress());
        updateUserDto.put("city", updateuser.getCity());
        updateUserDto.put("state", updateuser.getState());
        updateUserDto.put("email", updateuser.getEmail());
        updateUserDto.put("phone", updateuser.getPhone());

        int numRowsAffected = myDB.update("users", updateUserDto, "username = ?", new String[]{updateuser.getUsername()});

        myDB.close();

        return numRowsAffected == 1;
    }

    // TODO: Implement this method
    public ArrayList<User> getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT U.username, first_name, last_name, role FROM users U, user_roles UR WHERE U.username == UR.username";
        Cursor result = db.rawQuery(query, null);


        if ( result.getCount() == 0 ) {
            return null;
        }

        result.moveToFirst();

        ArrayList<User> usersList = new ArrayList<>();

        int colIndexUsername = result.getColumnIndex("username");
        int colIndexFirstName = result.getColumnIndex("first_name");
        int colIndexLastName = result.getColumnIndex("last_name");
        int colIndexRole = result.getColumnIndex("role");

        for ( int i = 0; i < result.getCount(); i++ ) {
            User u = new User(result.getString(colIndexUsername));
            u.setFirstName(result.getString(colIndexFirstName));
            u.setLastName(result.getString(colIndexLastName));
            u.setRole(result.getString(colIndexRole));
            usersList.add(u);
            result.moveToNext();
        }

        return usersList;
    }

    public boolean setUserAdmin(String username, boolean isAdmin) {
        String role = (isAdmin ? "admin" : "standard");

        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues updatedUserValues = new ContentValues();
        updatedUserValues.put("role", role);

        int numRowsAffected = myDB.update("user_roles", updatedUserValues, "username = ?", new String[]{username});

        return numRowsAffected == 1;
    }

    public String getUserRole(String username) {

        String query = "SELECT role FROM user_roles WHERE username = '"  + username + "'";

        SQLiteDatabase myDB = this.getReadableDatabase();


        Cursor result = myDB.rawQuery(query, null);

        if ( result.getCount() == 0 || result.getCount() > 1 ) {
            myDB.close();
            return null;
        }

        result.moveToFirst();

        int roleIndex = result.getColumnIndex("role");
        return result.getString(roleIndex);
    }

    public int deleteUser(String username) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        int delCount = 0;
        delCount += myDB.delete("users", "username = ?", new String[] {String.valueOf(username)} );
        delCount += myDB.delete("user_roles", "username = ?", new String[] {String.valueOf(username)} );
        delCount += myDB.delete("access", "username = ?", new String[] {String.valueOf(username)} );
        return delCount;
    }

    public boolean addRecipe(Recipe recipe) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("title", recipe.getTitle());
            values.put("description", recipe.getDescription());
            values.put("is_private", recipe.getIsPrivate() ? "true" : "false");
            values.put("username", recipe.getUsername());
            values.put("ingredients", recipe.getIngredients());
            values.put("type", recipe.getType());
            int rowId = (int) db.insert("recipe", null, values);
            Log.i(TAG, "RECIPE ADD!" + rowId);
            return true;
        } catch (SQLException err) {
            Log.e(TAG, err.getMessage());
            return false;
        }
    }


    public boolean editRecipe(Recipe recipe) {
        String isPrivate = (recipe.getIsPrivate() ? "true" : "false");

        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", recipe.getTitle());
        values.put("description", recipe.getDescription());
        values.put("is_private", isPrivate);
        values.put("username", recipe.getUsername());
        values.put("ingredients", recipe.getIngredients());
        values.put("type", recipe.getType());

        String _id = String.valueOf(recipe.get_id());
        int numRowsAffected = myDB.update("recipe", values, "id = ?", new String[]{_id});

        Log.i(TAG,"Edited Recipe");

        return numRowsAffected == 1;
    }

    public boolean deleteRecipe(int id) {

        SQLiteDatabase myDB = this.getWritableDatabase();

        String _id = String.valueOf(id);
        int numRowsAffected = myDB.delete("recipe", "id = ?", new String[]{_id});

        Log.i(TAG,"Deleted Recipe");

        return numRowsAffected == 1;
    }


    public ArrayList<Recipe> getAllRecipes() {
        Log.i(TAG, "getAllRecipe: Getting all recipes");

        String query = "SELECT * FROM recipe";

        SQLiteDatabase myDB = this.getReadableDatabase();

        Log.i(TAG, "getAllRecipes: Query - " + query);

        Cursor resultSet = myDB.rawQuery(query, null);



        Log.i(TAG, "getAllRecipes: Number of recipes " + resultSet.getCount());

        if ( resultSet.getCount() == 0 ) {
            Log.i(TAG, "getAllRecipes: No users found");
            return new ArrayList<>(0);
        }

        resultSet.moveToFirst();

        ArrayList<Recipe> recipes = new ArrayList<>();

        int idColumn = resultSet.getColumnIndex("id");
        int titleColumn = resultSet.getColumnIndex("title");
        int descriptionColumn = resultSet.getColumnIndex("description");
        int ingredientColumn = resultSet.getColumnIndex("ingredients");
        int usernameColumn = resultSet.getColumnIndex("username");
        int isPrivateColumn = resultSet.getColumnIndex("is_private");
        int typeColumn = resultSet.getColumnIndex("type");



        for ( int i = 0; i < resultSet.getCount(); i++ ) {
            Recipe r = new Recipe();

            r.set_id(resultSet.getInt(idColumn));
            r.setDescription(resultSet.getString(descriptionColumn));
            r.setTitle(resultSet.getString(titleColumn));
            r.setIngredients(resultSet.getString(ingredientColumn));
            r.setUsername(resultSet.getString(usernameColumn));
            r.setType(resultSet.getString(typeColumn));
            String _isPrivate = resultSet.getString(isPrivateColumn);
            Boolean isPrivate = _isPrivate.equalsIgnoreCase("true");
            r.setIsPrivate(isPrivate);

            recipes.add(r);
            resultSet.moveToNext();
        }

        return recipes;
    }






}
