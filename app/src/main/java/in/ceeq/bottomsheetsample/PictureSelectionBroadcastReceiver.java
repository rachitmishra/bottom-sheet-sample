package in.ceeq.bottomsheetsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class PictureSelectionBroadcastReceiver extends BroadcastReceiver {

        private GalleryWrapper.GalleryCallback mGalleryCallback;

        public void setGalleryCallbackListener(GalleryWrapper.GalleryCallback galleryCallbackListener) {
            mGalleryCallback = galleryCallbackListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case GalleryWrapper.ACTION_PICTURE_SUCCESS:
                    Bundle bundle = intent.getExtras();
                    Uri selectedImageUri = Uri.parse(bundle.getString(GalleryWrapper.BUNDLE_SELECTED_IMAGE_URI));
                    mGalleryCallback.onPictureSelectionSuccess(selectedImageUri);
                    break;
                    default:
                        mGalleryCallback.onPictureSelectionFailure();
            }
        }
    }