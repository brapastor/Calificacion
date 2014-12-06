package calificacion.unsm.edu.pe.calificacion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.GridEscuelasAdapter;
import model.ItemGrid;


public class CalificationActivity extends Activity {
    private GridView gridView;
    private static final int RESULT_CONFIG=1;
    private ArrayList<ItemGrid> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calification);

        gridView = (GridView) findViewById(R.id.id_grid_escuela);

        //<editor-fold desc="CREATE GRID">
        items = new ArrayList<ItemGrid>();
        items.add(new ItemGrid("Sistemas e Informática", R.drawable.fisi));
      //items.add(new ItemGrid("Ciencias Economicas", R.drawable.fce));
        items.add(new ItemGrid("Agronomía", R.drawable.fca));
        items.add(new ItemGrid("Veterinaria", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Enfermeria", R.drawable.enfermeria));
        items.add(new ItemGrid("Obstetricia", R.drawable.obst));
        items.add(new ItemGrid("Agroindustria", R.drawable.agroindustria));
        items.add(new ItemGrid("Ingenieria Civil", R.drawable.civil));
        items.add(new ItemGrid("Arquitectura", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Ingenieria Ambiental", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Derecho y Ciencias Politicas", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Medicina Humana", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Administración", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Turismo", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Economía", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Contabilidad", R.drawable.unsm_escudo));
        items.add(new ItemGrid("Idiomas", R.drawable.unsm_escudo));
        GridEscuelasAdapter adapter = new GridEscuelasAdapter(this, items);
        gridView.setAdapter(adapter);
        //</editor-fold>

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position+"", Toast.LENGTH_SHORT).show();
                Intent intent=null;
                switch (position){

                    case 0 :
                        intent = new Intent(CalificationActivity.this,CriterionOne.class);
                        break;

                }
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(CalificationActivity.this,
                    ConfigPreference.class);
            startActivityForResult(intent, RESULT_CONFIG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case RESULT_CONFIG:
                showSettings();
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void showSettings() {
        SharedPreferences mSharePreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String url = mSharePreferences.getString("prefUrl", "NULL");

        mSharePreferences = getSharedPreferences("CalificationPreferences",
                MODE_PRIVATE);

        // guardamos las preferencias en un xml
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putString("url", url);
        editor.commit();
    }
}
