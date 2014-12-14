package calificacion.unsm.edu.pe.calificacion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.dd.processbutton.iml.GenerateProcessButton;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.HttpDispachet;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import bd.EscuelasDataSource;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import utils.ProgressGenerator;


public class CalificacionActivity extends ActionBarActivity implements ProgressGenerator.OnCompleteListener {

    //<editor-fold desc="INJECTVIEW">
    @InjectView(R.id.editCriterio1) FormEditText editCriterio1;
    @InjectView(R.id.editCriterio2) FormEditText editCriterio2;
    @InjectView(R.id.editCriterio3) FormEditText editCriterio3;
    @InjectView(R.id.editCriterio4) FormEditText editCriterio4;
    @InjectView(R.id.editCriterio5) FormEditText editCriterio5;
    @InjectView(R.id.editCriterio6) FormEditText editCriterio6;
    @InjectView(R.id.btnSend) GenerateProcessButton btnSend;
    //</editor-fold>
    private static final String TAG = "CalificacionActivity";
    private static final String METHOD = "calificaciones/nuevo";
    private int escuela_id;
    private String nombre_escuela;
    private EscuelasDataSource source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificacion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ButterKnife.inject(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        escuela_id = getIntent().getExtras().getInt("escuela_id");
        nombre_escuela = getIntent().getExtras().getString("escuela");
        getSupportActionBar().setSubtitle(nombre_escuela);
        Log.i("id", escuela_id + "");
    }

    @OnClick(R.id.btnSend)
    public void submit(View view) {
        FormEditText[] allFields = {editCriterio1, editCriterio2, editCriterio3, editCriterio4, editCriterio5, editCriterio6};
        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }
        if (allValid) {
            sendInformation();
        } else {
            // EditText are going to appear with an exclamation mark and an explicative message.
        }
    }

    private void sendInformation() {
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        progressGenerator.start(btnSend);
        btnSend.setEnabled(false);
        String url = Config.SERVER + METHOD;
        RequestParams params = new RequestParams();
        params.put("puntaje1", editCriterio1.getText().toString().trim());
        params.put("criterio1", 1);
        params.put("puntaje2", editCriterio2.getText().toString().trim());
        params.put("criterio2", 2);
        params.put("puntaje3", editCriterio3.getText().toString().trim());
        params.put("criterio3", 3);
        params.put("puntaje4", editCriterio4.getText().toString().trim());
        params.put("criterio4", 4);
        params.put("puntaje5", editCriterio5.getText().toString().trim());
        params.put("criterio5", 5);
        params.put("puntaje6", editCriterio6.getText().toString().trim());
        params.put("criterio6", 6);
        params.put("escuela_id",escuela_id);
        params.put("jurado_id",getIdJurado());
        //TODO: borrar
        Log.i("url", url);

        HttpDispachet.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String value = new String(responseBody, "UTF-8");
                        JSONObject mObject = new JSONObject(value);
                        if (mObject.getString("Result").equals("OK")) {
                            source = new EscuelasDataSource(CalificacionActivity.this);
                            source.open();
                            source.updateEscuela(1,escuela_id);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(CalificacionActivity.this, "hubo un error "+statusCode, Toast.LENGTH_LONG).show();
            }
        });
    }

    //<editor-fold desc="GET DATA - SHAREPREFERENCES">
/*
    recupero datos de SharePreferences
     */
    public int getIdJurado() {
        SharedPreferences pref = getSharedPreferences(
                "CalificationPreferences", MODE_PRIVATE);
        return pref.getInt("id", 0);
    }
    //</editor-fold>

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(){
        Toast.makeText(this, "La calificación fue enviada exitosamente", Toast.LENGTH_LONG).show();
        finish();
    }
}
