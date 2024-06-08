package com.google.codelabs.mdc.java.shrine.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.google.codelabs.mdc.java.shrine.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A product entry in the list of products.
 */
public class ClienteEntry {
    private static final String TAG = ClienteEntry.class.getSimpleName();


    public final Uri dynamicUrl;

    public final String url;
    public final String nombre;
    public final String dni;
    public final Integer num_factura;
    public final Integer cantidad_productos;
    public final Double monto_factura;
    public final String enlace_de_informacion;
    public final String enlace_de_imagen;




    public ClienteEntry(
            String dynamicUrl, String url, String nombre, String dni, Integer num_factura, String cantidad_productos,
            Double monto_factura, String enlace_de_informacion, String enlace_de_imagen ) {


        this.dynamicUrl = Uri.parse(dynamicUrl);
        this.url = url;
        this.nombre = nombre;
        this.dni = dni;
        this.num_factura = num_factura;
        this.cantidad_productos = Integer.valueOf(cantidad_productos);
        this.monto_factura = monto_factura;
        this.enlace_de_informacion = enlace_de_informacion;
        this.enlace_de_imagen = enlace_de_imagen;

    }

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
     */
    public static List<ClienteEntry> initClienteEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.clientes);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonClientesString = writer.toString();
        Gson gson = new Gson();
        Type clienteListType = new TypeToken<ArrayList<ClienteEntry>>() {
        }.getType();
        return gson.fromJson(jsonClientesString, clienteListType);
    }
}

//  JSON         JAVA
//  []Lista      ArrayList
//  {}Object    Object. Objetos que se
//              instancian de una clase
//