package unico.app.general;

import androidx.fragment.app.Fragment;

public class FragmentItem {
    private String title;
    private Fragment fragment;
    public FragmentItem(String title, Fragment fragment){
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
