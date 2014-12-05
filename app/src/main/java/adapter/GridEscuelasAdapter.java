package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import calificacion.unsm.edu.pe.calificacion.R;
import model.ItemGrid;

/**
 * Created by BRAPASTOR on 05/12/2014.
 */
public class GridEscuelasAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<ItemGrid> arrayitems;

    public GridEscuelasAdapter(Context mContext, ArrayList<ItemGrid> arrayitems) {
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

    static class ViewHolder{
        ImageView imagen;
        TextView titulo;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View v = convertView;
        viewHolder = new ViewHolder();
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.grid_item, null);
            viewHolder.titulo = (TextView) v.findViewById(R.id.id_txt_content);
            viewHolder.imagen = (ImageView) v.findViewById(R.id.id_img_content);
            v.setTag(viewHolder);

        }
        ItemGrid grid = arrayitems.get(position);

        viewHolder = (ViewHolder) v.getTag();
        viewHolder.titulo.setText(grid.getTitulo());
        viewHolder.imagen.setImageResource(grid.getImagen());

        return v;
    }
}
