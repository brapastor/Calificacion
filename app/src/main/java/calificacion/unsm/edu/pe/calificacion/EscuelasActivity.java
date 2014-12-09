package calificacion.unsm.edu.pe.calificacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.HttpDispachet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import adapter.GridEscuelasAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import model.Escuelas;
import model.ItemGrid;


public class EscuelasActivity extends ActionBarActivity {
    private static final int RESULT_CONFIG = 1;
    private ArrayList<ItemGrid> items;
    private ArrayList<Escuelas> listEscuelas;

    @InjectView(R.id.id_grid_escuela) GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //<editor-fold desc="INIT ESCUELAS">
        listEscuelas = new ArrayList<Escuelas>();
        listEscuelas.add(new Escuelas(R.drawable.fisi, "Sistemas e Informática", 1));
        listEscuelas.add(new Escuelas(R.drawable.fca, "Agronomía", 2));
        listEscuelas.add(new Escuelas(R.drawable.enfermeria, "Enfermería", 3));
        listEscuelas.add(new Escuelas(R.drawable.obst, "Obstetricia", 4));
        listEscuelas.add(new Escuelas(R.drawable.agroindustria, "Agroindustria", 5));
        listEscuelas.add(new Escuelas(R.drawable.civil, "Ingenieria Civil", 6));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Arquitectura", 7));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Ingenieria Ambiental", 8));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Derecho y Ciencias Politicas", 9));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Medicina Humana", 10));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Administración", 10));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Medicina Humana", 10));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Turismo", 10));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Economía", 10));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Contabilidad", 10));
        listEscuelas.add(new Escuelas(R.drawable.unsm_escudo, "Idiomas", 10));
        //</editor-fold>
        setContentView(R.layout.activity_escuelas);
        ButterKnife.inject(this);
        getSupportActionBar().setSubtitle(getResources().getString(R.string.app_subtitle));

        //<editor-fold desc="CREATE GRID">
        items = new ArrayList<ItemGrid>();
        for (Escuelas escuela : listEscuelas) {
            items.add(new ItemGrid(escuela.getNombre(), escuela.getIcono()));
        }

        GridEscuelasAdapter adapter = new GridEscuelasAdapter(this, items);
        gridView.setAdapter(adapter);
        //</editor-fold>
    }

    @OnItemClick(R.id.id_grid_escuela)
    void onItemClick(int position) {
        Toast.makeText(this, "Clicked position " + position , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,CalificacionActivity.class);
        startActivity(intent);
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
            Intent intent = new Intent(EscuelasActivity.this,
                    ConfigPreference.class);
            startActivityForResult(intent, RESULT_CONFIG);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CONFIG:
                showSettings();
                createJurado();
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

    private void createJurado() {
        SharedPreferences mSharePreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String fullname = mSharePreferences.getString("preJurado", "NULL");
        String profesion = mSharePreferences.getString("preProfesionJurado", "NULL");

        RequestParams params = new RequestParams();
        params.put("fullname", fullname);
        params.put("profesion", profesion);
        Log.i("url", getUrl() + "jurados/nuevo");

        HttpDispachet.post(getUrl() + "jurados/nuevo", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String value = new String(bytes, "UTF-8");
                    JSONObject mObject = new JSONObject(value);
                    JSONArray mArray = mObject.getJSONArray("datos");
                    Log.e("OK", mObject.getString("Result"));
                    if (mObject.getString("Result").equals("OK")) {
                        showJurado(mArray.getJSONObject(0).getInt("id"),
                                mArray.getJSONObject(0).getString("profesion"),
                                mArray.getJSONObject(0).getString("fullname"));
                    } else {
                        Log.e("error", "Error");
                    }
                    //TODO: borrar
                    Log.i("response", mArray.toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("status", i + "");
            }
        });

    }

    public void showJurado(int id, String fullname, String profesion) {
        SharedPreferences mSharePreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        mSharePreferences = getSharedPreferences("CalificationPreferences",
                MODE_PRIVATE);

        // guardamos las preferencias en un xml
        SharedPreferences.Editor editor = mSharePreferences.edit();
        editor.putString("fullname", fullname);
        editor.putString("profesion", profesion);
        editor.putInt("id", id);
        editor.commit();
        //TODO
        Log.i("idJurado", getIdJurado() + "");
    }

    public String getUrl() {
        SharedPreferences pref = getSharedPreferences(
                "CalificationPreferences", MODE_PRIVATE);
        return pref.getString("url", "nada");
    }

    public int getIdJurado() {
        SharedPreferences pref = getSharedPreferences(
                "CalificationPreferences", MODE_PRIVATE);
        return pref.getInt("id", 0);
    }
}
