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

public class ProfileAddressFragment extends Fragment {
    private UserViewModel userViewModel;
    private EditText address, city, state;

    private final String TAG = "UserProfileAddressTab";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_address, container, false);

        address = view.findViewById(R.id.profile_address);
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setAddress(address.getText().toString());
            }
        });

        city = view.findViewById(R.id.profile_city);
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setCity(city.getText().toString());
            }
        });

        state = view.findViewById(R.id.profile_state);
        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userViewModel.getUser().setState(state.getText().toString());
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
        address.setText(userViewModel.getUser().getAddress());
        city.setText(userViewModel.getUser().getCity());
        state.setText(userViewModel.getUser().getState());
    }
}
