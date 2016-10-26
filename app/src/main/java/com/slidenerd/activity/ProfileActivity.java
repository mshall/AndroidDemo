package com.slidenerd.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.slidenerd.R;
import com.slidenerd.util.Utility;
import com.slidenerd.util.image.RoundedImageConverter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.bProfileChangePicture)
    Button bChangePicture;
    String userChoosenTask = "test";
    private boolean storeImage = false;
    private File outFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.smily);
//        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.smily)).getBitmap();
//        Drawable d = getResources().getDrawable(R.drawable.smily);
//        Bitmap bitmap = drawableToBitmap(d);
        Bitmap circularBitmap = RoundedImageConverter.getRoundedCornerBitmap(bitmap, 100);

        ivProfile.setImageBitmap(circularBitmap);
    }


    @OnClick(R.id.bProfileChangePicture)
    protected void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Profile Picture!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ProfileActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result) {
                        cameraIntent();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    //-----------------------------------------------------------
    // This methods responsible for setting image coming from
    // camera or gallery
    //-----------------------------------------------------------
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bundle extras = imageReturnedIntent.getExtras();
        switch (requestCode) {

            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    performCrop(selectedImage);
                    //ivProfile.setImageURI(selectedImage);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    ivProfile.setImageURI(selectedImage);
//                    Bitmap selecBitmap = extras.getParcelable("data");
//                    ivProfile.setImageBitmap(selecBitmap);
                    Uri selectedImage = imageReturnedIntent.getData();
//                    ivProfile.setImageURI(selectedImage);
                    performCrop(selectedImage);
                }
                break;

            case 100: //Crop code = 100
                // user is returning from cropping the image

                // get the returned data
                Bundle cropExtras = imageReturnedIntent.getExtras();
                // get the cropped bitmap
                Bitmap thePic = cropExtras.getParcelable("data");
                ivProfile.setImageBitmap(thePic);


                break;
        }
    }


    //-----------------------------------------------------------
    // This methods responsible for taking action
    // depending on type of choosing action
    //-----------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                    Toast.makeText(ProfileActivity.this, "Didn't choose any", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    //-----------------------------------------------------------
    // This methods responsible for navigating to gallery
    //-----------------------------------------------------------
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    //-----------------------------------------------------------
    // This methods responsible for navigating to gallery
    //-----------------------------------------------------------
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        // ******** code for crop image
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void performCrop(Uri picUri) {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 2);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 350);
            cropIntent.putExtra("outputY", 350);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 100);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
