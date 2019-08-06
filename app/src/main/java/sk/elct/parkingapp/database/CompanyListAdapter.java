package sk.elct.parkingapp.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sk.elct.parkingapp.R;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>{

    private LayoutInflater inflater;
    private List<Company> data;

    public CompanyListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        if (data == null) {
            holder.companyItemView.setText("No Company");
        } else {
            Company company = data.get(position);
            holder.companyItemView.setText(company.getName());

        }
    }

    public void setData(List<Company> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }

    }

    class CompanyViewHolder extends RecyclerView.ViewHolder {
        private final TextView companyItemView;

        private CompanyViewHolder(View itemView) {
            super(itemView);
            companyItemView = itemView.findViewById(R.id.textView);
        }
    }
}
