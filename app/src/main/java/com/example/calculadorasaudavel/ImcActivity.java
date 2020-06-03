package com.example.calculadorasaudavel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ImcActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        editWeight = findViewById(R.id.imc_weight);
        editHeight = findViewById(R.id.imc_heigt);
        Button btnSend = findViewById(R.id.imc_send);


        btnSend.setOnClickListener(v -> {

            if (!validate()) {
                Toast.makeText(ImcActivity.this, R.string.fields_message, Toast.LENGTH_LONG).show();

            }

            String sHeight = editHeight.getText().toString();
            String sWeight = editWeight.getText().toString();

            int height = Integer.parseInt(sHeight);
            double weight = Double.parseDouble(sWeight);

            double imc = calculateImc(height, weight);
            int resId = imcResponse(imc);

            AlertDialog alertDialog = new AlertDialog.Builder(ImcActivity.this)
                    .setTitle(getString(R.string.imc_response, imc))
                    .setMessage(resId)
                    .setNegativeButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(R.string.save, (dialog, which) -> {
                        SqlHelper sqlHelper = SqlHelper.getInstance(ImcActivity.this);
                        long calcId = sqlHelper.addItem(SqlHelper.TYPE_IMC, imc);
                        if (calcId > 0)
                            Toast.makeText(ImcActivity.this, R.string.calc_save, Toast.LENGTH_LONG).show();
                        openListCalcActivity();

                    })
                    .create();

            alertDialog.show();

            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert im != null;
            im.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
            im.hideSoftInputFromWindow(editWeight.getWindowToken(), 0);


        });
    }

    private void openListCalcActivity() {

        Intent intent = new Intent(ImcActivity.this, ListCalcActivity.class);
        intent.putExtra("type", SqlHelper.TYPE_IMC);
        ImcActivity.this.startActivity(intent);
    }

    @StringRes
    private int imcResponse(double imc) {
        if (imc < 15)
            return R.string.imc_severely_low_weight;
        else if (imc < 16)
            return R.string.imc_very_low_weight;
        else if (imc < 18.5)
            return R.string.imc_low_weight;
        else if (imc < 25)
            return R.string.normal;
        else if (imc < 30)
            return R.string.imc_high_weight;
        else if (imc < 35)
            return R.string.imc_so_high_weight;
        else if (imc < 40)
            return R.string.imc_severely_low_weight;
        else return R.string.imc_extreme_weight;
    }

    private double calculateImc(int height, double weight) {
        //peso dividido (altura * altura)
        return weight / (((double) height / 100) * ((double) height / 100));
    }

    private boolean validate() {
        return !editHeight.getText().toString().startsWith("0")
                && !editWeight.getText().toString().startsWith("0")
                && !editWeight.getText().toString().isEmpty()
                && !editHeight.getText().toString().isEmpty();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                openListCalcActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
