package edu.utsa.cs3443.weighttracker.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import edu.utsa.cs3443.weighttracker.R;
import edu.utsa.cs3443.weighttracker.data.WeightEntry;

public class WeightAdapter
        extends ListAdapter<WeightEntry, WeightAdapter.Holder> {

    public interface OnOptionsClick {
        void onOptions(WeightEntry entry, View anchor);
    }

    private final OnOptionsClick listener;

    //Lets submitList() only redraw things that actually changed instead of refreshing the whole list
    public WeightAdapter(OnOptionsClick listener) {
        super(new DiffUtil.ItemCallback<WeightEntry>() {
            @Override
            public boolean areItemsTheSame(@NonNull WeightEntry a, @NonNull WeightEntry b) {
                return a.getDate().equals(b.getDate());
            }
            @Override
            public boolean areContentsTheSame(@NonNull WeightEntry a, @NonNull WeightEntry b) {
                return a.getDate().equals(b.getDate()) && a.getWeight() == b.getWeight();
            }
        });
        this.listener = listener;
    }


    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weight_entry, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {
        WeightEntry entry = getItem(position);
        h.tvEntry.setText(entry.formattedDate() + " – " + entry.getWeight() + " lbs");
        h.ivOptions.setOnClickListener(v -> listener.onOptions(entry, v));
    }

    static class Holder extends RecyclerView.ViewHolder {
        final TextView tvEntry;
        final ImageButton ivOptions;
        Holder(@NonNull View itemView) {
            super(itemView);
            tvEntry   = itemView.findViewById(R.id.tvEntry);
            ivOptions = itemView.findViewById(R.id.ivOptions);
        }
    }
}
