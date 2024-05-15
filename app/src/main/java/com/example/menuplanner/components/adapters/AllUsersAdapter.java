package com.example.menuplanner.components.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;
import com.example.menuplanner.components.cards.UserCard;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;

import java.util.ArrayList;

public class AllUsersAdapter extends RecyclerView.Adapter<UserCard> {
    public AllUsersAdapter(Context context, User currentUser, ArrayList<User> users) {
        this.context = context;
        this.currentUser = currentUser;
        this.users = users;
    }

    Context context;
    User currentUser;
    ArrayList<User> users;

    @NonNull
    @Override
    public UserCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listContact = layoutInflater.inflate(R.layout.user_card, parent, false);
        UserCard userCard = new UserCard(listContact);
        return userCard;
    }

    @Override
    public void onBindViewHolder(@NonNull UserCard card, int position) {
        User user = users.get(position);
        String nameToDisplay = user.getUsername();
        if ( user.getUsername().equalsIgnoreCase(currentUser.getUsername()) ) {
            nameToDisplay += " - You";
        }

        card.userName.setText(nameToDisplay);
        card.deleteBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Db dbManager = new Db(context.getApplicationContext());
            int delCount = dbManager.deleteUser(user.getUsername());
            if ( delCount == 0 ) {
                Toast.makeText(context, "operation Failed!", Toast.LENGTH_SHORT).show();
                return;
            } else if ( delCount == 3 ) {
                Toast.makeText(context, "operation Successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "operation Successful!", Toast.LENGTH_SHORT).show();
            }
            for ( int i = 0; i < users.size(); i++ ) {
                if ( users.get(i).getUsername().equalsIgnoreCase(user.getUsername()) ) {
                    users.remove(i);
                    AllUsersAdapter.this.notifyDataSetChanged();
                    break;
                }
            }


        }}
        );

        card.userIsAdmin.setChecked(user.getRole().equalsIgnoreCase("admin"));
        card.userIsAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Db db = new Db(context);
                boolean success = db.setUserAdmin(user.getUsername(), isChecked);
                if ( success ) Toast.makeText(context, "Role changed successfully", Toast.LENGTH_SHORT).show();
                else Toast.makeText(context, "Operation Failed", Toast.LENGTH_SHORT).show();
            }
        });
        if ( !currentUser.getRole().equalsIgnoreCase("super_admin") ||
                user.getUsername().equalsIgnoreCase(currentUser.getUsername()) ) {
            card.userIsAdmin.setEnabled(false);
        }

        card.newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                card.updatePassword.setEnabled(false);
                String password = card.newPassword.getText().toString().trim();
                String newPasswordConfirmation = card.newPasswordConfirmation.getText().toString().trim();
                if ( password.length() > 0 && password.equals(newPasswordConfirmation) ) {
                    card.updatePassword.setEnabled(true);
                }
            }
        });

        card.newPasswordConfirmation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                card.updatePassword.setEnabled(false);
                String password = card.newPassword.getText().toString().trim();
                String newPasswordConfirmation = card.newPasswordConfirmation.getText().toString().trim();
                if ( password.length() > 0 && password.equals(newPasswordConfirmation) ) {
                    card.updatePassword.setEnabled(true);
                }
            }
        });

        card.updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = card.newPassword.getText().toString().trim();
                String newPasswordConfirmation = card.newPasswordConfirmation.getText().toString().trim();
                if ( newPassword.length() > 0 && newPassword.equals(newPasswordConfirmation) ) {
                    Db dbManager = new Db(context.getApplicationContext());
                    boolean success = dbManager.setUserPassword(user.getUsername(), newPassword);
                    if ( success )
                        Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Password not changed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ( users == null ? 0 : users.size() );
    }


}
