package calificacion.unsm.edu.pe.calificacion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.HttpDispachet;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import adapter.GridEscuelasAdapter;
import bd.EscuelasDataSource;
import bd.JuradoDataSource;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import model.Escuela;


public class EscuelasActivity extends ActionBarActivity {
    private static final int RESULT_CONFIG = 1;
    public static final int DIALOG_SEPARATION_WARNING = 0;
    private static int DEFAULT = 0;
    private ArrayList<Escuela> escuelas;
    private EscuelasDataSource datasource;
    private JuradoDataSource juradodatasource;
    private GridEscuelasAdapter adapter;
    private String fullnameJurado;

    @InjectView(R.id.id_grid_escuela) GridView gridView;
    @InjectView(R.id.jurado) TextView textJurado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuelas);
        ButterKnife.inject(this);

        getSupportActionBar().setSubtitle(getResources().getString(R.string.app_subtitle));

        if (DatosEnabled(this) || wifiEnabled(this))
        {
            if(getIdJurado() == 0){
                showDialog(DIALOG_SEPARATION_WARNING);
            }else{
                textJurado.setText("Jurado "+getFullnameJurado());
            }
        }else{
            createDialog("Usted necesita estar conectado a Internet para usar la Aplicación");
        }



    }

    @OnItemClick(R.id.id_grid_escuela)
    void onItemClick(int position) {
        Intent intent = new Intent(this, CalificacionActivity.class);
        intent.putExtra("escuela_id", escuelas.get(position).getId());
        intent.putExtra("escuela", escuelas.get(position).getNombre());
        startActivity(intent);
    }

    private void redirectConfig() {
        Intent intent = new Intent(EscuelasActivity.this,
                ConfigPreference.class);
        startActivityForResult(intent, RESULT_CONFIG);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textJurado.setText(fullnameJurado);

    }

    public void createGridView() {
        //open database
        datasource = new EscuelasDataSource(this);
        try {
            datasource.open();
            escuelas = datasource.getAllEscuelas();
            //TODO: borrar log
            Log.i("basedatos", escuelas.get(1).getNombre());

            adapter = new GridEscuelasAdapter(this, escuelas);
            gridView.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        createGridView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CONFIG:
                createJurado();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void createJurado() {
        SharedPreferences mSharePreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String fullname = mSharePreferences.getString("preJurado", "NULL");
        String profesion = mSharePreferences.getString("preProfesionJurado", "NULL");

        if (fullname != "NULL" && profesion != "NULL") {
            RequestParams params = new RequestParams();
            params.put("fullname", fullname);
            params.put("profesion", profesion);
            Log.i("url", Config.SERVER + "jurados/nuevo");

            HttpDispachet.post(Config.SERVER + "jurados/nuevo", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    try {
                        String value = new String(bytes, "UTF-8");
                        JSONObject mObject = new JSONObject(value);
                        JSONArray mArray = mObject.getJSONArray("datos");
                        if (mObject.getString("Result").equals("OK")) {
                            showJurado(mArray.getJSONObject(0).getInt("id"),
                                    mArray.getJSONObject(0).getString("profesion"),
                                    mArray.getJSONObject(0).getString("fullname"));

                            fullnameJurado = String.format("Jurado %s", mArray.getJSONObject(0).getString("fullname"));
                            textJurado.setText(fullnameJurado);
                            /*//insert database
                            juradodatasource = new JuradoDataSource(EscuelasActivity.this);
                            juradodatasource.createJurado(mArray.getJSONObject(0).getString("fullname"),
                                    mArray.getJSONObject(0).getInt("id"),mArray.getJSONObject(0).getString("profesion"));*/
                            Toast.makeText(EscuelasActivity.this, "Jurado Ingresado correctamente", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(EscuelasActivity.this, "Ha surgido un error, ingrese nuevamente", Toast.LENGTH_SHORT).show();
                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(EscuelasActivity.this, "Ha surgido un error en el servidor o verifique si tiene plan de datos", Toast.LENGTH_SHORT).show();
                    showDialog(DIALOG_SEPARATION_WARNING);
                }
            });
        }else{
            showDialog(DIALOG_SEPARATION_WARNING);
        }


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

    public int getIdJurado() {
        SharedPreferences pref = getSharedPreferences(
                "CalificationPreferences", MODE_PRIVATE);
        return pref.getInt("id", 0);
    }


    public String getFullnameJurado() {
        SharedPreferences pref = getSharedPreferences(
                "CalificationPreferences", MODE_PRIVATE);
        return pref.getString("fullname", null);
    }

    //<editor-fold desc="DIALOG - CREATE JURADO">
    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        AlertDialog.Builder builder;
        switch (id) {
            case DIALOG_SEPARATION_WARNING:
                // set up the users ability to disable this reminder
                View disableView = getLayoutInflater().inflate(
                        R.layout.separation_reminder_disable, null);

                builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.notice))
                        .setMessage(getString(R.string.separation_warning))
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(disableView)
                        .setCancelable(false)
                        .setNegativeButton(R.string.cancelar,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        EscuelasActivity.this.finish();
                                    }
                                })
                        .setPositiveButton(getString(R.string.cont),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.dismiss();
                                        //TODO: make codigo
                                        redirectConfig();
                                    }
                                });

                dialog = builder.create();

                break;
            default:
                return super.onCreateDialog(id);
        }
        return dialog;
    }
    //</editor-fold>

    private boolean wifiEnabled(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isConnected();
    }

    private boolean DatosEnabled(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infodatos = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return infodatos != null && infodatos.isConnected();
    }


    private void createDialog(String menssage)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(menssage);
        builder1.setCancelable(true);
        builder1.setNeutralButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EscuelasActivity.this.finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
