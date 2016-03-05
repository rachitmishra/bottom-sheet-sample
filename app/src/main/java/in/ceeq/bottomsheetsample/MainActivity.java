package in.ceeq.bottomsheetsample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_SELECT_IMAGE = 11;
    protected File mUploadCacheFolder;
    protected String mOriginalImagePathString;
    protected String mUploadImagePathString;
    private BottomSheetBehavior mBottomSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState ) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        onBackPressed();
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Snackbar.make(v, "Camera", Snackbar.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                selectPictureFromGallery();
            }
        });


    }


    private void selectPictureFromGallery() {
        //we are using action pick because we want to avoid google drive bing shown in selector
        //when selecting from gallery.
        Intent intentSelect = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentSelect.setType("image/jpg");
        startActivityForResult(Intent.createChooser(intentSelect, "Select picture"),
                REQUEST_CODE_SELECT_IMAGE);
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SELECT_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    final Uri selectedImageUri = data.getData();
                    final Bundle bundle = new Bundle();
                    bundle.putString(GalleryWrapper.BUNDLE_SELECTED_IMAGE_URI, selectedImageUri.toString());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(
                            new Intent(GalleryWrapper.ACTION_PICTURE_SUCCESS).putExtras(bundle));
                } else {
                    LocalBroadcastManager.getInstance(this).sendBroadcast(
                            new Intent(GalleryWrapper.ACTION_PICTURE_FAILURE));
                }
                break;

        }

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(-1, -1);
    }
}
