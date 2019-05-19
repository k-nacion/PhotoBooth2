package com.kennedy.hiatus.photobooth.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kennedy.hiatus.photobooth.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation")
public class CameraFragment extends Fragment {

    private static final int REQUEST_CAMERA_CODE = 1;
    @BindView(R.id.camera_surface)
    SurfaceView cameraSurface;

    private SurfaceHolder surfaceHolder;
    private Camera camera;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Bind views
        ButterKnife.bind(this, view);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
        else
            initCamera();
    }

    private void initCamera() {
        surfaceHolder = cameraSurface.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(holderCallback);
    }


    private SurfaceHolder.Callback holderCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open();
            camera.setDisplayOrientation(90);

            Camera.Parameters parameters = camera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            parameters.setPreviewFpsRange(30, 60);

            Camera.Size bestSize;
            List<Camera.Size> supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
            bestSize = supportedPreviewSizes.get(0);

            for (int index = 0; index < supportedPreviewSizes.size(); index++) {
                if ((supportedPreviewSizes.get(index).height * supportedPreviewSizes.get(index).width)
                        > (bestSize.width * bestSize.height))
                    bestSize = supportedPreviewSizes.get(index);
            }

            parameters.setPictureSize(bestSize.width, bestSize.height);

            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), "surfaceCreated: Something went wrong while setting preview display", e);
            }
            camera.startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    initCamera();
                    break;
                default:
                    break;
            }
        } else {

            Toast.makeText(getContext(), "Please grant access", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
        }
    }

    public Camera getCamera() {
        if (camera != null){
            return camera;
        }
        return null;
    }
}
