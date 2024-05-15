package com.example.menuplanner.components.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuplanner.R;
import com.example.menuplanner.views.UserViewModel;

public class ProfileContactFragment extends Fragment {
    public ProfileContactFragment() {

    }

    private final String TAG = "ProfileContactsFragment";
    private UserViewModel userViewModel;
    private EditText email, phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contact, container, false);

        email = view.findViewById(R.id.profile_email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setEmail(email.getText().toString());
            }
        });

        phone = view.findViewById(R.id.profile_phone);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setPhone(phone.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( userViewModel.getUser() == null ) {
            return;
        }
        email.setText(userViewModel.getUser().getEmail());
        phone.setText(userViewModel.getUser().getPhone());
    }
}
