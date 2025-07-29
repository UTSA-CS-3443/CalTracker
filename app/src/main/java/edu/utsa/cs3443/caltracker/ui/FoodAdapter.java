package edu.utsa.cs3443.caltracker.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.utsa.cs3443.caltracker.R;
import edu.utsa.cs3443.caltracker.data.FoodRepository;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>  {
    private final Context context;
    private final FoodRepository foodRepo;
    private final  OnFoodClickListener clickListener;

    public FoodAdapter(Context context, FoodRepository foodRepo, OnFoodClickListener listener) {
        this.context = context;
        this.foodRepo = foodRepo;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate layout and give alook to cards
        Log.d("FoodAdapter", "onCreateViewHolder called");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.recycler_view_card, parent, false);
        return new FoodAdapter.MyViewHolder(v, clickListener  );
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.MyViewHolder holder, int position) {
        // assign values to cards as they come back on screen  based on position
        Log.d("FoodAdapter", "onBindViewHolder called for position: " + position);
        holder.card_text.setText(foodRepo.getAllFoods().get(position).getName());
        holder.imageView4.setImageResource(R.drawable.ic_launcher_foreground);


    }

    @Override
    public int getItemCount() {

        int count = foodRepo.getAllFoods().size();
      //  Log.d("FoodAdapter", "getItemCount: " + count);
        return count;

        // for testing
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from our recyclerview similar to ONcreate
        ImageView imageView4;
        TextView card_text;

        public MyViewHolder(@NonNull View itemView , OnFoodClickListener listener) {
            super(itemView);
            itemView.setOnClickListener( v -> {

                    int position = getAdapterPosition();
                    if(position !=  RecyclerView.NO_POSITION){
                        listener.onFoodClick(position);

                }
            });


            imageView4 = itemView.findViewById(R.id.imageView4);
            card_text = itemView.findViewById(R.id.card_text);


        }
    }

}

