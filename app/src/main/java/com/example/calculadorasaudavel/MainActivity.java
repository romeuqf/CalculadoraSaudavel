package com.example.calculadorasaudavel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<MainItem> mainItems = new ArrayList<>();
        mainItems.add(new MainItem(1, R.drawable.ic_announcement_black_24dp, R.string.imc, 0xFFFF00FF));
        mainItems.add(new MainItem(2, R.drawable.ic_announcement_black_24dp, R.string.tbm, 0xFF0000FF));

        MainAdapter adapter = new MainAdapter(this,mainItems);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    class MainAdapter extends RecyclerView.Adapter<MainViewHolder> implements MainViewHolder.OnItemClickListener {

        private final List<MainItem> mainItems;
        private final MainActivity activity;

        MainAdapter(MainActivity activity, List<MainItem> mainItems) {
            this.activity = activity;
            this.mainItems = mainItems;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_main, viewGroup, false);


            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            MainItem mainItem = this.mainItems.get(position);
            holder.bind(mainItem, this);

        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }

        @Override
        public void onClick(int position) {
            MainItem mainItem = this.mainItems.get(position);
            switch (mainItem.getId()) {

                case 1: {
                    Intent intent = new Intent(activity, ImcActivity.class);
                    activity.startActivity(intent);
                }
                break;

                case 2: {
                    Intent intent = new Intent(activity, TmbActivity.class);
                    activity.startActivity(intent);

                }
                break;
            }
        }
    }


    private static class MainViewHolder extends RecyclerView.ViewHolder {

        interface OnItemClickListener {
            void onClick(int position);
        }


        private final ImageView imgViewMain;
        private final TextView textViewMain;


        MainViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewMain = itemView.findViewById(R.id.item_main_img);
            textViewMain = itemView.findViewById(R.id.item_main_text);
        }

        void bind(MainItem mainItem, final OnItemClickListener listener) {
            itemView.setBackgroundColor(mainItem.getColorValue());
            imgViewMain.setImageResource(mainItem.getImgId());
            textViewMain.setText(mainItem.getTextId());

            itemView.setOnClickListener(v -> listener.onClick(getAdapterPosition()));
        }
    }
}
