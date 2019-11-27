package com.ort.profesionalinvoicemanager.model.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ort.profesionalinvoicemanager.model.client.Client;

import org.w3c.dom.ls.LSException;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractDao {

    public static final DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");

    private ArrayList<PersistentObject> objectToManipulate;

    protected void clearObjects() {
        objectToManipulate = new ArrayList<>();
    }

    protected void addObjectToManipulate(PersistentObject object) {
        if (objectToManipulate == null) {
            objectToManipulate = new ArrayList<>();
        }
        objectToManipulate.add(object);
    }

    protected void insert() throws Exception {
        SQLiteDatabase sqLiteDatabase = ApplicationContext.getInstance().getDb().getWritableDatabase();
        sqLiteDatabase.beginTransaction();//abre transaccion
        try {
            validateSaveOrUpdateList(false);
            for (PersistentObject obj : objectToManipulate) {
                sqLiteDatabase.insert(
                        obj.getTableName(),
                        null,
                        obj.toContentValues());
            }
            sqLiteDatabase.setTransactionSuccessful();//marca como que es posible hacer el comit
        } catch (Exception e) {
            throw e;
        } finally {
            sqLiteDatabase.endTransaction();//termina transaccion, si se ejecuto setTransactionSuccessful hace comit sino rollback
            objectToManipulate.clear();
        }
    }

    protected void delete() throws Exception {
        SQLiteDatabase sqLiteDatabase = ApplicationContext.getInstance().getDb().getWritableDatabase();
        sqLiteDatabase.beginTransaction();//abre transaccion
        try {
            validateSaveOrUpdateList(false);
            for (PersistentObject obj : objectToManipulate) {
                String whereClause = obj.KEY_OID + " = ?";
                String[] whereArgs = {obj.getOid()};
                if (obj.physicalDelete()) {
                    sqLiteDatabase.delete(
                            obj.getTableName(),
                            whereClause,
                            whereArgs);
                } else {
                    ContentValues values = new ContentValues();
                    values.put(PersistentObjectWithLogicalDeletion.KEY_ACTIVE, new Integer(0));
                    sqLiteDatabase.update(
                            obj.getTableName(),
                            values,
                            whereClause,
                            whereArgs);
                }
            }
            sqLiteDatabase.setTransactionSuccessful();//marca como que es posible hacer el comit
        } catch (Exception e) {
            throw e;
        } finally {
            sqLiteDatabase.endTransaction();//termina transaccion, si se ejecuto setTransactionSuccessful hace comit sino rollback
            objectToManipulate.clear();
        }
    }

    protected void update() throws Exception {
        SQLiteDatabase sqLiteDatabase = ApplicationContext.getInstance().getDb().getWritableDatabase();
        sqLiteDatabase.beginTransaction();//abre transaccion
        try {
            validateSaveOrUpdateList(true);
            PersistentObject obj = objectToManipulate.get(0);
            String whereClause = obj.KEY_OID + " = ?";
            String[] whereArgs = {obj.getOid()};
            obj.updateObject();
            sqLiteDatabase.update(
                    obj.getTableName(),
                    obj.toContentValues(),
                    whereClause,
                    whereArgs);
            sqLiteDatabase.setTransactionSuccessful();//marca como que es posible hacer el comit
        } catch (Exception e) {
            throw e;
        } finally {
            sqLiteDatabase.endTransaction();//termina transaccion, si se ejecuto setTransactionSuccessful hace comit sino rollback
            objectToManipulate.clear();
        }
    }

    private void validateSaveOrUpdateList(boolean update) throws Exception {
        if (objectToManipulate == null) {
            throw new Exception("No hay nada que actualizar, lista en null");
        } else {
            if (objectToManipulate.isEmpty()) {
                throw new Exception("No hay nada que actualizar, lista vacia");
            } else {
                if (update && objectToManipulate.size() != 1) {
                    throw new Exception("Error al Actualizar, mas de un objeto en la lista");
                }
            }
        }
    }

    public Cursor executeQuery(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
       /* String table: Nombre de la tabla a consultar
        String[] columns: Lista de nombres de las columnas que se van a consultar. Si deseas obtener todas las columnas usas null.
                String selection: Es el cuerpo de la sentencia WHERE con las columnas a condicionar. Es posible usar el placeholder '?' para generalizar la condición.
        String[] selectionArgs: Es una lista de los valores que se usaran para reemplazar las incógnitas de selection en el WHERE.
        String groupBy: Aquí puedes establecer cómo se vería la cláusula GROUP BY, si es que la necesitas.
        String having: Establece la sentencia HAVING para condicionar a groupBy.
        String orderBy: Reordena las filas de la consulta a través de ORDER BY.*/
        SQLiteDatabase db = ApplicationContext.getInstance().getDb().getReadableDatabase();
        Cursor c = db.query(
                table,  // Nombre de la tabla
                columns,  // Lista de Columnas a consultar
                selection,  // Columnas para la cláusula WHERE
                selectionArgs,  // Valores a comparar con las columnas del WHERE
                groupBy,  // Agrupar con GROUP BY
                having,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }

    public ArrayList getAll() {
        Cursor c = executeSqlQuery("Select * from " + getTableNameForModel(), null);
        if (c.getCount() == 0) {
            return new ArrayList<>();
        }
        ArrayList result = new ArrayList<>();
        while (c.moveToNext()) {
            result.add(mapBasicData(mapFromCursor(c), c));
        }
        return result;
    }

    public ArrayList getAllWithActiveCondition(boolean showActive) {
        int value;
        Cursor c;
        if (showActive) {
            c = executeSqlQuery("Select * from " + getTableNameForModel() + " where ACTIVE = 1", null);
            if (c.getCount() == 0) {
                return new ArrayList<>();
            }
            ArrayList result = new ArrayList<>();
            while (c.moveToNext()) {
                result.add(mapBasicData(mapFromCursor(c), c));
            }
            return result;
        } else {
            return getAll();
        }


    }

    public <T extends PersistentObject> T  getByOid(String oid) {
        Cursor c = executeSqlQuery("Select * from " + getTableNameForModel()+" where OID = ?", new String[]{oid});
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToNext();
        return mapBasicData(mapFromCursor(c), c);
    }

    public Cursor executeSqlQuery(String query, String[] selectionArgs) {
        SQLiteDatabase db = ApplicationContext.getInstance().getDb().getReadableDatabase();
        Cursor c = db.rawQuery(query, selectionArgs);
        return c;
    }

    protected abstract String getTableNameForModel();

    protected abstract <T extends PersistentObject> T mapFromCursor(Cursor c);

    protected <T extends PersistentObject> T mapBasicData(PersistentObject p, Cursor c) {
        p.setOid(c.getString(c.getColumnIndex(p.KEY_OID)));
        try {
            p.setCreationTimestamp(iso8601Format.parse(c.getString(c.getColumnIndex(p.KEY_CREATION_TIMESTAMP))));
            p.setModificationTimestamp(iso8601Format.parse(c.getString(c.getColumnIndex(p.KEY_MODIFICATION_TIMESTAMP))));
            if (!p.physicalDelete()){
                ((PersistentObjectWithLogicalDeletion)p).active=c.getInt(c.getColumnIndex(((PersistentObjectWithLogicalDeletion) p).KEY_ACTIVE));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (T) p;
    }

}
