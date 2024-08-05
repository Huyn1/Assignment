package com.example.assignment.navfragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment.R;

public class AnUongFragment extends Fragment {
    EditText edtCanNang, edtChieuCao;
    Button btntinh;
    TextView txtLoaiBeo, txtThucAnNenAn,txt_BMI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_an_uong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtCanNang = view.findViewById(R.id.edt_cannngau);
        edtChieuCao = view.findViewById(R.id.edt_chieucaoan);
        btntinh = view.findViewById(R.id.btn_tinh);
        txtLoaiBeo = view.findViewById(R.id.txtLoaiBeo);
        txtThucAnNenAn = view.findViewById(R.id.txtNenAn);
        txt_BMI = view.findViewById(R.id.txt_BMI);

        btntinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String weightStr = edtCanNang.getText().toString().trim();
        String heightStr = edtChieuCao.getText().toString().trim();

        if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(heightStr)) {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ cân nặng và chiều cao", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr);

            if (weight <= 0 || height <= 0) {
                Toast.makeText(getActivity(), "Cân nặng và chiều cao phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }

            height = height / 100; // chuyển đổi chiều cao sang mét
            float bmi = weight / (height * height);
            txt_BMI.setText(String.format("BMI: %.2f", bmi));
            displayResults(bmi);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Vui lòng nhập số hợp lệ cho cân nặng và chiều cao", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayResults(float bmi) {
        String bmiCategory;
        String dietaryRecommendation;

        if (bmi < 18.5) {
            bmiCategory = "Thiếu cân";
            dietaryRecommendation = "Tăng lượng calo với các thực phẩm giàu dinh dưỡng. Ví dụ:\n" + "- Các loại hạt và hạt giống\n" + "- Quả bơ\n" + "- Bánh mì nguyên hạt\n" + "- Thịt nạc\n" + "- Các sản phẩm từ sữa như sữa, sữa chua và phô mai";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            bmiCategory = "Cân nặng bình thường";
            dietaryRecommendation = "Duy trì chế độ ăn cân bằng. Ví dụ:\n" + "- Đa dạng các loại rau và trái cây\n" + "- Ngũ cốc nguyên hạt như gạo lứt và quinoa\n" + "- Protein nạc như gà, cá và đậu\n" + "- Chất béo lành mạnh như dầu ô liu và các loại hạt";
        } else if (bmi >= 25 && bmi < 29.9) {
            bmiCategory = "Thừa cân";
            dietaryRecommendation = "Cân nhắc chế độ ăn giàu trái cây, rau và protein nạc. Ví dụ:\n" + "- Rau lá xanh như cải bó xôi và cải xoăn\n" + "- Rau họ cải như bông cải xanh và súp lơ\n" + "- Trái cây tươi như dâu và táo\n" + "- Protein nạc như gà tây và đậu hũ\n" + "- Ngũ cốc nguyên hạt như yến mạch và lúa mạch";
        } else {
            bmiCategory = "Béo phì";
            dietaryRecommendation = "Tham khảo ý kiến của chuyên gia y tế để có kế hoạch ăn uống cá nhân. Khuyến nghị chung bao gồm:\n" + "- Rau nhiều chất xơ như cà rốt và ớt chuông\n" + "- Trái cây ít calo như dâu và dưa\n" + "- Protein nạc như cá và đậu\n" + "- Ngũ cốc nguyên hạt như quinoa và mì ống nguyên hạt\n" + "- Tránh các thực phẩm nhiều đường và chất béo";
        }

        txtLoaiBeo.setText("Loại BMI: " + bmiCategory);
        txtThucAnNenAn.setText("Khuyến nghị ăn uống: " + dietaryRecommendation);
    }
}
