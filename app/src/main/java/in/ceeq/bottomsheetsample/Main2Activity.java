package in.ceeq.bottomsheetsample;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity implements GalleryWrapper.GalleryCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new GalleryWrapper(Main2Activity.this, false).selectPicture();
            }
        });
    }


    @Override
    public void onPictureSelectionSuccess(final Uri selectedImageUri) {
        ImageView.class.cast(findViewById(R.id.imageView)).setImageURI(selectedImageUri);
    }

    @Override
    public void onPictureSelectionFailure() {
        Snackbar.make(findViewById(android.R.id.content),
                "Uh, oh! Geez, someone stole the pictures.", Snackbar.LENGTH_SHORT).show();
    }
}
