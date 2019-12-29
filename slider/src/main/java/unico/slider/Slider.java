package unico.slider;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Slider {
    static final String LAYOUTS = "layouts";
    static final String RES_ID = "resId";
    static final String ACTIVITY= "activity";

    private static String getClassName(AppCompatActivity activity){
        return activity.getClass().getName();
    }

    public static void openSlider(Context context, AppCompatActivity targetActivity, int[] layouts){
        Intent intent = new Intent(context, SliderActivity.class);
        intent.putExtra(Slider.ACTIVITY, Slider.getClassName(targetActivity));
        intent.putExtra(Slider.LAYOUTS, layouts);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
