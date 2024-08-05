package com.example.assignment.HdBuocChan;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Model.HoatDOng;
import com.example.assignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomNayFragment extends Fragment implements SensorEventListener {

    private TextView txt_sobuochn, txt_khoangcachhn, txt_calohn, txt_timehn;
    private Button btn_start, btn_stop;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isRunning = false;
    private int stepCount = 0;
    private long startTime, elapsedTime;

    private Handler handler = new Handler();
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hom_nay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_sobuochn = view.findViewById(R.id.txt_sobuoc);
        txt_khoangcachhn = view.findViewById(R.id.txt_khoangcach);
        txt_calohn = view.findViewById(R.id.txt_calo);
        txt_timehn = view.findViewById(R.id.txt_time);
        btn_start = view.findViewById(R.id.btn_start);
        btn_stop = view.findViewById(R.id.btn_stop);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        btn_start.setOnClickListener(v -> startCounting());
        btn_stop.setOnClickListener(v -> stopCounting());
    }

    private void startCounting() {
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST);
            isRunning = true;
            stepCount = 0;
            startTime = System.currentTimeMillis();
            handler.post(updateRunnable);
        }
    }

    private void stopCounting() {
        if (isRunning) {
            sensorManager.unregisterListener(this, stepSensor);
            isRunning = false;
            handler.removeCallbacks(updateRunnable);
            showStopDialog();
        }
    }

    private void showStopDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Dừng Đếm Bước Chân")
                .setMessage("Bạn có muốn lưu tiến trình không?")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    saveToFirebase();
                    // Showing a Toast message to indicate success and resetting
                    Toast.makeText(getContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    // Just dismiss the dialog
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }


    private void saveToFirebase() {
        long duration = elapsedTime / 1000;
        int calories = calculateCalories(stepCount);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        HoatDOng hoatDOng = new HoatDOng(date, stepCount, calories, duration);
        if (currentUser != null) {
            String userId = currentUser.getUid();
            db.collection("users").document(userId).collection("hoatdong")
                    .add(hoatDOng)
                    .addOnSuccessListener(documentReference -> {
                        resetAll();
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý khi lưu thất bại
                    });
        }
    }

    private void resetAll() {
        stepCount = 0;
        elapsedTime = 0;
        txt_sobuochn.setText(String.format(Locale.getDefault(), "%d", stepCount));
        txt_khoangcachhn.setText(String.format(Locale.getDefault(), "%.2f km", stepCount * 0.0008));
        txt_calohn.setText(String.format(Locale.getDefault(), "%d cal", calculateCalories(stepCount)));
        txt_timehn.setText(String.format(Locale.getDefault(), "%02d:%02d", elapsedTime / 60000, (elapsedTime % 60000) / 1000));
    }

    private int calculateCalories(int steps) {
        // Tính toán ví dụ, bạn có thể điều chỉnh theo nhu cầu
        return steps / 20;
    }

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime;
                int calories = calculateCalories(stepCount);

                txt_timehn.setText(String.format(Locale.getDefault(), "%02d:%02d", elapsedTime / 60000, (elapsedTime % 60000) / 1000));
                txt_sobuochn.setText(String.format(Locale.getDefault(), "%d", stepCount));
                txt_khoangcachhn.setText(String.format(Locale.getDefault(), "%.2f km", stepCount * 0.0008));
                txt_calohn.setText(String.format(Locale.getDefault(), "%d cal", calories));

                handler.postDelayed(this, 1000); // Cập nhật mỗi giây
            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isRunning && event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Không làm gì
    }
}
