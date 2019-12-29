package unico.app.general;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<FragmentItem> fragmentItems;
    public PagerAdapter(@NonNull FragmentManager fm, ArrayList<FragmentItem> fragmentItems) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentItems = fragmentItems;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentItems.get(position).getTitle();
    }
}
