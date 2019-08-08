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

/**
 * Adapter pre RecyclerView. Vlastny adapter podla vytvorenych layoutov.
 * Toto je nezavisle od sposobu ulozenia dat a nema priamy suvis s databazou ale s RecyclerView
 */
public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>{

    /**
     * Kompresor na vytvorenie layoutu
     */
    private LayoutInflater inflater;
    /**
     * Data v liste - nie live data
     */
    private List<Company> data;

    public CompanyListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    // view sa vytvori z vlastneho definovaneho layoutu pre jednu polozku pomocou kompresora
    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        // v tejto aplikacii sa data nacitavaju asynchronne

        if (data == null) {
            // ak este data nie su k dispozicii
            holder.companyItemView.setText("No Company");
        } else {
            // ak su data uz nacitane
            Company company = data.get(position);
            holder.companyItemView.setText(company.getName());

        }
    }

    // aktivita je observer=pozorovatel zivych dat vo viewModeli. V pripade ze sa data zmenia,
    // tak sa aktualizuje aj zoznam v adapteri cez tuto metodu.
    // Sluzi to pre recyclerView a upozorni sa samotny recyclerView.
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

    // pomocna trieda na view holder. Jedna polozka v recyclerView pozostava iba z jedneho textView
    class CompanyViewHolder extends RecyclerView.ViewHolder {
        private final TextView companyItemView;

        private CompanyViewHolder(View itemView) {
            super(itemView);
            companyItemView = itemView.findViewById(R.id.textView);
        }
    }
}
