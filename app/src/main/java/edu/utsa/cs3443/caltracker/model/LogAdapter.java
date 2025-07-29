package edu.utsa.cs3443.caltracker.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// <-- ADD THIS LINE:
import edu.utsa.cs3443.caltracker.R;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    public interface OnEntryActionListener {
        void onEntryAction(LogEntry entry, View anchor);
    }

    private final List<LogEntry> items = new ArrayList<>();
    private final OnEntryActionListener listener;

    public LogAdapter(OnEntryActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<LogEntry> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_log_entry, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        holder.bind(items.get(pos));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView     tvMain, tvMacros;
        ImageButton  btnOptions;

        ViewHolder(View itemView) {
            super(itemView);
            tvMain     = itemView.findViewById(R.id.tvEntryMain);
            tvMacros   = itemView.findViewById(R.id.tvEntryMacros);
            btnOptions = itemView.findViewById(R.id.btnOptions);
        }

        void bind(LogEntry e) {
            tvMain.setText(e.getDescription() + "  " + e.getCalories() + " Cal");
            tvMacros.setText(
                    "Fat: "     + e.getFat()     + "g  " +
                            "Carbs: "   + e.getCarbs()   + "g  " +
                            "Fiber: "   + e.getFiber()   + "g  " +
                            "Protein: " + e.getProtein()+ "g"
            );
            btnOptions.setOnClickListener(v ->
                    listener.onEntryAction(e, btnOptions)
            );
        }
    }
}

