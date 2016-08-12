package com.example.nora.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton takePhoto, sharePhoto, chooseFromGallery;
    private final int REQUEST_CAMERA= 1, REQUEST_GALLERY= 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePhoto = (ImageButton) findViewById(R.id.take_photo_view);
        sharePhoto= (ImageButton)findViewById(R.id.share_view);
        imageView= (ImageView)findViewById(R.id.image_view);
        chooseFromGallery= (ImageButton)findViewById(R.id.choose_from_gallery);


        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCamera, REQUEST_CAMERA);


            }
        });
        chooseFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery= new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                openGallery.setType("image/*");
                openGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(openGallery, "Select Image"), REQUEST_GALLERY );
            }
        });
        sharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent= new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                view.getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CAMERA) {
            takePhoto.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            sharePhoto.setVisibility(View.VISIBLE);
            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmapImage);
        }
        else if(requestCode== REQUEST_GALLERY){
            takePhoto.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            sharePhoto.setVisibility(View.VISIBLE);
            Uri y= data.getData();

            try {
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), y);
                //imageView.setImageURI(y);
               // imageView.setImageBitmap(BitmapFactory.decodeFile(y.getPath()));
                imageView.setImageBitmap(bitmapImage);
            }
            catch(IOException e){
                e.printStackTrace();
            }
//            File path= new File(y);
//            String x= path.getPath();
//            Bitmap bitmapImageSelected= (Bitmap) data.getData(x.);
        }
    }

}
