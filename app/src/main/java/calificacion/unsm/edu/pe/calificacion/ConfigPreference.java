package calificacion.unsm.edu.pe.calificacion;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by BRAPASTOR on 05/12/2014.
 */
public class ConfigPreference extends PreferenceActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config);
    }


}



