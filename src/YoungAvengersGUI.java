import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class YoungAvengersGUI {
    private JPanel pGeneral;
    private JTabbedPane tabbedPane1;
    private JTextArea txtCodigoR;
    private JTextArea txtNombreR;
    private JComboBox<String> comboBox1; // Poder especial
    private JComboBox<Integer> comboBox2; // Nivel de habilidad
    private JTextField textField1; // Código para búsqueda
    private JTextArea txtNombre;
    private JTextArea txtPoder;
    private JTextArea txtNivel;
    private JTextArea txtMision;
    private JComboBox<String> CbFiltrar; // Poder especial para filtro
    private JTable tbFiltrar;
    private JButton BtnRegistrar;
    private JButton BtnBuscar;
    private JTable tbRegistrar;
    private JButton BtnFiltrar;
    private JTextArea txtCalcular;
    private JButton BtnCalcular;
    private JComboBox<String> comboBox3; // Misión activa

    private GestionYoungAvengers gestion = new GestionYoungAvengers();

    public YoungAvengersGUI() {
        // Inicializamos los modelos de las tablas
        DefaultTableModel modelRegistrar = new DefaultTableModel(new Object[]{"Código", "Nombre", "Poder Especial", "Nivel Habilidad", "Misión Activa"}, 0);
        tbRegistrar.setModel(modelRegistrar);

        DefaultTableModel modelFiltrar = new DefaultTableModel(new Object[]{"Código", "Nombre", "Poder Especial", "Nivel Habilidad", "Misión Activa"}, 0);
        tbFiltrar.setModel(modelFiltrar);

        // Configuramos los ComboBox
        comboBox1.setModel(new DefaultComboBoxModel<>(new String[]{
                "Magia", "Manipulación de Energía", "Super Velocidad", "Superfuerza", "Arquería"
        }));

        comboBox2.setModel(new DefaultComboBoxModel<>(new Integer[]{1, 2, 3, 4, 5}));

        comboBox3.setModel(new DefaultComboBoxModel<>(new String[]{
                "Rescate de Civiles", "Contención de Amenazas", "Investigación", "Apoyo Estratégico", "Recuperación de Artefactos"
        }));

        CbFiltrar.setModel(new DefaultComboBoxModel<>(new String[]{
                "Magia", "Manipulación de Energía", "Super Velocidad", "Superfuerza", "Arquería"
        }));

        // Ejecutamos el botón de registrar
        BtnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(txtCodigoR.getText());
                    String nombre = txtNombreR.getText();
                    String poder = comboBox1.getSelectedItem().toString();
                    int habilidad = (int) comboBox2.getSelectedItem();
                    String mision = comboBox3.getSelectedItem().toString();

                    YoungAvenger avenger = new YoungAvenger(codigo, nombre, poder, habilidad, mision);
                    if (gestion.agregarAvenger(avenger)) {
                        actualizarTabla(tbRegistrar);
                        JOptionPane.showMessageDialog(null, "Young Avenger registrado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "El código ya existe.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un código válido.");
                }
            }
        });

        // Ejecutamos el botón de buscar
        BtnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(textField1.getText());
                    YoungAvenger avenger = gestion.buscarAvenger(codigo);

                    if (avenger != null) {
                        txtNombre.setText(avenger.getNombre());
                        txtPoder.setText(avenger.getPoderEspecial());
                        txtNivel.setText(String.valueOf(avenger.getNivelHabilidad()));
                        txtMision.setText(avenger.getMisionActiva());
                    } else {
                        JOptionPane.showMessageDialog(null, "Young Avenger no encontrado.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un código válido.");
                }
            }
        });

        // Ejecutamos el botón de filtrar
        BtnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String poderSeleccionado = CbFiltrar.getSelectedItem().toString();
                LinkedList<YoungAvenger> listaFiltrada = gestion.filtrarYOrdenar(poderSeleccionado);

                DefaultTableModel model = (DefaultTableModel) tbFiltrar.getModel();
                model.setRowCount(0);
                for (YoungAvenger avenger : listaFiltrada) {
                    model.addRow(new Object[]{
                            avenger.getCodigo(),
                            avenger.getNombre(),
                            avenger.getPoderEspecial(),
                            avenger.getNivelHabilidad(),
                            avenger.getMisionActiva()
                    });
                }
            }
        });

        // Ejecutamos el botón de calcular
        BtnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String misionSeleccionada = comboBox3.getSelectedItem().toString();
                int totalMisiones = gestion.contarMisionesPorMision(misionSeleccionada, 0);
                txtCalcular.setText("Total de misiones activas para \"" + misionSeleccionada + "\": " + totalMisiones);
            }
        });
    }

    //Aquí actualizamos los contenidos de las tablas con nuevos datos
    private void actualizarTabla(JTable tabla) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        for (YoungAvenger avenger : gestion.getListaAvengers()) {
            model.addRow(new Object[]{
                    avenger.getCodigo(),
                    avenger.getNombre(),
                    avenger.getPoderEspecial(),
                    avenger.getNivelHabilidad(),
                    avenger.getMisionActiva()
            });
        }
    }

    //Main para iniciar la aplicación

    public static void main(String[] args) {
        JFrame frame = new JFrame("Young Avengers GUI");
        frame.setContentPane(new YoungAvengersGUI().pGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
    }
}
