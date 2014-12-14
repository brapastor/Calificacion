package bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Jurado;

/**
 * Created by eveR Vásquez on 14/12/2014.
 */
public class JuradoDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID_JURADO,
            MySQLiteHelper.COLUMN_FULLNAME, MySQLiteHelper.COLUMN_PROFESION };

    public JuradoDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Jurado createJurado(String jurado, int id, String profesion) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_JURADO, id);
        values.put(MySQLiteHelper.COLUMN_FULLNAME, jurado);
        values.put(MySQLiteHelper.COLUMN_PROFESION, profesion);
        long insertId = database.insert(MySQLiteHelper.TABLE_JURADOS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_JURADOS,
                allColumns, MySQLiteHelper.COLUMN_ID_JURADO + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Jurado newJurado = cursorToComment(cursor);
        cursor.close();
        return newJurado;
    }

    public ArrayList<Jurado> getAllJurados() {
        ArrayList<Jurado> ListJurados = new ArrayList<Jurado>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_JURADOS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Jurado jurado = cursorToComment(cursor);
            ListJurados.add(jurado);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return ListJurados;
    }

    public ArrayList<Jurado> getJurado(int id) {
        ArrayList<Jurado> ListJurados = new ArrayList<Jurado>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_JURADOS,
                null, MySQLiteHelper.COLUMN_ID_JURADO + " = " + id, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Jurado jurado = cursorToComment(cursor);
            ListJurados.add(jurado);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return ListJurados;
    }

    private Jurado cursorToComment(Cursor cursor) {
        Jurado jurado = new Jurado();
        jurado.setId(cursor.getInt(0));
        jurado.setFullname(cursor.getString(1));
        jurado.setProfesion(cursor.getString(2));
        return jurado;
    }
}
