package com.ort.profesionalinvoicemanager.model.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;
import com.ort.profesionalinvoicemanager.model.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pim.db";
    private ArrayList<String> persistenClasses;

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        persistenClasses=new ArrayList<>();
        persistenClasses.add(DocumentType.class.getName());
        persistenClasses.add(IvaCategory.class.getName());
        persistenClasses.add(MonotributoCategory.class.getName());
        persistenClasses.add(TaxInformation.class.getName());
        persistenClasses.add(Industry.class.getName());
        persistenClasses.add(User.class.getName());
        persistenClasses.add(Client.class.getName());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase = ApplicationContext.getInstance().getDb().getWritableDatabase();
            for (String classes:persistenClasses) {
                PersistentObject object = (PersistentObject) Class.forName(classes).newInstance();
                sqLiteDatabase.execSQL(object.getCreationScript());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createinitialData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createinitialData(SQLiteDatabase db) {
        ArrayList<PersistentObject> mockObjects=new ArrayList<>();
        //mockObjects.add(new User("Pablo Rodriguez","123456","pablorodri1984@gmail.com"));
        mockObjects.add(new IvaCategory(new Integer(1),"IVA Responsable Inscripto"));
        mockObjects.add(new IvaCategory(new Integer(2),"IVA Responsable no Inscripto"));
        mockObjects.add(new IvaCategory(new Integer(3),"IVA no Responsable"));
        mockObjects.add(new IvaCategory(new Integer(4),"IVA Sujeto Exento"));
        mockObjects.add(new IvaCategory(new Integer(5),"Consumidor Final"));
        mockObjects.add(new IvaCategory(new Integer(6),"Responsable Monotributo"));
        mockObjects.add(new IvaCategory(new Integer(7),"Sujeto no Categorizado"));
        mockObjects.add(new IvaCategory(new Integer(8),"Proveedor del Exterior"));
        mockObjects.add(new IvaCategory(new Integer(9),"Cliente del Exterior"));
        mockObjects.add(new IvaCategory(new Integer(10),"IVA Liberado – Ley Nº 19.640"));
        mockObjects.add(new IvaCategory(new Integer(11),"IVA Responsable Inscripto – Agente de Percepción"));
        mockObjects.add(new IvaCategory(new Integer(12),"Pequeño Contribuyente Eventual"));
        mockObjects.add(new IvaCategory(new Integer(13),"Monotributista Social"));
        mockObjects.add(new IvaCategory(new Integer(14),"Pequeño Contribuyente Eventual Social"));
        mockObjects.add(new MonotributoCategory("A",new BigDecimal("138127.99"),"No requiere","Hasta 30 m2","Hasta 3330 Kw"));
        mockObjects.add(new MonotributoCategory("B",new BigDecimal("207191.98"),"No requiere","Hasta 45 m2","Hasta 5000 Kw"));
        mockObjects.add(new MonotributoCategory("C",new BigDecimal("276255.98"),"No requiere","Hasta 60 m2","Hasta 6700 Kw"));
        mockObjects.add(new MonotributoCategory("D",new BigDecimal("414383.98"),"No requiere","Hasta 85 m2","Hasta 10000 Kw"));
        mockObjects.add(new MonotributoCategory("E",new BigDecimal("552511.95"),"No requiere","Hasta 110 m2","Hasta 13000 Kw"));
        mockObjects.add(new MonotributoCategory("F",new BigDecimal("690639.95"),"No requiere","Hasta 150 m2","Hasta 16500 Kw"));
        mockObjects.add(new MonotributoCategory("G",new BigDecimal("828767.94"),"No requiere","Hasta 200 m2","Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("H",new BigDecimal("1151066.58"),"No requiere","Hasta 200 m2","Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("I",new BigDecimal("1352503.24"),"No requiere","Hasta 200 m2","Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("J",new BigDecimal("1553939.89"),"No requiere","Hasta 200 m2","Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("K",new BigDecimal("1726599.88"),"No requiere","Hasta 200 m2","Hasta 20000 Kw"));
        mockObjects.add(new DocumentType(new Integer(0),"CI Policía Federal"));
        mockObjects.add(new DocumentType(new Integer(1),"CI Buenos Aires"));
        mockObjects.add(new DocumentType(new Integer(2),"CI Catamarca"));
        mockObjects.add(new DocumentType(new Integer(3),"CI Córdoba"));
        mockObjects.add(new DocumentType(new Integer(4),"CI Corrientes"));
        mockObjects.add(new DocumentType(new Integer(5),"CI Entre Ríos"));
        mockObjects.add(new DocumentType(new Integer(6),"CI Jujuy"));
        mockObjects.add(new DocumentType(new Integer(7),"CI Mendoza"));
        mockObjects.add(new DocumentType(new Integer(8),"CI La Rioja"));
        mockObjects.add(new DocumentType(new Integer(9),"CI Salta"));
        mockObjects.add(new DocumentType(new Integer(10),"CI San Juan"));
        mockObjects.add(new DocumentType(new Integer(11),"CI San Luis"));
        mockObjects.add(new DocumentType(new Integer(12),"CI Santa Fe"));
        mockObjects.add(new DocumentType(new Integer(13),"CI Santiago del Estero"));
        mockObjects.add(new DocumentType(new Integer(14),"CI Tucumán"));
        mockObjects.add(new DocumentType(new Integer(16),"CI Chaco"));
        mockObjects.add(new DocumentType(new Integer(17),"CI Chubut"));
        mockObjects.add(new DocumentType(new Integer(18),"CI Formosa"));
        mockObjects.add(new DocumentType(new Integer(19),"CI Misiones"));
        mockObjects.add(new DocumentType(new Integer(20),"CI Neuquén"));
        mockObjects.add(new DocumentType(new Integer(21),"CI La Pampa"));
        mockObjects.add(new DocumentType(new Integer(22),"CI Río Negro"));
        mockObjects.add(new DocumentType(new Integer(23),"CI Santa Cruz"));
        mockObjects.add(new DocumentType(new Integer(24),"CI Tierra del Fuego"));
        mockObjects.add(new DocumentType(new Integer(80),"CUIT"));
        mockObjects.add(new DocumentType(new Integer(86),"CUIL"));
        mockObjects.add(new DocumentType(new Integer(87),"CDI"));
        mockObjects.add(new DocumentType(new Integer(89),"LE"));
        mockObjects.add(new DocumentType(new Integer(90),"LC"));
        mockObjects.add(new DocumentType(new Integer(91),"CI extranjera"));
        mockObjects.add(new DocumentType(new Integer(92),"en trámite"));
        mockObjects.add(new DocumentType(new Integer(93),"Acta nacimiento"));
        mockObjects.add(new DocumentType(new Integer(94),"Pasaporte"));
        mockObjects.add(new DocumentType(new Integer(95),"CI Bs. As. RNP"));
        mockObjects.add(new DocumentType(new Integer(96),"DNI"));
        mockObjects.add(new DocumentType(new Integer(99),"Sin identificar/venta global diaria"));
        mockObjects.add(new DocumentType(new Integer(30),"Certificado de Migración"));
        mockObjects.add(new DocumentType(new Integer(88),"Usado por Anses para Padrón"));
        mockObjects.add(new Client("Juan Carlos", "Gil", "Calle Sin Numeración 1422"));
        for(PersistentObject p:mockObjects) {
            db.insert(p.getTableName(), null, p.toContentValues());
        }
    }
}
