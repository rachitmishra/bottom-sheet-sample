package in.ceeq.bottomsheetsample;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rachit on 04/03/16.
 */
public class GalleryWrapper {

    private boolean mFromCamera;
    public static final String ACTION_PICTURE_SUCCESS = "picture_selection_success";
    public static final String ACTION_PICTURE_FAILURE = "picture_selection_failure";
    public static final String BUNDLE_SELECTED_IMAGE_URI = "selected_image_uri";
    private Activity mActivity;

    public interface GalleryCallback {
        void onPictureSelectionSuccess(Uri selectedImageUri);
        void onPictureSelectionFailure();
    }

    public GalleryWrapper(AppCompatActivity activity, boolean fromCamera) {
        mActivity = activity;
        mFromCamera = fromCamera;
        initBroadcastReceiver(activity);
    }

    private void initBroadcastReceiver(final Activity activity) {
        final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(activity);
        PictureSelectionBroadcastReceiver pictureSelectionBroadcastReceiver = new PictureSelectionBroadcastReceiver();
        pictureSelectionBroadcastReceiver.setGalleryCallbackListener((GalleryCallback) activity);
        localBroadcastManager.registerReceiver(pictureSelectionBroadcastReceiver, new IntentFilter(ACTION_PICTURE_SUCCESS));
    }

    public void selectPicture() {
        Intent startGalleryIntent = new Intent(mActivity, MainActivity.class);
        startGalleryIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mActivity.startActivity(startGalleryIntent);
    }
}
