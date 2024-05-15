package com.example.menuplanner.components.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.menuplanner.components.fragments.ProfileAddressFragment;
import com.example.menuplanner.components.fragments.ProfileContactFragment;
import com.example.menuplanner.components.fragments.ProfileNameFragment;

public class ProfileTabStateAdapter extends FragmentStateAdapter {
    public ProfileTabStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if ( position == 0 )
            return new ProfileContactFragment();
        else if ( position == 1 )
            return new ProfileNameFragment();
        else if ( position == 2 )
            return new ProfileAddressFragment();

        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
