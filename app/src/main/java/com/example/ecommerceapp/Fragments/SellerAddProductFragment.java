package com.example.ecommerceapp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Constants;
import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.net.URI;
import java.util.HashMap;


public class SellerAddProductFragment extends Fragment {

    View view;
    protected RoundedImageView addproductimg_var;
    protected TextInputLayout title_var, description_var, quantity_var, price_var;
    protected TextView category_var;
    protected Button addproductbtn_var;

    ///permissions//
    protected static final int CAMERA_REQUEST_CODE=200;
    protected static final int STORAGE_REQUEST_CODE=300;
    ///image pick gallery
    protected static final int IMAGE_PICK_GALLERY_CODE=400;
    protected static final int IMAGE_PICK_CAMERA_CODE=500;

    ///PERMISSION ARRAYS
    protected String[] cameraPermissions;
    protected String[] storagePermissions;
    ////imgg pick uri
    protected Uri image_uri;

    protected FirebaseAuth firebaseAuth;
    protected ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_seller_add_product, container, false);
        //////hooking all fir=elds and buttons/////
        addproductimg_var = view.findViewById(R.id.addproductIcon);
        title_var = view.findViewById(R.id.titleET);
        description_var = view.findViewById(R.id.descriptionET);
        quantity_var = view.findViewById(R.id.quantityET);
        price_var = view.findViewById(R.id.priceET);

        category_var = view.findViewById(R.id.categoryTV);

        addproductbtn_var = view.findViewById(R.id.addproduct);

        firebaseAuth =FirebaseAuth.getInstance();
        //setting up progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);


        //permission arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //adding image on click
        addproductimg_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show dialog to pick image
                showImgPickDialog();
            }
        });
        //category listener
        category_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick category
                categoryDialog();
            }
        });
        //add product button
        addproductbtn_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                //validate data
                //add data to database
                inputData();

            }
        });
        return view;
    }

    private String productTitle,productDescription,productCategory,productQuantity,productPrice;
    private void inputData() {
        productTitle = title_var.getEditText().getText().toString();
        productDescription = description_var.getEditText().getText().toString();
        productCategory= category_var.getText().toString();
        productQuantity = quantity_var.getEditText().getText().toString();
        productPrice = price_var.getEditText().getText().toString();

        //validate data
        if(productTitle.isEmpty()){
            Toast.makeText(getActivity(), "Title Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(productCategory.isEmpty()){
            Toast.makeText(getActivity(), "Category Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(productDescription.isEmpty()){
            Toast.makeText(getActivity(), "Description Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(productQuantity.isEmpty()){
            Toast.makeText(getActivity(), "Quantity Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(productPrice.isEmpty()){
            Toast.makeText(getActivity(), "Price Required", Toast.LENGTH_SHORT).show();
            return;
        }
        addProduct();
    }

    private void addProduct() {
        //add data to database
        progressDialog.setMessage("Adding Product");
        progressDialog.show();

        String timestamp = ""+System.currentTimeMillis();

        if(image_uri==null){
            //upload without image
            //setup data to upload
            FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
            HashMap<String, Object> hashMap =new HashMap<>();
            hashMap.put("PId",""+timestamp);
            hashMap.put("Title",""+productTitle);
            hashMap.put("Description",""+productDescription);
            hashMap.put("Category",""+productCategory);
            hashMap.put("Quantity",""+productQuantity);
            hashMap.put("Price",""+productPrice);
            hashMap.put("Img","");//no image
            hashMap.put("TimeStamp",""+timestamp);
            hashMap.put("Uid",""+user.getUid());

            //add to db
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("seller");
            reference.child(user.getUid()).child("Products").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    //added to database
                    progressDialog.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(),"Product Added",Toast.LENGTH_SHORT).show();
                    clearData();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //failed to add product
                    progressDialog.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

        }else{
            //add with image
            //first add image to storage

            //name and path of image to be uploaded
            String filePathAndName ="product_images/"+""+timestamp;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

            storageReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //image uploaded
                    //get url
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());
                    Uri downloadImageUri = uriTask.getResult();
                    if(uriTask.isSuccessful()){
                        //url image received and uploaded
                        //setup data to upload
                        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                        HashMap<String, Object> hashMap =new HashMap<>();
                        hashMap.put("PId",""+timestamp);
                        hashMap.put("Title",""+productTitle);
                        hashMap.put("Description",""+productDescription);
                        hashMap.put("Category",""+productCategory);
                        hashMap.put("Quantity",""+productQuantity);
                        hashMap.put("Price",""+productPrice);
                        hashMap.put("Img",""+downloadImageUri);
                        hashMap.put("TimeStamp",""+timestamp);
                        hashMap.put("Uid",""+user.getUid());

                        //add to db
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("seller");
                        reference.child(user.getUid()).child("Products").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //added to database
                                progressDialog.dismiss();
                                Toast.makeText(getActivity().getApplicationContext(),"Product Added",Toast.LENGTH_SHORT).show();
                                clearData();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //failed to add product
                                progressDialog.dismiss();
                                Toast.makeText(getActivity().getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //failed uploading image
                    progressDialog.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void clearData() {
        //clear data after uploading product
        category_var.setText("");
        title_var.getEditText().setText("");
        quantity_var.getEditText().setText("");
        description_var.getEditText().setText("");
        price_var.getEditText().setText("");
        addproductimg_var.setImageResource(R.drawable.ic_baseline_add_24);
    }

    private void categoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Product Category").setItems(Constants.productCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = Constants.productCategories[which];

                //set picked categories from constants class
                category_var.setText(category);

            }
        }).show();

    }

    private void showImgPickDialog() {
        //options to display in dialog
        String[] options = {"camera","gallery"};
        //dialog
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle item clicks
                if(which==0){
                    // camera clicked
                    if(checkCameraPermission()){
                        //permission granted
                        pickFromCamera();
                    }else{
                        //permission not granted
                        requestCameraPermissions();
                    }
                }else{
                    //gallery clicked
                    if(checkStoragePermission()){
                        //permission granted
                        pickFromGallery();
                    }else{
                        //permission not granted
                        requestStoragePermission();
                    }
                }
            }
        }).show();

    }
    private void pickFromGallery(){
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera(){

        //INTENT TO PICK IMAGE FORM CAMERA
        //USING MEDIA STORE TO PICK HIGH QUALTIY IMAGE
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_Image_Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Temp_Image_Description");

        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)== (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(getActivity(),storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)== (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private  void requestCameraPermissions(){
        ActivityCompat.requestPermissions(getActivity(),cameraPermissions,CAMERA_REQUEST_CODE);
    }

    //handle permission results

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        //both permission granted
                        pickFromCamera();
                    }else{
                        //both or one permission not granted
                        Toast.makeText(getActivity(), "camera and storage permission required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        //permission granted for storage
                        pickFromGallery();
                    }else{
                        //permission not granted
                        Toast.makeText(getActivity(), "Storage permission required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //handle image pick results

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //image picked form gallery

                //save picked image
                image_uri = data.getData();

                //set image
                addproductimg_var.setImageURI(image_uri);
            }else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //image clicked from camera
                addproductimg_var.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}