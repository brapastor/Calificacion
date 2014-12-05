package calificacion.unsm.edu.pe.calificacion;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

import adapter.GridEscuelasAdapter;
import model.ItemGrid;


public class CalificationActivity extends Activity {
    private GridView gridView;
    private ArrayList<ItemGrid> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calification);

        gridView = (GridView) findViewById(R.id.id_grid_escuela);

        items = new ArrayList<ItemGrid>();

        items.add(new ItemGrid("sabe", R.drawable.ic_launcher));
        items.add(new ItemGrid("sabe", R.drawable.ic_launcher));
        GridEscuelasAdapter adapter = new GridEscuelasAdapter(this, items);
        gridView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
