package com.example.assignment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.assignment.navfragment.AnUongFragment;
import com.example.assignment.navfragment.BietOnFragment;
import com.example.assignment.navfragment.HoatDongFragment;
import com.example.assignment.navfragment.TaiKhoanFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int MY_REQUEST_CODE = 10;
    private static final int BIET_ON = 0;
    private static final int HOAT_DONG = 1;
    private static final int AN_UONG = 2;
    private static final int TAI_KHOAN = 3;
    int currentFramgment = AN_UONG;
    private DrawerLayout m;
    NavigationView mnavigationView;
    private ImageView img_avt;
    private TextView txtNameHeader,txtEmailHeader;

     final private TaiKhoanFragment taiKhoanFragment = new TaiKhoanFragment();
   final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if ((o.getResultCode() == RESULT_OK)) {
                Intent intent = o.getData();
                if (intent == null) {
                    return;
                }
                Uri uri = intent.getData();
                taiKhoanFragment.setUri(uri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    taiKhoanFragment.setBitmapImageView(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUi();
        m = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle t = new ActionBarDrawerToggle(this, m, toolbar, R.string.app_mo, R.string.app_dong);
        m.addDrawerListener(t);
        t.syncState();


        mnavigationView.setNavigationItemSelectedListener(this);
        showUserInformation();

    }
    private void initUi () {
        mnavigationView = findViewById(R.id.nav_view);
        img_avt =  mnavigationView.getHeaderView(0).findViewById(R.id.img_avt);
        txtNameHeader =  mnavigationView.getHeaderView(0).findViewById(R.id.txtUserHeade);
        txtEmailHeader =  mnavigationView.getHeaderView(0).findViewById(R.id.txt_emailhear);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_hoatdong) {
            if (currentFramgment != HOAT_DONG) {
                raplaceFragment(new HoatDongFragment());
                currentFramgment = HOAT_DONG;
            }

        } else if (id == R.id.nav_bieton) {
            if (currentFramgment != BIET_ON) {
                raplaceFragment(new BietOnFragment());
                currentFramgment = BIET_ON;
            }
        } else if (id == R.id.nav_anuong) {
            if (currentFramgment != AN_UONG) {
                raplaceFragment(new AnUongFragment());
                currentFramgment = AN_UONG;
            }
        } else if (id == R.id.nav_dangxuat) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,DangNhap.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_taikhoan) {
            if (currentFramgment != TAI_KHOAN) {
                raplaceFragment(taiKhoanFragment);
                currentFramgment = TAI_KHOAN;
            }

        }
        m.closeDrawer(GravityCompat.START);
        return true;
    }

    private void raplaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
    public void showUserInformation () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        if (name == null) {
            txtNameHeader.setVisibility(View.GONE);

        } else {
            txtNameHeader.setVisibility(View.VISIBLE) ;
        txtNameHeader.setText(name);
    }
        txtEmailHeader.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.user).into(img_avt);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Ficture"));
    }
}