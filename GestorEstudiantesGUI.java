import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GestorEstudiantesGUI extends JFrame {
    // COMPOSICIÓN: La GUI contiene listas de estudiantes
    private ArrayList<Estudiante> listaInformaticos;
    private ArrayList<Estudiante> listaIndustriales;
    private Estudiante estudianteSeleccionado;

    // Componentes GUI
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private JTextArea areaDetalle;
    private JComboBox<String> comboFiltro;
    private JLabel lblContador;

    public GestorEstudiantesGUI() {
        // Inicializar listas
        listaInformaticos = new ArrayList<>();
        listaIndustriales = new ArrayList<>();
        estudianteSeleccionado = null;

        // Configurar ventana
        setTitle("Gestor de Estudiantes - Ejemplo de Composición");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Crear barra de menú
        crearMenu();

        // Panel principal dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(350);

        // Panel superior: Tabla
        JPanel panelTabla = crearPanelTabla();
        splitPane.setTopComponent(panelTabla);

        // Panel inferior: Detalles y controles
        JPanel panelDetalle = crearPanelDetalle();
        splitPane.setBottomComponent(panelDetalle);

        add(splitPane, BorderLayout.CENTER);
        add(crearPanelEstadisticas(), BorderLayout.SOUTH);

        // Cargar datos de ejemplo
        cargarDatosEjemplo();
        actualizarTabla();

        setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuEstudiante = new JMenu("🎓 Estudiante");
        
        JMenuItem itemIngresar = new JMenuItem("Ingresar Nuevo Estudiante");
        itemIngresar.addActionListener(e -> mostrarDialogoIngreso());
        
        JMenuItem itemEditar = new JMenuItem("Editar Estudiante Seleccionado");
        itemEditar.addActionListener(e -> editarEstudiante());
        
        JMenuItem itemEliminar = new JMenuItem("Eliminar Estudiante");
        itemEliminar.addActionListener(e -> eliminarEstudiante());
        
        menuEstudiante.add(itemIngresar);
        menuEstudiante.add(itemEditar);
        menuEstudiante.addSeparator();
        menuEstudiante.add(itemEliminar);
        
        JMenu menuVer = new JMenu("👁️ Ver");
        
        JMenuItem itemMostrarDetalle = new JMenuItem("Mostrar Detalle Completo");
        itemMostrarDetalle.addActionListener(e -> mostrarDetalleCompleto());
        
        JMenuItem itemOrdenar = new JMenuItem("Ordenar por Apellidos");
        itemOrdenar.addActionListener(e -> ordenarLista());
        
        menuVer.add(itemMostrarDetalle);
        menuVer.add(itemOrdenar);
        
        JMenu menuAyuda = new JMenu("❓ Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de Composición");
        itemAcerca.addActionListener(e -> mostrarAyuda());
        menuAyuda.add(itemAcerca);

        menuBar.add(menuEstudiante);
        menuBar.add(menuVer);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("📋 Lista de Estudiantes"));

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Filtrar por:"));
        
        comboFiltro = new JComboBox<>(new String[]{"Todos", "Informáticos", "Industriales"});
        comboFiltro.addActionListener(e -> actualizarTabla());
        panelFiltros.add(comboFiltro);

        // Botón actualizar
        JButton btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.addActionListener(e -> actualizarTabla());
        panelFiltros.add(btnActualizar);

        panel.add(panelFiltros, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Código", "Apellidos", "Nombres", "Carrera", "Promedio", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setRowHeight(25);
        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalleSeleccionado();
            }
        });
        
        JScrollPane scroll = new JScrollPane(tablaEstudiantes);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelDetalle() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("📝 Detalles del Estudiante"));

        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaDetalle.setBackground(new Color(240, 248, 255));
        JScrollPane scroll = new JScrollPane(areaDetalle);
        panel.add(scroll, BorderLayout.CENTER);

        // Panel de botones de acción rápida
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton btnVerCompleto = new JButton("📊 Ver Estado Completo");
        btnVerCompleto.addActionListener(e -> mostrarDetalleCompleto());
        
        JButton btnCambiarEstado = new JButton("🔄 Cambiar Estado Académico");
        btnCambiarEstado.addActionListener(e -> cambiarEstadoAcademico());
        
        panelBotones.add(btnVerCompleto);
        panelBotones.add(btnCambiarEstado);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panel.setBorder(new TitledBorder("📊 Estadísticas"));
        panel.setBackground(new Color(240, 240, 240));

        lblContador = new JLabel();
        actualizarEstadisticas();
        panel.add(lblContador);

        JButton btnExportar = new JButton("📤 Exportar Resumen");
        btnExportar.addActionListener(e -> exportarResumen());
        panel.add(btnExportar);

        return panel;
    }

    private void mostrarDialogoIngreso() {
        // Creamos un panel con tabs para organizar los datos
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Panel 1: Datos Personales
        JPanel panelPersonal = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField txtNombres = new JTextField();
        JTextField txtApellidos = new JTextField();
        JTextField txtDomicilio = new JTextField();
        JTextField txtFechaNac = new JTextField();
        
        panelPersonal.add(new JLabel("Nombres:"));
        panelPersonal.add(txtNombres);
        panelPersonal.add(new JLabel("Apellidos:"));
        panelPersonal.add(txtApellidos);
        panelPersonal.add(new JLabel("Domicilio:"));
        panelPersonal.add(txtDomicilio);
        panelPersonal.add(new JLabel("Fecha Nacimiento:"));
        panelPersonal.add(txtFechaNac);
        
        // Panel 2: Datos Académicos
        JPanel panelAcademico = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField txtCodigo = new JTextField();
        JComboBox<String> cmbCarrera = new JComboBox<>(new String[]{"Ingeniería Informática", "Ingeniería Industrial"});
        JTextField txtPromedio = new JTextField();
        JTextField txtCiclo = new JTextField();
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo", "Graduado"});
        
        panelAcademico.add(new JLabel("Código:"));
        panelAcademico.add(txtCodigo);
        panelAcademico.add(new JLabel("Carrera:"));
        panelAcademico.add(cmbCarrera);
        panelAcademico.add(new JLabel("Promedio:"));
        panelAcademico.add(txtPromedio);
        panelAcademico.add(new JLabel("Ciclo:"));
        panelAcademico.add(txtCiclo);
        panelAcademico.add(new JLabel("Estado:"));
        panelAcademico.add(cmbEstado);
        
        // Panel 3: Datos de Contacto
        JPanel panelContacto = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCelular = new JTextField();
        JTextField txtRedes = new JTextField();
        
        panelContacto.add(new JLabel("Email:"));
        panelContacto.add(txtEmail);
        panelContacto.add(new JLabel("Teléfono:"));
        panelContacto.add(txtTelefono);
        panelContacto.add(new JLabel("Celular:"));
        panelContacto.add(txtCelular);
        panelContacto.add(new JLabel("Redes Sociales:"));
        panelContacto.add(txtRedes);

        tabbedPane.addTab("👤 Personal", panelPersonal);
        tabbedPane.addTab("📚 Académico", panelAcademico);
        tabbedPane.addTab("📱 Contacto", panelContacto);

        int resultado = JOptionPane.showConfirmDialog(
            this,
            tabbedPane,
            "Ingresar Nuevo Estudiante",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            // Validar campos obligatorios
            if (txtNombres.getText().trim().isEmpty() || 
                txtApellidos.getText().trim().isEmpty() ||
                txtCodigo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nombres, Apellidos y Código son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Crear los tres objetos componentes
                DatosPersonales dp = new DatosPersonales(
                    txtNombres.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtDomicilio.getText().trim(),
                    txtFechaNac.getText().trim()
                );

                DatosAcademicos da = new DatosAcademicos(
                    txtCodigo.getText().trim(),
                    (String) cmbCarrera.getSelectedItem(),
                    Double.parseDouble(txtPromedio.getText().trim()),
                    Integer.parseInt(txtCiclo.getText().trim()),
                    (String) cmbEstado.getSelectedItem()
                );

                DatosContacto dc = new DatosContacto(
                    txtEmail.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCelular.getText().trim(),
                    txtRedes.getText().trim()
                );

                // Crear el estudiante usando COMPOSICIÓN
                String tipo = ((String) cmbCarrera.getSelectedItem()).contains("Informática") 
                             ? "Informático" : "Industrial";
                
                Estudiante estudiante = new Estudiante(dp, da, dc, tipo);

                // Agregar a la lista correspondiente
                if (tipo.equals("Informático")) {
                    listaInformaticos.add(estudiante);
                } else {
                    listaIndustriales.add(estudiante);
                }

                actualizarTabla();
                JOptionPane.showMessageDialog(this, 
                    "✅ Estudiante agregado exitosamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Error en el formato de números (Promedio o Ciclo).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        
        String filtro = (String) comboFiltro.getSelectedItem();
        ArrayList<Estudiante> listaMostrar = new ArrayList<>();
        
        if (filtro.equals("Todos")) {
            listaMostrar.addAll(listaInformaticos);
            listaMostrar.addAll(listaIndustriales);
        } else if (filtro.equals("Informáticos")) {
            listaMostrar.addAll(listaInformaticos);
        } else if (filtro.equals("Industriales")) {
            listaMostrar.addAll(listaIndustriales);
        }

        // Ordenar por apellidos
        Collections.sort(listaMostrar);

        for (Estudiante e : listaMostrar) {
            Object[] fila = {
                e.getCodigo(),
                e.getDatosPersonales().getApellidos(),
                e.getDatosPersonales().getNombres(),
                e.getDatosAcademicos().getCarrera(),
                String.format("%.2f", e.getDatosAcademicos().getPromedio()),
                e.estaAprobado() ? "✅ Aprobado" : "❌ Desaprobado"
            };
            modeloTabla.addRow(fila);
        }

        actualizarEstadisticas();
    }

    private void mostrarDetalleSeleccionado() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila >= 0) {
            String filtro = (String) comboFiltro.getSelectedItem();
            ArrayList<Estudiante> listaMostrar = new ArrayList<>();
            
            if (filtro.equals("Todos")) {
                listaMostrar.addAll(listaInformaticos);
                listaMostrar.addAll(listaIndustriales);
            } else if (filtro.equals("Informáticos")) {
                listaMostrar.addAll(listaInformaticos);
            } else if (filtro.equals("Industriales")) {
                listaMostrar.addAll(listaIndustriales);
            }
            
            Collections.sort(listaMostrar);
            
            if (fila < listaMostrar.size()) {
                estudianteSeleccionado = listaMostrar.get(fila);
                areaDetalle.setText(estudianteSeleccionado.mostrarEstadoCompleto());
            }
        }
    }

    private void mostrarDetalleCompleto() {
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un estudiante de la tabla.",
                "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this,
            estudianteSeleccionado.mostrarEstadoCompleto(),
            "Detalle Completo del Estudiante",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void editarEstudiante() {
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un estudiante de la tabla.",
                "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Aquí iría la lógica para editar (similar al ingreso)
        JOptionPane.showMessageDialog(this,
            "Función de edición en desarrollo.\n" +
            "Estudiante seleccionado: " + estudianteSeleccionado.getNombreCompleto(),
            "Editar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarEstudiante() {
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un estudiante de la tabla.",
                "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar a:\n" + 
            estudianteSeleccionado.getNombreCompleto() + "\n" +
            "Código: " + estudianteSeleccionado.getCodigo(),
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (estudianteSeleccionado.getTipo().equals("Informático")) {
                listaInformaticos.remove(estudianteSeleccionado);
            } else {
                listaIndustriales.remove(estudianteSeleccionado);
            }
            estudianteSeleccionado = null;
            areaDetalle.setText("");
            actualizarTabla();
            JOptionPane.showMessageDialog(this,
                "✅ Estudiante eliminado exitosamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cambiarEstadoAcademico() {
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un estudiante de la tabla.",
                "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] opciones = {"Activo", "Inactivo", "Graduado"};
        String nuevoEstado = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione el nuevo estado para:\n" + 
            estudianteSeleccionado.getNombreCompleto(),
            "Cambiar Estado Académico",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            estudianteSeleccionado.getDatosAcademicos().getEstado()
        );

        if (nuevoEstado != null) {
            estudianteSeleccionado.getDatosAcademicos().setEstado(nuevoEstado);
            actualizarTabla();
            mostrarDetalleSeleccionado();
            JOptionPane.showMessageDialog(this,
                "✅ Estado actualizado a: " + nuevoEstado,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void ordenarLista() {
        Collections.sort(listaInformaticos);
        Collections.sort(listaIndustriales);
        actualizarTabla();
        JOptionPane.showMessageDialog(this,
            "✅ Listas ordenadas por apellidos y nombres.",
            "Ordenado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarEstadisticas() {
        int total = listaInformaticos.size() + listaIndustriales.size();
        int aprobados = 0;
        
        for (Estudiante e : listaInformaticos) {
            if (e.estaAprobado()) aprobados++;
        }
        for (Estudiante e : listaIndustriales) {
            if (e.estaAprobado()) aprobados++;
        }

        lblContador.setText(String.format(
            "📊 Total: %d estudiantes | ✅ Aprobados: %d | ❌ Desaprobados: %d | " +
            "💻 Informáticos: %d | 🏭 Industriales: %d",
            total, aprobados, total - aprobados,
            listaInformaticos.size(), listaIndustriales.size()
        ));
    }

    private void exportarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMEN DE ESTUDIANTES ===\n\n");
        
        sb.append("INFORMÁTICOS (").append(listaInformaticos.size()).append("):\n");
        Collections.sort(listaInformaticos);
        for (Estudiante e : listaInformaticos) {
            sb.append("  • ").append(e.toString()).append("\n");
        }
        
        sb.append("\nINDUSTRIALES (").append(listaIndustriales.size()).append("):\n");
        Collections.sort(listaIndustriales);
        for (Estudiante e : listaIndustriales) {
            sb.append("  • ").append(e.toString()).append("\n");
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JOptionPane.showMessageDialog(this,
            new JScrollPane(textArea),
            "📤 Resumen de Estudiantes",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAyuda() {
        String ayuda = """
            🎓 SISTEMA DE GESTIÓN DE ESTUDIANTES
            ════════════════════════════════════
            
            📚 COMPOSICIÓN EN ACCIÓN:
            
            Cada ESTUDIANTE está compuesto por:
            1. DatosPersonales (nombre, apellidos, domicilio, fecha nac.)
            2. DatosAcademicos (código, carrera, promedio, ciclo, estado)
            3. DatosContacto (email, teléfono, celular, redes sociales)
            
            🔍 PRINCIPIOS DEMOSTRADOS:
            • Composición: Estudiante TIENE UN objeto de cada tipo
            • Delegación: Métodos que usan los componentes
            • Encapsulamiento: Cada componente maneja sus datos
            • Reutilización: Los componentes pueden usarse en otros contextos
            
            🎯 FUNCIONALIDADES:
            • Ingresar estudiantes con todos sus datos
            • Ver listado con filtros por carrera
            • Ver detalle completo de cada estudiante
            • Ordenar por apellidos y nombres
            • Cambiar estado académico
            • Exportar resumen
            """;
        
        JOptionPane.showMessageDialog(this,
            ayuda,
            "❓ Ayuda - Composición con Estudiantes",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarDatosEjemplo() {
        // Crear algunos estudiantes de ejemplo usando COMPOSICIÓN
        DatosPersonales dp1 = new DatosPersonales("Ana", "García Pérez", "Calle 123", "15/05/2000");
        DatosAcademicos da1 = new DatosAcademicos("2024001", "Ingeniería Informática", 15.5, 5, "Activo");
        DatosContacto dc1 = new DatosContacto("ana@email.com", "555-1234", "999-888-777", "@anagarcia");
        listaInformaticos.add(new Estudiante(dp1, da1, dc1, "Informático"));

        DatosPersonales dp2 = new DatosPersonales("Carlos", "López Mendoza", "Av. Principal 456", "20/08/1999");
        DatosAcademicos da2 = new DatosAcademicos("2024002", "Ingeniería Informática", 12.0, 6, "Activo");
        DatosContacto dc2 = new DatosContacto("carlos@email.com", "555-5678", "999-666-555", "@carloslopez");
        listaInformaticos.add(new Estudiante(dp2, da2, dc2, "Informático"));

        DatosPersonales dp3 = new DatosPersonales("María", "Rodríguez Castro", "Calle 789", "10/12/2001");
        DatosAcademicos da3 = new DatosAcademicos("2024003", "Ingeniería Industrial", 8.5, 3, "Activo");
        DatosContacto dc3 = new DatosContacto("maria@email.com", "555-9012", "999-444-333", "@mariarod");
        listaIndustriales.add(new Estudiante(dp3, da3, dc3, "Industrial"));

        DatosPersonales dp4 = new DatosPersonales("Juan", "Martínez Sánchez", "Av. Secundaria 321", "25/03/2000");
        DatosAcademicos da4 = new DatosAcademicos("2024004", "Ingeniería Industrial", 14.0, 7, "Activo");
        DatosContacto dc4 = new DatosContacto("juan@email.com", "555-3456", "999-222-111", "@juanmartinez");
        listaIndustriales.add(new Estudiante(dp4, da4, dc4, "Industrial"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestorEstudiantesGUI());
    }
}
