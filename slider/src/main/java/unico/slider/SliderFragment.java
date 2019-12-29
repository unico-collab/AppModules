package unico.slider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SliderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() != null){
            return null;
        }

        if (getArguments() == null) {
            return null;
        }
        int resId = getArguments().getInt(Slider.RES_ID);
        return LayoutInflater.from(getActivity()).inflate(resId, container, false);
    }
}
