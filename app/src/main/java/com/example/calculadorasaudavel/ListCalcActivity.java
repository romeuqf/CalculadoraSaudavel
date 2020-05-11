package com.example.calculadorasaudavel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    private List<SqlHelper.Register> registers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            String type = extras.getString("type");

            SqlHelper db = SqlHelper.getInstance(this);
            registers.addAll(db.getRegisterBy(type));
            ListCalcAdapter listCalcAdapter = new ListCalcAdapter(registers);
            RecyclerView recyclerView = findViewById(R.id.recycle_view_list);
            recyclerView.setAdapter(listCalcAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        }

    }

    private class ListCalcAdapter extends RecyclerView.Adapter<ListCalcViewHolder> {

        private final List<SqlHelper.Register> registers;

        ListCalcAdapter(List<SqlHelper.Register> registers) {
            this.registers = registers;
        }

        @NonNull
        @Override
        public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);

            return new ListCalcViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListCalcViewHolder holder, int position) {
            SqlHelper.Register register = this.registers.get(position);
            holder.bind(register);
        }

        @Override
        public int getItemCount() {
            return registers.size();
        }
    }

    private static class ListCalcViewHolder extends RecyclerView.ViewHolder {

        ListCalcViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(SqlHelper.Register register) {
            ((TextView) itemView).setText(String.format(new Locale("pt", "BR"),
                    "resultado: %.2f, data: %s", register.response, register.createdDate));
        }
    }
}
