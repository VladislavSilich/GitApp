package com.example.lenovo.testgif;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.lenovo.testgif.managers.DataManager;
import com.example.lenovo.testgif.utils.ConstantManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static String TAG = "myLogs";
    private int navigationDrawerCount = 0;
    private int mCurrentEditMode ;
    private FloatingActionButton mFab;
    private DataManager mDataManager;
    ImageView mCallImg;
    CoordinatorLayout mCoordinatorLayout;
    Toolbar mToolBar;
    DrawerLayout mDrawerLayout;
    EditText mUserTel,mUserEmail,mUserVk,mUserGit,mUserBio;
    RelativeLayout mProfilePlaceholder;
    CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout mAppBarLayout;
    private List<EditText> mUserInfoViews;
    private AppBarLayout.LayoutParams mAppBarParams = null;
    private File mPhotoFile = null;
    private Uri mSelectedImage = null;
    ImageView mProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        Log.d(TAG,"onCreate");
        mDataManager = DataManager.getInstance();

        mCallImg = (ImageView)findViewById(R.id.callImg);
        mToolBar = (Toolbar)findViewById(R.id.toolBar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.navigation_drawer);
        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_layout);
        mFab = (FloatingActionButton)findViewById(R.id.mFab);
        mUserTel = (EditText)findViewById(R.id.edtMobile);
        mUserEmail = (EditText)findViewById(R.id.edtEmail);
        mUserVk = (EditText)findViewById(R.id.edtVk);
        mUserGit = (EditText)findViewById(R.id.edtRepository);
        mUserBio = (EditText)findViewById(R.id.edtMySelf);
        mProfileImage = (ImageView)findViewById(R.id.user_photo);
        mCollapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        mToolBar.setTitle("Владислав Силич");
        mProfilePlaceholder = (RelativeLayout)findViewById(R.id.profile_placeholder);
        mAppBarLayout = (AppBarLayout)findViewById(R.id.appbarlayout);
        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserTel);
        mUserInfoViews.add(mUserEmail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);

        mFab.setOnClickListener(this);
        mProfilePlaceholder.setOnClickListener(this);
        setupToolBar();
        setupDrawer();
        loadUserInfoValue();
        Picasso.with(this).load(mDataManager.getPreferencesManager().LoadUserPhoto()).into(mProfileImage);


        // тестирование сохранения данных
        //List<String> test = mDataManager.getPreferencesManager().loadUserProfileData();
        if (savedInstanceState == null){
        }
        else {
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY);
            changeEditMode(mCurrentEditMode);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            navigationDrawerCount = 1;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mFab :
                if (mCurrentEditMode == 0){
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                }
                else {
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }
                break;
            case R.id.profile_placeholder:
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                break;
        }
    }
    private void showSnackbar (String message){
        Snackbar.make(mCoordinatorLayout,message,Snackbar.LENGTH_SHORT).show();
    }

    private void setupToolBar(){
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();

        mAppBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_view_headline_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawerCount == 1) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            navigationDrawerCount = 0;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUserInfoValue();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY,mCurrentEditMode);
    }

    private void setupDrawer(){
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                navigationDrawerCount = 0;
                return false;
            }
        });
    }
    private void changeEditMode (int mode){
        if (mode == 1) {
            mFab.setImageResource(R.drawable.ic_check_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                showProfilePlaceHolder();
                lookToolbar();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            }
        }
        else {
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                hideProfilePlaceHolder();
                unlockToolbar();
                saveUserInfoValue();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
            }
        }
    }

    private void loadUserInfoValue(){
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i <userData.size();i++){
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }
    private void saveUserInfoValue(){
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews){
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    }

    private void loadPhotoGallery(){
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent,"Выберите фото"),ConstantManager.REQUEST_GALLERY_PICTURES);
    }
    private void loadPhotoCamera(){

        Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try{
            mPhotoFile = createImageFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if (mPhotoFile != null){
            takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
            startActivityForResult(takeCaptureIntent,ConstantManager.REQUEST_CAMERA_PICTURES);
        }
    }

    private void hideProfilePlaceHolder(){
        mProfilePlaceholder.setVisibility(View.GONE);
    }
    private void showProfilePlaceHolder(){
        mProfilePlaceholder.setVisibility(View.VISIBLE);
    }

    private void lookToolbar(){
        mAppBarLayout.setExpanded(true,true);
        mAppBarParams.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }
    private void unlockToolbar(){
        mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ConstantManager.REQUEST_GALLERY_PICTURES:
                if (resultCode == RESULT_OK && data != null){
                    mSelectedImage = data.getData();
                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURES:
                if (resultCode == RESULT_OK && data != null){
                    mSelectedImage = Uri.fromFile(mPhotoFile);
                    insertProfileImage(mSelectedImage);
                }
        }
    }

    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(this).load(selectedImage).into(mProfileImage);
        mDataManager.getPreferencesManager().SaveUserPhoto(selectedImage);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItems = {"Загрузить из галереи","Сделать снимок","Отмена"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Изменить фотографию профиля");
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0 :
                                loadPhotoGallery();
                                break;
                            case 1 :
                                loadPhotoCamera();
                                break;
                            case 2 :
                                dialog.cancel();
                                break;
                        }
                    }
                });
                return builder.create();

            default:
                return null;
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        return image;
    }
}


