package com.example.assignment.navfragment;

import static com.example.assignment.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.assignment.HdBuocChan.ViewBuocChan;
import com.example.assignment.MainActivity;
import com.example.assignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class TaiKhoanFragment extends Fragment {

   private View view;
   private ImageView img_avtup;
   EditText edtFullName, edt_emailup;
   Button btnUpdateName;
   private Uri mUri;
   private  MainActivity mMainActivity;

   private ProgressDialog poget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.tai_khoan_frm, container, false);

        initUi();
        mMainActivity = (MainActivity) getActivity();
        poget = new ProgressDialog(getActivity());
        setUpdateInformation();
        initListener();
        return view;
    }

 

    private void initUi() {
        img_avtup = view.findViewById(R.id.img_avt_update);
        edtFullName = view.findViewById(R.id.edt_fullname);
        edt_emailup = view.findViewById(R.id.edt_emailup);
        btnUpdateName = view.findViewById(R.id.btn_update_name);
    }

    public void setUpdateInformation () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        edtFullName.setText(user.getDisplayName());
        edt_emailup.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.user).into(img_avtup);
    }
    private void initListener() {
    img_avtup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickRequestPermisstion();
        }


    });
    btnUpdateName.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickUpdateDate();
        }
    });
    
    }



    private void onClickRequestPermisstion() {

        if (mMainActivity == null){
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mMainActivity.openGallery();
            return;
        }

        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            mMainActivity.openGallery();
        } else {
            String [] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permisstions,MY_REQUEST_CODE);
        }


    }
    public void setBitmapImageView(Bitmap bitmapImageView) {
        img_avtup.setImageBitmap(bitmapImageView);
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

    private void onClickUpdateDate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        poget.show();
        String name = edtFullName.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        poget.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Update thành công", Toast.LENGTH_SHORT).show();
                            mMainActivity.showUserInformation();
                        }
                    }
                });
    }

}
