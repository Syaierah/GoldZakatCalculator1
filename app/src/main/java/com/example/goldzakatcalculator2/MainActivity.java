package com.example.goldzakatcalculator2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtWeight, edtGoldValue;
    RadioButton radioKeep, radioWear;
    Button btnCalculate, btnReset;
    TextView txtResult;

    String appUrl = "https://github.com/yourusername/GoldZakatCalculator2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Gold Zakat Calculator");
        }

        edtWeight = findViewById(R.id.edtWeight);
        edtGoldValue = findViewById(R.id.edtGoldValue);
        radioKeep = findViewById(R.id.radioKeep);
        radioWear = findViewById(R.id.radioWear);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        txtResult = findViewById(R.id.txtResult);

        btnCalculate.setOnClickListener(v -> calculateZakat());

        btnReset.setOnClickListener(v -> {
            edtWeight.setText("");
            edtGoldValue.setText("");
            radioKeep.setChecked(false);
            radioWear.setChecked(false);
            txtResult.setText("Calculation Result\n\nTotal Gold Value: RM 0.00\nZakat Payable: RM 0.00\nTotal Zakat (2.5%): RM 0.00");
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

        if (zakatWeight < 0) {
            zakatWeight = 0;
        }

        double zakatPayable = zakatWeight * valuePerGram;
        double totalZakat = zakatPayable * 0.025;

        String result =
                "Calculation Result\n\n" +
                        "Total Gold Value: RM " + String.format("%.2f", totalGoldValue) +
                        "\nZakat Payable: RM " + String.format("%.2f", zakatPayable) +
                        "\nTotal Zakat (2.5%): RM " + String.format("%.2f", totalZakat);

        txtResult.setText(result);
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
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Try this Gold Zakat Calculator app: " + appUrl);
            startActivity(Intent.createChooser(shareIntent, "Share App"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}