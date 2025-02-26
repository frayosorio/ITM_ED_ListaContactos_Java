import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Lista {

    private Nodo cabeza;

    public Lista() {
        cabeza = null;
    }

    public void agregar(Nodo n) {
        if (n != null) {
            if (cabeza == null) {
                cabeza = n;
            } else {
                // ubicar el último nodo
                Nodo apuntador = cabeza;
                while (apuntador.siguiente != null) {
                    apuntador = apuntador.siguiente;
                }
                // unir el nuevo nodo
                apuntador.siguiente = n;
            }
            n.siguiente = null;
        }
    }

    public void desdeArchivo(String nombreArchivo) {
        cabeza = null;
        BufferedReader br = Archivo.abrirArchivo(nombreArchivo);
        if (br != null) {
            try {
                String linea = br.readLine();
                while (linea != null) {
                    String[] datos = linea.split(";");
                    if (datos.length >= 5) {
                        Nodo n = new Nodo(datos[0], datos[1], datos[2], datos[3], datos[4]);
                        agregar(n);
                    }
                    linea = br.readLine();
                }

            } catch (IOException ex) {

            } catch (Exception ex) {

            }
        }
    }

    public int getLongitud() {
        int longitud = 0;
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            apuntador = apuntador.siguiente;
            longitud++;
        }
        return longitud;
    }

    public void mostrar(JTable tbl) {
        String[][] datos = new String[getLongitud()][5];

        int fila = -1;
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            fila++;
            datos[fila][0]=apuntador.nombre;
            datos[fila][1]=apuntador.telefono;
            datos[fila][2]=apuntador.celular;
            datos[fila][3]=apuntador.direccion;
            datos[fila][4]=apuntador.correo;
            apuntador = apuntador.siguiente;
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }

    // ********* atributos estaticos

    public static String[] encabezados = new String[] { "Nombre", "Telefono", "Celular", "Direccion", "Correo" };

}
