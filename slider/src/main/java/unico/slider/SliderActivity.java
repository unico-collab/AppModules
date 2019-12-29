package unico.slider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import unico.app.general.FragmentItem;
import unico.app.general.PagerAdapter;

public class SliderActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private int[] layouts;

    private ViewPager viewPager;
    private Button skip, next;
    private TextView dots;

    private String className;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_view);

        if (getIntent().getIntArrayExtra(Slider.LAYOUTS) == null | getIntent().getStringExtra(Slider.ACTIVITY) == null){
            return;
        }
        className = getIntent().getStringExtra(Slider.ACTIVITY);
        layouts = getIntent().getIntArrayExtra(Slider.LAYOUTS);

        if (className == null | layouts == null){
            return;
        }

        ArrayList<FragmentItem> fragmentItems = new ArrayList<>();
        for (int resId : layouts){
            Fragment fragment = new SliderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Slider.RES_ID, resId);
            fragment.setArguments(bundle);

            fragmentItems.add(new FragmentItem(getString(R.string.app_name), fragment));
        }

        skip = findViewById(R.id.skip);
        next = findViewById(R.id.next);

        dots = findViewById(R.id.dots);

        viewPager = findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentItems);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        if (layouts.length > 0){
            viewPager.setCurrentItem(0);
            dots.setText(dots(0));
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewPager.getCurrentItem();
                int next = position + 1;
                if (next < layouts.length){
                    viewPager.setCurrentItem(next);
                } else {
                    intent();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent();
            }
        });

    }

    private void intent(){
       if (className != null){
           try {
               Intent intent = new Intent(this, Class.forName(className));
               startActivity(intent);
               finish();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }
       }

    }

    private CharSequence dots(int pos) {
        if (layouts == null){
            return null;
        }

        //dots
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<layouts.length; i++){
            stringBuilder.append("&middot;");
        }

        String dots = HtmlCompat.fromHtml(stringBuilder.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString();
        Spannable spannable = new SpannableString(dots);
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), pos, pos + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannable;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        dots.setText(dots(position));
        if (position == layouts.length - 1){
            skip.setVisibility(View.INVISIBLE);
            next.setText(getString(R.string.continue_action));
        } else {
            skip.setVisibility(View.VISIBLE);
            next.setText(getString(R.string.next));
        }
        dots.setText(dots(position));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
