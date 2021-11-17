package br.unicamp.dailytherapy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ResumoAdapter extends BaseAdapter
{
    private List<Remedio> remedioList;
    private Context context;
    private Activity app;
    private Remedio remedio;

    public ResumoAdapter(List<Remedio> remedioList, Context context)
    {
        this.remedioList = remedioList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return remedioList.size();
    }

    @Override
    public Object getItem(int position) {
        return remedioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v == null)
        {
            v = LayoutInflater.from(context).inflate(R.layout.adapter_remedio, parent, false);
        }
        TextView tvNomeRemedio = (TextView) v.findViewById(R.id.tvNomeRemedio);
        TextView tvInicio = (TextView) v.findViewById(R.id.tvInicio);
        TextView tvFim = (TextView) v.findViewById(R.id.tvFim);

        remedio = remedioList.get(position);
        tvNomeRemedio.setText(remedio.getNomeRemedio());
        tvInicio.setText(remedio.getInicio());
        tvFim.setText(remedio.getFim());

        return v;
    }
}

