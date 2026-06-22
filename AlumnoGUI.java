import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class AlumnoGUI extends JFrame {
    // Colecciones para almacenar alumnos
    private ArrayList<Alumno> listaInformaticos;
    private ArrayList<Alumno> listaIndustriales;

    // Componentes de la GUI
    private JTextArea areaMostrar;

    public AlumnoGUI() {
        // Inicializar listas
        listaInformaticos = new ArrayList<>();
        listaIndustriales = new ArrayList<>();

        // Configurar la ventana principal
        setTitle("Sistema de Alumnos");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear el menú
        crearMenu();

        // Área para mostrar información
        areaMostrar = new JTextArea();
        areaMostrar.setEditable(false);
        areaMostrar.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaMostrar);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menú "Opciones"
        JMenu menuOpciones = new JMenu("Opciones");

        // Opción 1: Ingresar Informático
        JMenuItem itemIngresarInfo = new JMenuItem("Ingresar datos de Informático");
        itemIngresarInfo.addActionListener(e -> ingresarAlumno("Informático"));
        menuOpciones.add(itemIngresarInfo);

        // Opción 2: Ingresar Industrial
        JMenuItem itemIngresarInd = new JMenuItem("Ingresar datos de Industrial");
        itemIngresarInd.addActionListener(e -> ingresarAlumno("Industrial"));
        menuOpciones.add(itemIngresarInd);

        menuOpciones.addSeparator();

        // Opción 3: Mostrar Informáticos
        JMenuItem itemMostrarInfo = new JMenuItem("Mostrar Informáticos");
        itemMostrarInfo.addActionListener(e -> mostrarAlumnos(listaInformaticos, "INFORMÁTICOS"));
        menuOpciones.add(itemMostrarInfo);

        // Opción 4: Mostrar Industriales
        JMenuItem itemMostrarInd = new JMenuItem("Mostrar Industriales");
        itemMostrarInd.addActionListener(e -> mostrarAlumnos(listaIndustriales, "INDUSTRIALES"));
        menuOpciones.add(itemMostrarInd);

        menuOpciones.addSeparator();

        // Opción 5: Salir
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuOpciones.add(itemSalir);

        menuBar.add(menuOpciones);
        setJMenuBar(menuBar);
    }

    // Método para ingresar un alumno (validando campos vacíos)
    private void ingresarAlumno(String tipo) {
        // Diálogo personalizado con JOptionPane
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField txtCodigo = new JTextField(15);
        JTextField txtApellidos = new JTextField(15);
        JTextField txtNombres = new JTextField(15);
        JTextField txtDomicilio = new JTextField(15);

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Apellidos:"));
        panel.add(txtApellidos);
        panel.add(new JLabel("Nombres:"));
        panel.add(txtNombres);
        panel.add(new JLabel("Domicilio:"));
        panel.add(txtDomicilio);

        int resultado = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Ingresar datos de " + tipo, 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            // Validar que ningún campo esté vacío
            String codigo = txtCodigo.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String nombres = txtNombres.getText().trim();
            String domicilio = txtDomicilio.getText().trim();

            if (codigo.isEmpty() || apellidos.isEmpty() || nombres.isEmpty() || domicilio.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Todos los campos son obligatorios. No se permiten campos vacíos.", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Crear el objeto Alumno
            Alumno nuevoAlumno = new Alumno(codigo, apellidos, nombres, domicilio);

            // Agregar a la lista correspondiente
            if (tipo.equals("Informático")) {
                listaInformaticos.add(nuevoAlumno);
                JOptionPane.showMessageDialog(this, "Informático agregado exitosamente.");
            } else {
                listaIndustriales.add(nuevoAlumno);
                JOptionPane.showMessageDialog(this, "Industrial agregado exitosamente.");
            }
        }
    }

    // Método para mostrar alumnos ordenados
    private void mostrarAlumnos(ArrayList<Alumno> lista, String titulo) {
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, 
                "No hay alumnos en la lista de " + titulo + ".", 
                "Lista vacía", 
                JOptionPane.INFORMATION_MESSAGE
            );
            areaMostrar.setText("");
            return;
        }

        // Ordenar la lista usando Comparable (Apellidos y luego Nombres)
        Collections.sort(lista);

        // Construir el texto a mostrar
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTA DE ").append(titulo).append(" ===\n\n");
        for (Alumno alumno : lista) {
            sb.append(alumno.toString()).append("\n");
        }

        areaMostrar.setText(sb.toString());
    }

    // Método main
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new AlumnoGUI());
    }
}
