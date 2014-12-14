package bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import calificacion.unsm.edu.pe.calificacion.R;

/**
 * Created by eveR Vásquez on 13/12/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_ESCUELAS = "escuelas";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ESCUELA = "escuela";
    public static final String COLUMN_ESTADO = "estado";
    public static final String COLUMN_ICONO = "icono";


    public static final String TABLE_JURADOS = "jurados";
    public static final String COLUMN_ID_JURADO = "_id";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_PROFESION = "profesion";

    private static final String DATABASE_NAME = "concurso.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ESCUELAS + "(" + COLUMN_ID
            + " integer primary key, "
            + COLUMN_ICONO + " integer not null, "
            + COLUMN_ESTADO + " integer not null, "
            + COLUMN_ESCUELA + " text not null);";

    private static final String DATABASE_CREATE_JURADO = "create table "
            + TABLE_JURADOS + "(" + COLUMN_ID_JURADO
            + " integer primary key, "
            + COLUMN_FULLNAME + " text not null, "
            + COLUMN_PROFESION + " text not null);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE_JURADO);

        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (1,'Sistemas e Informática',"+R.drawable.fisi+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (2,'Agronomía',"+R.drawable.fca+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (3,'Enfermería',"+R.drawable.enfermeria+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (4,'Veterinaria',"+R.drawable.veterinaria+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (5,'Agroindustrial',"+R.drawable.agroindustria+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (6,'Ingenieria Civil',"+R.drawable.civil+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (7,'Arquitectura',"+R.drawable.unsm_escudo+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (8,'Obstetricia',"+R.drawable.obst+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (9,'Medicina Humana',"+R.drawable.medicina+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (10,'Ingenieria Ambiental',"+R.drawable.ambiental+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (11,'Ingenieria Sanitaria',"+R.drawable.unsm_escudo+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (12,'Contabilidad',"+R.drawable.unsm_escudo+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (13,'Turismo',"+R.drawable.turismo+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (14,'Administración',"+R.drawable.unsm_escudo+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (15,'Economía',"+R.drawable.economia+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (16,'Idiomas',"+R.drawable.unsm_escudo+",0) ");
        database.execSQL("INSERT INTO "+TABLE_ESCUELAS+" ("+COLUMN_ID+","+COLUMN_ESCUELA+","+COLUMN_ICONO+","+COLUMN_ESTADO+") VALUES (17,'Derecho',"+R.drawable.unsm_escudo+",0) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESCUELAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JURADOS);
        onCreate(db);
    }

}
