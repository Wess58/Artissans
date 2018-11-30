package com.wess58.artissans.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wess58.artissans.R;

import java.util.UUID;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    FirebaseStorage storage;
    StorageReference storageReference;

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

        //References can be seen as pointers to a file in the cloud
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        mCaptionEditText = view.findViewById(R.id.captionEditText);
        mLocationEditText = view.findViewById(R.id.LocationEditText);
        mPriceEditText = view.findViewById(R.id.PriceEditText);
        mImageButton = view.findViewById(R.id.imageButton);
        mGalleryIcon = view.findViewById(R.id.galleryicon);
        mCameraIcon = view.findViewById(R.id.cameraicon);
        mPostButton = view.findViewById(R.id.postButton);

        //<--- CLICKLISTENERS FOR IMAGE PICKING AND POST IMAGE
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
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
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

    // Launch Camera to Take pics
    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            mImageButton.setImageURI(uri);
        }
    }


    //<--- Upload Images to Firebase using FireBase Storage START

    private void uploadImage() {

        if(uri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    //Upload Images to Firebase using FireBase Storage END --->


}
