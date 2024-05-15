package com.example.menuplanner.components.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuplanner.R;
import com.example.menuplanner.views.UserViewModel;

public class ProfileNameFragment extends Fragment {
    public ProfileNameFragment() {
    }

    private UserViewModel userViewModel;
    private EditText firstName;
    private EditText middleName;
    private EditText lastName;

    private final String TAG = "ProfileNameFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile_name, container, false);

        firstName = view.findViewById(R.id.profile_first_name);
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setFirstName(firstName.getText().toString());
            }
        });

        middleName = view.findViewById(R.id.profile_other_name);
        middleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setMiddleName(middleName.getText().toString());
            }
        });

        lastName = view.findViewById(R.id.profile_last_name);
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setLastName(lastName.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( userViewModel.getUser() == null ) {
            return;
        }
        firstName.setText((userViewModel.getUser().getFirstName()));
        middleName.setText((userViewModel.getUser().getMiddleName()));
        lastName.setText((userViewModel.getUser().getLastName()));
    }
}
