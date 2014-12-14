package bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Escuela;

/**
 * Created by eveR Vásquez on 13/12/2014.
 */
public class EscuelasDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_ESCUELA, MySQLiteHelper.COLUMN_ICONO,MySQLiteHelper.COLUMN_ESTADO };

    public EscuelasDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Escuela createEscuela(String escuela) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ESCUELA, escuela);
        long insertId = database.insert(MySQLiteHelper.TABLE_ESCUELAS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ESCUELAS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Escuela newEscuela = cursorToComment(cursor);
        cursor.close();
        return newEscuela;
    }

    public void deleteEscuela(Escuela escuela) {
        long id = escuela.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ESCUELAS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public int updateEscuela(int estado, int id)
    {
        //TODO: borrar
        Log.i("SQLite", "UPDATE: id=" + id + " - " + estado + "," + estado);
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ESTADO, estado);
        return database.update( MySQLiteHelper.TABLE_ESCUELAS , values, MySQLiteHelper.COLUMN_ID + " = " + id , null);
    }

    public int updateEscuelasAll(String column, String estado)
    {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ESTADO, estado);
        return database.update( column , values, null , null);
    }

    public ArrayList<Escuela> getAllEscuelas() {
        ArrayList<Escuela> ListEscuelas = new ArrayList<Escuela>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ESCUELAS,
                allColumns, MySQLiteHelper.COLUMN_ESTADO + " = " + 0, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Escuela escuela = cursorToComment(cursor);
            ListEscuelas.add(escuela);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return ListEscuelas;
    }

    private Escuela cursorToComment(Cursor cursor) {
        Escuela escuela = new Escuela();
        escuela.setId(cursor.getInt(0));
        escuela.setNombre(cursor.getString(1));
        escuela.setIcono(cursor.getInt(2));
        escuela.setEstado(cursor.getInt(3));
        return escuela;
    }
}
