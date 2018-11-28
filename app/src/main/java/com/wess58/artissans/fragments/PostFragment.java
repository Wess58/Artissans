package com.wess58.artissans.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.wess58.artissans.R;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private static final int REQUEST_SELECT_IMAGE = 1;
    private ImageButton mImageButton;
    @BindView(R.id.postButton) Button mPostButton;
    @BindView(R.id.LocationEditText) EditText mLocationEditText;
    @BindView(R.id.captionEditText) EditText mCaptionEditText;
    @BindView(R.id.PriceEditText) EditText mPriceEditText;
    @BindView(R.id.galleryicon) ImageView mGalleryIcon;
    @BindView(R.id.cameraicon) ImageView mCameraIcon;
    public Uri uri;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        mCaptionEditText = view.findViewById(R.id.captionEditText);
        mLocationEditText = view.findViewById(R.id.LocationEditText);
        mPriceEditText = view.findViewById(R.id.PriceEditText);
        mImageButton = view.findViewById(R.id.imageButton);
        mGalleryIcon = view.findViewById(R.id.galleryicon);
        mCameraIcon = view.findViewById(R.id.cameraicon);

        //<--- CLICKLISTENERS FOR IMAGE PICKING
        mGalleryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        mCameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera();
            }
        });

        //CLICKLISTENERS FOR IMAGE PICKING END --->




        mCaptionEditText.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(100) });
        mLocationEditText.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(20) });
        mPriceEditText.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(10) });




        return view;

    }

    //selectImageFromGallery Intent to choose image straight from gallery
    private void selectImageFromGallery () {
        Intent selectFromGallery = new Intent(Intent.ACTION_GET_CONTENT);
        selectFromGallery.setType("image/*");
        startActivityForResult(selectFromGallery, REQUEST_SELECT_IMAGE);
    }

//    // Launch Camera to Take pics
//    public void onLaunchCamera() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            mImageButton.setImageURI(uri);
        }




    }
}
