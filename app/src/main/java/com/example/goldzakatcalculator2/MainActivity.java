package com.example.goldzakatcalculator2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText edtWeight, edtGoldValue;
    RadioButton radioKeep, radioWear;
    Button btnCalculate, btnReset;
    // UI untuk Result Card
    TextView tvTotalValue, tvZakatPayable, tvTotalZakat;

    String appUrl = "https://github.com/Syaierah/GoldZakatCalculator2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menghubungkan Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Gold Zakat Calculator");
        }

        // Inisialisasi input
        edtWeight = findViewById(R.id.edtWeight);
        edtGoldValue = findViewById(R.id.edtGoldValue);
        radioKeep = findViewById(R.id.radioKeep);
        radioWear = findViewById(R.id.radioWear);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        // Inisialisasi output (Result Card)
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvZakatPayable = findViewById(R.id.tvZakatPayable);
        tvTotalZakat = findViewById(R.id.tvTotalZakat);

        btnCalculate.setOnClickListener(v -> calculateZakat());

        btnReset.setOnClickListener(v -> {
            edtWeight.setText("");
            edtGoldValue.setText("");
            radioKeep.setChecked(false);
            radioWear.setChecked(false);
            // Set semula paparan keputusan
            tvTotalValue.setText("RM 0.00");
            tvZakatPayable.setText("RM 0.00");
            tvTotalZakat.setText("RM 0.00");
        });
    }

    private void calculateZakat() {
        String weightText = edtWeight.getText().toString().trim();
        String valueText = edtGoldValue.getText().toString().trim();

        if (weightText.isEmpty()) {
            edtWeight.setError("Please enter gold weight");
            return;
        }
        if (valueText.isEmpty()) {
            edtGoldValue.setError("Please enter gold value");
            return;
        }
        if (!radioKeep.isChecked() && !radioWear.isChecked()) {
            Toast.makeText(this, "Please select gold type", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightText);
        double valuePerGram = Double.parseDouble(valueText);
        double uruf = radioKeep.isChecked() ? 85 : 200;

        double totalGoldValue = weight * valuePerGram;
        double zakatWeight = weight - uruf;
        if (zakatWeight < 0) zakatWeight = 0;

        double zakatPayable = zakatWeight * valuePerGram;
        double totalZakat = zakatPayable * 0.025;

        // Kemas kini paparan keputusan menggunakan format asal (concatenation)
        tvTotalValue.setText("RM " + String.format("%.2f", totalGoldValue));
        tvZakatPayable.setText("RM " + String.format("%.2f", zakatPayable));
        tvTotalZakat.setText("RM " + String.format("%.2f", totalZakat));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.menu_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Try this Gold Zakat Calculator app: " + appUrl);
            startActivity(Intent.createChooser(shareIntent, "Share App"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}