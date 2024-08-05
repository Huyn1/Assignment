package com.example.assignment.navfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Adapter.BietOnAdapter;
import com.example.assignment.Model.DieuBietOn;
import com.example.assignment.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BietOnFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    ImageButton add;
    Context context;
    RecyclerView recyclerView;
    BietOnAdapter adapter;
    List<DieuBietOn> dataList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biet_on, container, false);

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recycler_view);
        dataList = new ArrayList<>();
        adapter = new BietOnAdapter(context, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        FirebaseApp.initializeApp(requireContext());
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getListBietOn();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add = view.findViewById(R.id.img_add);
        add.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater1 = LayoutInflater.from(context);
            View viewda = inflater1.inflate(R.layout.dialog_bieton, null, false);
            builder.setView(viewda);
            AlertDialog dialog = builder.create();
            dialog.show();

            EditText eddieu1 = viewda.findViewById(R.id.txt_dieu1);
            EditText eddieu2 = viewda.findViewById(R.id.txt_dieu2);
            EditText eddieu3 = viewda.findViewById(R.id.txt_dieu3);
            EditText eddieu4 = viewda.findViewById(R.id.txt_dieu4);
            EditText eddieu5 = viewda.findViewById(R.id.txt_dieu5);
            Button them = viewda.findViewById(R.id.btn_save);

            them.setOnClickListener(v1 -> {
                String dieu1 = eddieu1.getText().toString();
                String dieu2 = eddieu2.getText().toString();
                String dieu3 = eddieu3.getText().toString();
                String dieu4 = eddieu4.getText().toString();
                String dieu5 = eddieu5.getText().toString();
                if (dieu1.isEmpty() || dieu2.isEmpty() || dieu3.isEmpty() || dieu4.isEmpty() || dieu5.isEmpty()) {
                    Toast.makeText(context, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the current date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String currentDate = sdf.format(new Date());

                    // Create a new DieuBietOn object
                    DieuBietOn dieuBietOn = new DieuBietOn(currentDate, dieu1, dieu2, dieu3, dieu4, dieu5);

                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        String userId = user.getUid(); // Lấy ID người dùng hiện tại

                        // Add the new item to Firestore
                        db.collection("users").document(userId).collection("bieton")
                                .add(dieuBietOn)
                                .addOnSuccessListener(documentReference -> {
                                    dataList.add(dieuBietOn);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(context, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("loimes", e.getMessage());
                                    Toast.makeText(context, "Lỗi khi lưu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(context, "Không thể xác định người dùng", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void getListBietOn() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid(); // Lấy ID người dùng hiện tại

            db.collection("users").document(userId).collection("bieton")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            dataList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DieuBietOn dieuBietOn = document.toObject(DieuBietOn.class);
                                dataList.add(dieuBietOn);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Lỗi khi tải dữ liệu: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(context, "Không thể xác định người dùng", Toast.LENGTH_SHORT).show();
        }
    }
}
