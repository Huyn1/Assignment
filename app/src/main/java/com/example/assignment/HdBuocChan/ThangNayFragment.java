package com.example.assignment.HdBuocChan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Adapter.HoatDongAdapter;
import com.example.assignment.Model.HoatDOng;
import com.example.assignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ThangNayFragment extends Fragment {

    private RecyclerView rcv_lsuchay;
    private HoatDongAdapter hoatDongAdapter;
    private List<HoatDOng> hoatDongList;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thang_nay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_lsuchay = view.findViewById(R.id.rcv_lsuchay);
        rcv_lsuchay.setLayoutManager(new LinearLayoutManager(getContext()));

        hoatDongList = new ArrayList<>();
        hoatDongAdapter = new HoatDongAdapter(getContext(), hoatDongList);
        rcv_lsuchay.setAdapter(hoatDongAdapter);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        getListHoatDong();
    }

    private void getListHoatDong() {
        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("users").document(userId).collection("hoatdong")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            hoatDongList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HoatDOng hoatDOng = document.toObject(HoatDOng.class);
                                hoatDongList.add(hoatDOng);
                            }
                            hoatDongAdapter.notifyDataSetChanged();
                        } else {

                        }
                    });
        }
    }
}
