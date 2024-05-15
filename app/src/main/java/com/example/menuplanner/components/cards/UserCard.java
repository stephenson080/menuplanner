package com.example.menuplanner.components.cards;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;

public class UserCard extends RecyclerView.ViewHolder{
    public UserCard(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.user_username);
        userIsAdmin = itemView.findViewById(R.id.user_is_admin);
        newPassword = itemView.findViewById(R.id.user_record_password);
        newPasswordConfirmation = itemView.findViewById(R.id.user_record_password_confirmation);
        updatePassword = itemView.findViewById(R.id.user_record_save_new_password);
        deleteBtn = itemView.findViewById(R.id.user_delete_btn);
    }

    public TextView userName;
    public SwitchCompat userIsAdmin;
    public EditText newPassword;
    public EditText newPasswordConfirmation;
    public Button updatePassword;

    public Button deleteBtn;
}