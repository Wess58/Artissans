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
import com.wess58.artissans.ui.CustomToast;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    public static View view;
    public Uri uri;

    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private static final int REQUEST_SELECT_IMAGE = 1;

    private ImageButton mImageButton;


    FirebaseStorage storage;
    StorageReference storageReference;


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
        EditText mCaptionEditText = view.findViewById(R.id.captionEditText);
        EditText mLocationEditText = view.findViewById(R.id.LocationEditText);
        EditText mPriceEditText = view.findViewById(R.id.PriceEditText);
        mImageButton = view.findViewById(R.id.imageButton);
        ImageView mGalleryIcon = view.findViewById(R.id.galleryicon);
        ImageView mCameraIcon = view.findViewById(R.id.cameraicon);
        Button mPostButton = view.findViewById(R.id.postButton);

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


        mCaptionEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        mLocationEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        mPriceEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});


        return view;

    }


    //selectImageFromGallery Intent to choose image straight from gallery
    private void selectImageFromGallery() {
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

        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            new CustomToast().Show_Toast(getContext(), view,
                                    "Uploaded");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            new CustomToast().Show_Toast(getContext(), view,
                                    "Failed " + e.getMessage());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    //Upload Images to Firebase using FireBase Storage END --->


}
