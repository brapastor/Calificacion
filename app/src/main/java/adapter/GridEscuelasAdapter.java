package adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import calificacion.unsm.edu.pe.calificacion.Config;
import calificacion.unsm.edu.pe.calificacion.R;
import model.Escuela;

/**
 * Created by BRAPASTOR on 05/12/2014.
 */
public class GridEscuelasAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Escuela> arrayitems;

    public GridEscuelasAdapter(Context mContext, ArrayList<Escuela> arrayitems) {
        this.mContext = mContext;
        this.arrayitems = arrayitems;
    }

    @Override
    public int getCount() {
        return arrayitems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        @InjectView(R.id.id_img_content)
        ImageView imagen;
        @InjectView(R.id.id_txt_content)
        TextView titulo;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View v = convertView;
        int estado = 0;
        Escuela grid = arrayitems.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);

            estado = grid.getEstado();
            Log.i("estado",position+" - "+estado+"-id-"+grid.getId());

        }else{
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.titulo.setText(grid.getNombre());
        viewHolder.imagen.setScaleType(ImageView.ScaleType.FIT_CENTER);
        viewHolder.imagen.setPadding(8, 8, 8, 8);

        viewHolder.imagen.setImageResource(grid.getIcono());
        return v;
    }
}
