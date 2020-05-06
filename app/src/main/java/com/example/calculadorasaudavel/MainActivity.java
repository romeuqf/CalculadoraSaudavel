package com.example.calculadorasaudavel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        mainItems.add(new MainItem(R.drawable.ic_announcement_black_24dp, R.string.imc, 0xFFFF00FF));
        mainItems.add(new MainItem(R.drawable.ic_announcement_black_24dp, R.string.imc, 0xFF0000FF));
        mainItems.add(new MainItem(R.drawable.ic_announcement_black_24dp, R.string.imc, 0xFFFF0000));
        mainItems.add(new MainItem(R.drawable.ic_announcement_black_24dp, R.string.imc, 0xFF110022));
        mainItems.add(new MainItem(R.drawable.ic_announcement_black_24dp, R.string.imc, 0xFF550043));
        mainItems.add(new MainItem(R.drawable.ic_announcement_black_24dp, R.string.imc, 0xFF6600FF));

        MainAdapter adapter = new MainAdapter(mainItems);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

        private final List<MainItem> mainItems;

        MainAdapter(List<MainItem> mainItems) {
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
            MainItem MainItem = this.mainItems.get(position);
            holder.bind(MainItem);

        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }
    }


    private static class MainViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgViewMain;
        private final TextView textViewMain;


        MainViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewMain = itemView.findViewById(R.id.item_main_img);
            textViewMain = itemView.findViewById(R.id.item_main_text);
        }

         void bind(MainItem mainItem) {
            itemView.setBackgroundColor(mainItem.getColorValue());
            imgViewMain.setImageResource(mainItem.getImgId());
            textViewMain.setText(mainItem.getTextId());
        }
    }
}
