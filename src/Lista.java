import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

    public void eliminar(Nodo n) {
        if (n != null && cabeza != null) {
            // buscar el nodo y su antecesor
            Nodo antecesor = null;
            Nodo apuntador = cabeza;
            while (apuntador != null && apuntador != n) {
                antecesor = apuntador;
                apuntador = apuntador.siguiente;
            }
            if (apuntador != null) {
                if (antecesor == null) {
                    cabeza = cabeza.siguiente;
                } else {
                    antecesor.siguiente = apuntador.siguiente;
                }
            }
        }
    }

    public Nodo getNodo(int posicion) {
        int posicionActual = 0;
        Nodo apuntador = cabeza;
        while (apuntador != null && posicionActual != posicion) {
            posicionActual++;
            apuntador = apuntador.siguiente;
        }
        /*
         * if (apuntador != null && posicionActual == posicion) {
         * return apuntador;
         * } else {
         * return null;
         * }
         */
        return apuntador != null && posicionActual == posicion ? apuntador : null;
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
            datos[fila][0] = apuntador.nombre;
            datos[fila][1] = apuntador.telefono;
            datos[fila][2] = apuntador.celular;
            datos[fila][3] = apuntador.direccion;
            datos[fila][4] = apuntador.correo;
            apuntador = apuntador.siguiente;
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        dtm.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // obtener la fuente de datos de la tabla
                DefaultTableModel dtm = (DefaultTableModel) e.getSource();
                int fila = e.getFirstRow();
                actualizar(fila,
                        (String) dtm.getValueAt(fila, 0),
                        (String) dtm.getValueAt(fila, 1),
                        (String) dtm.getValueAt(fila, 2),
                        (String) dtm.getValueAt(fila, 3),
                        (String) dtm.getValueAt(fila, 4));
            }

        });
        tbl.setModel(dtm);
    }

    public void actualizar(int posicion,
            String nombre,
            String telefono,
            String celular,
            String direccion,
            String correo) {
        Nodo n = getNodo(posicion);
        if (n != null) {
            n.actualizar(nombre, telefono, celular, direccion, correo);
        }
    }

    public boolean guardar(String nombreArchivo) {
        String[] datos = new String[getLongitud()];

        int fila = -1;
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            fila++;
            datos[fila] = apuntador.nombre + ";" +
                    apuntador.telefono + ";" +
                    apuntador.celular + ";" +
                    apuntador.direccion + ";" +
                    (apuntador.correo.length() == 0 ? " " : apuntador.correo);
            apuntador = apuntador.siguiente;
        }
        return Archivo.guardarArchivo(nombreArchivo, datos);
    }

    private void intercambiar(Nodo a1, Nodo n1, Nodo a2, Nodo n2) {
        if (cabeza != null && n1 != null && n2 != null && n1 != n2 && a2 != null) {
            if (a1 != null) {
                a1.siguiente = n2;
            } else {
                cabeza = n2;
            }
            // Se guarda temporalmente el apuntador siguiente del segundo nodo
            Nodo t = n2.siguiente;

            if (n1 != a2) {
                n2.siguiente = n1.siguiente;
                a2.siguiente = n1;
            } else {
                n2.siguiente = n1;
            }

            n1.siguiente = t;
        }
    }

    // ********* atributos estaticos

    public static String[] encabezados = new String[] { "Nombre", "Telefono", "Celular", "Direccion", "Correo" };

}
