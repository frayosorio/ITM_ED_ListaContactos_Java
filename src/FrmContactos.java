import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmContactos extends JFrame {

    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnGuardar;
    private JButton btnOrdenar;
    private JToolBar tbContactos;
    private JTable tblContactos;

    private Lista listaContactos = new Lista();

    public FrmContactos() {
        tbContactos = new JToolBar();
        btnAgregar = new JButton();
        btnEliminar = new JButton();
        btnGuardar = new JButton();
        btnOrdenar = new JButton();
        tblContactos = new JTable();

        setSize(600, 300);
        setTitle("Mis contactos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // getContentPane().setLayout(null);

        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/Iconos/Agregar.png")));
        btnAgregar.setToolTipText("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAgregarClick(evt);
            }
        });
        tbContactos.add(btnAgregar);

        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/Iconos/Eliminar.png")));
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnEliminarClick(evt);
            }
        });
        tbContactos.add(btnEliminar);

        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/Iconos/Guardar.png")));
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnGuardarClick(evt);
            }
        });
        tbContactos.add(btnGuardar);

        btnOrdenar.setIcon(new ImageIcon(getClass().getResource("/Iconos/Ordenar.png")));
        btnOrdenar.setToolTipText("Ordenar");
        btnOrdenar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOrdenarClick(evt);
            }
        });
        tbContactos.add(btnOrdenar);

        DefaultTableModel dtm = new DefaultTableModel(null, Lista.encabezados);
        tblContactos.setModel(dtm);

        JScrollPane spContactos = new JScrollPane(tblContactos);

        getContentPane().add(tbContactos, BorderLayout.NORTH);
        getContentPane().add(spContactos, BorderLayout.CENTER);

        // Cargar los datos desde el archivo a la lista
        String nombreArchivo = System.getProperty("user.dir") + "/src/datos/datos.txt";

        listaContactos.desdeArchivo(nombreArchivo);
        listaContactos.mostrar(tblContactos);
    }

    private void btnAgregarClick(ActionEvent evt) {
        listaContactos.agregar(new Nodo());
        listaContactos.mostrar(tblContactos);
    }

    private void btnEliminarClick(ActionEvent evt) {
        if (tblContactos.getSelectedRow() >= 0) {
            String[] opciones = { "Sí", "No" };
            int decision = JOptionPane.showOptionDialog(null, "Está seguro en eliminar el contacto?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[1]);
            if (decision == JOptionPane.YES_OPTION) {
                listaContactos.eliminar(listaContactos.getNodo(tblContactos.getSelectedRow()));
                listaContactos.mostrar(tblContactos);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el contacto a retirar");
        }
    }

    private void btnGuardarClick(ActionEvent evt) {
    }

    private void btnOrdenarClick(ActionEvent evt) {
    }

}