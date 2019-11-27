package com.ort.profesionalinvoicemanager.model.base;

import android.database.sqlite.SQLiteDatabase;

import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import com.ort.profesionalinvoicemanager.model.invoice.InvoiceDetail;
import com.ort.profesionalinvoicemanager.model.invoice.PaymentCondition;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.model.product.Unit;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;
import com.ort.profesionalinvoicemanager.model.user.User;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public interface PersistenceAndMockData {

    public static ArrayList<String> getPersistenClasses() {
        ArrayList<String> persistenClasses = new ArrayList<>();
        persistenClasses.add(DocumentType.class.getName());
        persistenClasses.add(IvaCategory.class.getName());
        persistenClasses.add(MonotributoCategory.class.getName());
        persistenClasses.add(TaxInformation.class.getName());
        persistenClasses.add(Industry.class.getName());
        persistenClasses.add(User.class.getName());
        persistenClasses.add(Client.class.getName());
        persistenClasses.add(Unit.class.getName());
        persistenClasses.add(Product.class.getName());
        persistenClasses.add(Invoice.class.getName());
        persistenClasses.add(InvoiceDetail.class.getName());
        persistenClasses.add(PaymentCondition.class.getName());
        return persistenClasses;
    }

    public static ArrayList<PersistentObject> getMoObjects() {
        ArrayList<PersistentObject> mockObjects = new ArrayList<>();

        /*-----DATOS DE Referencia----*/
        IvaCategory ivaCategory = new IvaCategory(new Integer(1), "IVA Responsable Inscripto");
        mockObjects.add(ivaCategory);
        mockObjects.add(new IvaCategory(new Integer(2), "IVA Responsable no Inscripto"));
        mockObjects.add(new IvaCategory(new Integer(3), "IVA no Responsable"));
        mockObjects.add(new IvaCategory(new Integer(4), "IVA Sujeto Exento"));
        mockObjects.add(new IvaCategory(new Integer(5), "Consumidor Final"));
        mockObjects.add(new IvaCategory(new Integer(6), "Responsable Monotributo"));
        mockObjects.add(new IvaCategory(new Integer(7), "Sujeto no Categorizado"));
        mockObjects.add(new IvaCategory(new Integer(8), "Proveedor del Exterior"));
        mockObjects.add(new IvaCategory(new Integer(9), "Cliente del Exterior"));
        mockObjects.add(new IvaCategory(new Integer(10), "IVA Liberado – Ley Nº 19.640"));
        mockObjects.add(new IvaCategory(new Integer(11), "IVA Responsable Inscripto – Agente de Percepción"));
        mockObjects.add(new IvaCategory(new Integer(12), "Pequeño Contribuyente Eventual"));
        mockObjects.add(new IvaCategory(new Integer(13), "Monotributista Social"));
        mockObjects.add(new IvaCategory(new Integer(14), "Pequeño Contribuyente Eventual Social"));
        MonotributoCategory monotributoCategory = new MonotributoCategory("A", new BigDecimal("138127.99"), "No requiere", "Hasta 30 m2", "Hasta 3330 Kw");
        mockObjects.add(monotributoCategory);
        mockObjects.add(new MonotributoCategory("B", new BigDecimal("207191.98"), "No requiere", "Hasta 45 m2", "Hasta 5000 Kw"));
        mockObjects.add(new MonotributoCategory("C", new BigDecimal("276255.98"), "No requiere", "Hasta 60 m2", "Hasta 6700 Kw"));
        mockObjects.add(new MonotributoCategory("D", new BigDecimal("414383.98"), "No requiere", "Hasta 85 m2", "Hasta 10000 Kw"));
        mockObjects.add(new MonotributoCategory("E", new BigDecimal("552511.95"), "No requiere", "Hasta 110 m2", "Hasta 13000 Kw"));
        mockObjects.add(new MonotributoCategory("F", new BigDecimal("690639.95"), "No requiere", "Hasta 150 m2", "Hasta 16500 Kw"));
        mockObjects.add(new MonotributoCategory("G", new BigDecimal("828767.94"), "No requiere", "Hasta 200 m2", "Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("H", new BigDecimal("1151066.58"), "No requiere", "Hasta 200 m2", "Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("I", new BigDecimal("1352503.24"), "No requiere", "Hasta 200 m2", "Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("J", new BigDecimal("1553939.89"), "No requiere", "Hasta 200 m2", "Hasta 20000 Kw"));
        mockObjects.add(new MonotributoCategory("K", new BigDecimal("1726599.88"), "No requiere", "Hasta 200 m2", "Hasta 20000 Kw"));
        DocumentType documentType = new DocumentType(new Integer(0), "CI Policía Federal");
        mockObjects.add(documentType);
        mockObjects.add(new DocumentType(new Integer(1), "CI Buenos Aires"));
        mockObjects.add(new DocumentType(new Integer(2), "CI Catamarca"));
        mockObjects.add(new DocumentType(new Integer(3), "CI Córdoba"));
        mockObjects.add(new DocumentType(new Integer(4), "CI Corrientes"));
        mockObjects.add(new DocumentType(new Integer(5), "CI Entre Ríos"));
        mockObjects.add(new DocumentType(new Integer(6), "CI Jujuy"));
        mockObjects.add(new DocumentType(new Integer(7), "CI Mendoza"));
        mockObjects.add(new DocumentType(new Integer(8), "CI La Rioja"));
        mockObjects.add(new DocumentType(new Integer(9), "CI Salta"));
        mockObjects.add(new DocumentType(new Integer(10), "CI San Juan"));
        mockObjects.add(new DocumentType(new Integer(11), "CI San Luis"));
        mockObjects.add(new DocumentType(new Integer(12), "CI Santa Fe"));
        mockObjects.add(new DocumentType(new Integer(13), "CI Santiago del Estero"));
        mockObjects.add(new DocumentType(new Integer(14), "CI Tucumán"));
        mockObjects.add(new DocumentType(new Integer(16), "CI Chaco"));
        mockObjects.add(new DocumentType(new Integer(17), "CI Chubut"));
        mockObjects.add(new DocumentType(new Integer(18), "CI Formosa"));
        mockObjects.add(new DocumentType(new Integer(19), "CI Misiones"));
        mockObjects.add(new DocumentType(new Integer(20), "CI Neuquén"));
        mockObjects.add(new DocumentType(new Integer(21), "CI La Pampa"));
        mockObjects.add(new DocumentType(new Integer(22), "CI Río Negro"));
        mockObjects.add(new DocumentType(new Integer(23), "CI Santa Cruz"));
        mockObjects.add(new DocumentType(new Integer(24), "CI Tierra del Fuego"));
        mockObjects.add(new DocumentType(new Integer(80), "CUIT"));
        mockObjects.add(new DocumentType(new Integer(86), "CUIL"));
        mockObjects.add(new DocumentType(new Integer(87), "CDI"));
        mockObjects.add(new DocumentType(new Integer(89), "LE"));
        mockObjects.add(new DocumentType(new Integer(90), "LC"));
        mockObjects.add(new DocumentType(new Integer(91), "CI extranjera"));
        mockObjects.add(new DocumentType(new Integer(92), "en trámite"));
        mockObjects.add(new DocumentType(new Integer(93), "Acta nacimiento"));
        mockObjects.add(new DocumentType(new Integer(94), "Pasaporte"));
        mockObjects.add(new DocumentType(new Integer(95), "CI Bs. As. RNP"));
        mockObjects.add(new DocumentType(new Integer(96), "DNI"));
        mockObjects.add(new DocumentType(new Integer(99), "Sin identificar/venta global diaria"));
        mockObjects.add(new DocumentType(new Integer(30), "Certificado de Migración"));
        mockObjects.add(new DocumentType(new Integer(88), "Usado por Anses para Padrón"));
        Unit u=new Unit(new Integer(0), "SIN DESCRIPCION");
        mockObjects.add(u);
        mockObjects.add(new Unit(new Integer(1), "KILOGRAMO"));
        mockObjects.add(new Unit(new Integer(2), "METROS"));
        mockObjects.add(new Unit(new Integer(3), "METRO CUADRADO"));
        mockObjects.add(new Unit(new Integer(4), "METRO CUBICO"));
        mockObjects.add(new Unit(new Integer(5), "LITROS"));
        mockObjects.add(new Unit(new Integer(6), "1000 KILOWATT HORA"));
        mockObjects.add(new Unit(new Integer(7), "UNIDAD"));
        mockObjects.add(new Unit(new Integer(8), "PAR"));
        mockObjects.add(new Unit(new Integer(9), "DOCENA"));
        mockObjects.add(new Unit(new Integer(10), "QUILATE"));
        mockObjects.add(new Unit(new Integer(11), "MILLAR"));
        mockObjects.add(new Unit(new Integer(12), "MEGA U. INTER. ACT. ANTIB"));
        mockObjects.add(new Unit(new Integer(13), "UNIDAD INT. ACT. INMUNG"));
        mockObjects.add(new Unit(new Integer(14), "GRAMO"));
        mockObjects.add(new Unit(new Integer(15), "MILIMETRO"));
        mockObjects.add(new Unit(new Integer(16), "MILIMETRO CUBICO"));
        mockObjects.add(new Unit(new Integer(17), "KILOMETRO"));
        mockObjects.add(new Unit(new Integer(18), "HECTOLITRO"));
        mockObjects.add(new Unit(new Integer(19), "MEGA UNIDAD INT. ACT. INMUNG"));
        mockObjects.add(new Unit(new Integer(20), "CENTIMETRO"));
        mockObjects.add(new Unit(new Integer(21), "KILOGRAMO ACTIVO"));
        mockObjects.add(new Unit(new Integer(22), "GRAMO ACTIVO"));
        mockObjects.add(new Unit(new Integer(23), "GRAMO BASE"));
        mockObjects.add(new Unit(new Integer(24), "UIACTHOR"));
        mockObjects.add(new Unit(new Integer(25), "JGO.PQT. MAZO NAIPES"));
        mockObjects.add(new Unit(new Integer(26), "MUIACTHOR"));
        mockObjects.add(new Unit(new Integer(27), "CENTIMETRO CUBICO"));
        mockObjects.add(new Unit(new Integer(28), "UIACTANT"));
        mockObjects.add(new Unit(new Integer(29), "TONELADA"));
        mockObjects.add(new Unit(new Integer(30), "DECAMETRO CUBICO"));
        mockObjects.add(new Unit(new Integer(31), "HECTOMETRO CUBICO"));
        mockObjects.add(new Unit(new Integer(32), "KILOMETRO CUBICO"));
        mockObjects.add(new Unit(new Integer(33), "MICROGRAMO"));
        mockObjects.add(new Unit(new Integer(34), "NANOGRAMO"));
        mockObjects.add(new Unit(new Integer(35), "PICOGRAMO"));
        mockObjects.add(new Unit(new Integer(36), "MUIACTANT"));
        mockObjects.add(new Unit(new Integer(37), "UIACTIG"));
        mockObjects.add(new Unit(new Integer(41), "MILIGRAMO"));
        mockObjects.add(new Unit(new Integer(47), "MILILITRO"));
        mockObjects.add(new Unit(new Integer(48), "CURIE"));
        mockObjects.add(new Unit(new Integer(49), "MILICURIE"));
        mockObjects.add(new Unit(new Integer(50), "MICROCURIE"));
        mockObjects.add(new Unit(new Integer(51), "U.INTER. ACT. HORMONAL"));
        mockObjects.add(new Unit(new Integer(52), "MEGA U. INTER. ACT. HOR."));
        mockObjects.add(new Unit(new Integer(53), "KILOGRAMO BASE"));
        mockObjects.add(new Unit(new Integer(54), "GRUESA"));
        mockObjects.add(new Unit(new Integer(55), "MUIACTIG"));
        mockObjects.add(new Unit(new Integer(61), "KILOGRAMO BRUTO"));
        mockObjects.add(new Unit(new Integer(62), "PACK"));
        mockObjects.add(new Unit(new Integer(63), "HORMA"));
        mockObjects.add(new Unit(new Integer(97), "SEÑAS/ANTICIPOS"));
        mockObjects.add(new Unit(new Integer(98), "OTRAS UNIDADES"));
        mockObjects.add(new Unit(new Integer(99), "BONIFICACION"));
        PaymentCondition paymentCondition = new PaymentCondition(new Integer(0), "Contado");
        mockObjects.add(paymentCondition);
        mockObjects.add(new PaymentCondition(new Integer(1), "Tarjeta de Debito"));
        mockObjects.add(new PaymentCondition(new Integer(2), "Tarjeta de Debito"));
        mockObjects.add(new PaymentCondition(new Integer(3), "Cuenta Corriente"));
        mockObjects.add(new PaymentCondition(new Integer(4), "Cheque"));
        mockObjects.add(new PaymentCondition(new Integer(5), "Ticket"));
        mockObjects.add(new PaymentCondition(new Integer(6), "Mercado Pago"));
        mockObjects.add(new PaymentCondition(new Integer(7), "Otra"));

        /*-----DATOS DE PRUEBA quitar para version final----*/
        //mockObjects.add(new User("Pablo Rodriguez", "123456", "pablorodri1984@gmail.com"));

                Product p=new Product("Un Servicio","Desc","00",new Double(9.5),u,Product.IDENTIFICATOR_SERVICE);
        Product p2= new Product("Un Producto","Desc","01",new Double(98.5),u,Product.IDENTIFICATOR_PRODUCT);
       /* InvoiceDetail i=new InvoiceDetail(new Double(2.5), new Double(0), new Double(0), new Double(0),new Integer (10), p);
        InvoiceDetail i2=new InvoiceDetail(new Double(2.5), new Double(0), new Double(0), new Double(0),new Integer (10), p2);*/
        Date today = new Date();
        TaxInformation taxInformation = new TaxInformation("No inscritpo","11111111",documentType,ivaCategory,monotributoCategory);
//        TaxInformation taxInformation = new TaxInformation("No inscritpo","11111111",documentType, "CI Buenos Aires",ivaCategory,monotributoCategory);
        Client c=new Client("Juan Carlos", "Gil", "Calle Sin Numeración 1422","prueba@aol.com",taxInformation);
        Industry industry =new Industry("Industry test,","Salguero","pepe@pepe.com","12345678","12345678",today,taxInformation);

        Invoice invoice = new Invoice(c,paymentCondition,today,today,today,today,"A",industry,12345678,12345,1250.00,0.0,0.0,1250.00);

        mockObjects.add(c);
        mockObjects.add(p);
        mockObjects.add(p2);
        mockObjects.add(taxInformation);
        mockObjects.add(industry);
        mockObjects.add(invoice);
        return mockObjects;
    }

}
