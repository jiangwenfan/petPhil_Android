package com.example.petphil.testDebug;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.petphil.R;

import java.io.File;
import java.io.IOException;

public class CameraTest extends AppCompatActivity {
    private static final int TAKE_PHOTO = 1;
    ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        picture = (ImageView)findViewById(R.id.picture);

        //File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        //File outputImage = new File(getExternalFilesDir("/").getAbsolutePath(),"output_image.jpg");
        //File outputImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),"output_image.jpg");
        String currentPhotoPath;
        File image = null;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
           image = File.createTempFile(
                    "testpic",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            currentPhotoPath = image.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File outputImage = image;
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(CameraTest.this,"com.example.petphil.fileprovider",outputImage);

        }else{
            imageUri = Uri.fromFile(outputImage);
        }
        // start camera program
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                      //显示拍摄照片
                      Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                      picture.setImageBitmap(bitmap);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
