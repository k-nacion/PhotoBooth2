package com.kennedy.hiatus.photobooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.kennedy.hiatus.photobooth.callbacks.JpegCallback;
import com.kennedy.hiatus.photobooth.fragments.CameraFragment;
import com.kennedy.hiatus.photobooth.fragments.PlaceholderFragment;
import com.kennedy.hiatus.photobooth.helper.StickyImmersive;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RushIdActivity extends AppCompatActivity {

    @BindView(R.id.camera_fragment_container)
    FrameLayout cameraFragmentContainer;
    @BindView(R.id.capture_button)
    ExtendedFloatingActionButton captureButton;
    @BindView(R.id.done_button)
    ExtendedFloatingActionButton doneButton;
    @BindView(R.id.retake_button)
    ExtendedFloatingActionButton retakeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rush_id);

        ButterKnife.bind(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.camera_fragment_container, new CameraFragment());
        transaction.commit();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus){
            View decorView = getWindow().getDecorView();
            StickyImmersive.hideSystemUI(decorView);
        }
    }

    @OnClick(R.id.capture_button)
    void onClick(View view){
        CameraFragment instance = new CameraFragment();
        Camera camera = instance.getCamera();
        camera.takePicture(null, null, new JpegCallback());
    }
}
