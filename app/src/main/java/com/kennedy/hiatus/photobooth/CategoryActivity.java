package com.kennedy.hiatus.photobooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kennedy.hiatus.photobooth.helper.StickyImmersive;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.rush_id_container)
    ConstraintLayout rushID;
    @BindView(R.id.classic_container)
    ConstraintLayout classic;
    @BindView(R.id.filters_decors_container)
    ConstraintLayout filtersDecors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus){
            View decorView = getWindow().getDecorView();
            StickyImmersive.hideSystemUI(decorView);
        }
    }

    @OnClick(R.id.rush_id_container)
    void onClick(View view){
        Intent intent = new Intent(this, RushIdActivity.class);
        startActivity(intent);
    }
}
