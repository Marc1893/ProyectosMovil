package jorgemedina.guardanotas_v5.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlmgm on 02/12/2017.
 */

public class DaoNotifica {

    private Context _contexto;
    private SQLiteDatabase _midb;


    //CREACION DE LA DB;
    public DaoNotifica(Context contexto){

        this._contexto = contexto;
        this._midb = new DBOpenHelper(contexto).getWritableDatabase();

    }

    //INSERTAR DATOS;
    public long insert(POJO_Alerta_Serial c){

        ContentValues cv = new ContentValues();

        cv.put(DBOpenHelper.COLUMNS_ALERTAS[1],c.getId_tarea());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[2],c.getTituloAlerta());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[3],c.getDescripcionAlerta());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[4],c.getFechaAlerta());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[5],c.getHoraAlerta());

        return _midb.insert(DBOpenHelper.TABLE_ALERTAS_NAME,null,cv) ;

    }

    //ACTUALIZAR DATOS;
    public long update(POJO_Alerta_Serial c){

        ContentValues cv = new ContentValues();

        cv.put(DBOpenHelper.COLUMNS_ALERTAS[1],c.getId_tarea());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[2],c.getTituloAlerta());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[3],c.getDescripcionAlerta());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[4],c.getFechaAlerta());
        cv.put(DBOpenHelper.COLUMNS_ALERTAS[5],c.getHoraAlerta());

        return _midb.update(DBOpenHelper.TABLE_ALERTAS_NAME, cv, "_id=?", new String[] { String.valueOf( c.getId_alerta())});

    }

    //ELIMINAR DATOS (ID);
    public int delete(String id){

        return  _midb.delete("alertas","_id='"+id+"'",null);

    }

    //ELIMINAR DATOS (ID TAREA);
    public int deleteTODAS(String id){

        return  _midb.delete("alertas","id_Tarea='"+id+"'",null);

    }

    //CREACION DE LISTA;
    public List<POJO_Alerta_Serial> buscarTodos() {

        List<POJO_Alerta_Serial> notesArrayList = new ArrayList<POJO_Alerta_Serial>();
        String selectQuery = "SELECT * FROM alertas";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                POJO_Alerta_Serial notas = new POJO_Alerta_Serial();
                notas.setId_alerta(c.getInt(c.getColumnIndex("_id")));
                notas.setId_tarea(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setTituloAlerta(c.getString(c.getColumnIndex("titulo")));
                notas.setDescripcionAlerta(c.getString(c.getColumnIndex("descripcion")));
                notas.setFechaAlerta(c.getString(c.getColumnIndex("fechaAlerta")));
                notas.setHoraAlerta(c.getString(c.getColumnIndex("horaAlerta")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }

    //BUSQUEDA POR ID TAREA;
    public List<POJO_Alerta_Serial> buscarTodosDeTarea(int iden) {

        List<POJO_Alerta_Serial> notesArrayList = new ArrayList<POJO_Alerta_Serial>();
        String selectQuery = "SELECT * FROM alertas WHERE id_Tarea = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                POJO_Alerta_Serial notas = new POJO_Alerta_Serial();
                notas.setId_alerta(c.getInt(c.getColumnIndex("_id")));
                notas.setId_tarea(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setTituloAlerta(c.getString(c.getColumnIndex("titulo")));
                notas.setDescripcionAlerta(c.getString(c.getColumnIndex("descripcion")));
                notas.setFechaAlerta(c.getString(c.getColumnIndex("fechaAlerta")));
                notas.setHoraAlerta(c.getString(c.getColumnIndex("horaAlerta")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }

    //BUSQUEDA POR FECHA;
    public List<POJO_Alerta_Serial> buscarTodosDeFecha(String iden) {

        List<POJO_Alerta_Serial> notesArrayList = new ArrayList<POJO_Alerta_Serial>();
        String selectQuery = "SELECT * FROM alertas WHERE fechaAlerta = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                POJO_Alerta_Serial notas = new POJO_Alerta_Serial();
                notas.setId_alerta(c.getInt(c.getColumnIndex("_id")));
                notas.setId_tarea(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setTituloAlerta(c.getString(c.getColumnIndex("titulo")));
                notas.setDescripcionAlerta(c.getString(c.getColumnIndex("descripcion")));
                notas.setFechaAlerta(c.getString(c.getColumnIndex("fechaAlerta")));
                notas.setHoraAlerta(c.getString(c.getColumnIndex("horaAlerta")));

                notesArrayList.add(notas);

            } while (c.moveToNext());

        }

        return notesArrayList;

    }

    //BUSQUEDA POR ID;
    public POJO_Alerta_Serial buscarUno(int iden) {

        POJO_Alerta_Serial notesUno = new POJO_Alerta_Serial();
        String selectQuery = "SELECT * FROM alertas WHERE _id = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this._midb;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                POJO_Alerta_Serial notas = new POJO_Alerta_Serial();
                notas.setId_alerta(c.getInt(c.getColumnIndex("_id")));
                notas.setId_tarea(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setTituloAlerta(c.getString(c.getColumnIndex("titulo")));
                notas.setDescripcionAlerta(c.getString(c.getColumnIndex("descripcion")));
                notas.setFechaAlerta(c.getString(c.getColumnIndex("fechaAlerta")));
                notas.setHoraAlerta(c.getString(c.getColumnIndex("horaAlerta")));

                notesUno = notas;

            } while (c.moveToNext());

        }

        return notesUno;

    }











}
