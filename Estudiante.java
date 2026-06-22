public class Estudiante implements Comparable<Estudiante> {
    // COMPOSICIÓN: El estudiante está compuesto por 3 objetos
    private DatosPersonales datosPersonales;
    private DatosAcademicos datosAcademicos;
    private DatosContacto datosContacto;
    private String tipo; // "Informático" o "Industrial"

    // Constructor que recibe los tres componentes
    public Estudiante(DatosPersonales datosPersonales, 
                     DatosAcademicos datosAcademicos, 
                     DatosContacto datosContacto,
                     String tipo) {
        this.datosPersonales = datosPersonales;
        this.datosAcademicos = datosAcademicos;
        this.datosContacto = datosContacto;
        this.tipo = tipo;
    }

    // Getters para acceder a los componentes
    public DatosPersonales getDatosPersonales() { return datosPersonales; }
    public DatosAcademicos getDatosAcademicos() { return datosAcademicos; }
    public DatosContacto getDatosContacto() { return datosContacto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    // Métodos de conveniencia (delegación)
    public String getNombreCompleto() {
        return datosPersonales.getNombreCompleto();
    }

    public boolean estaAprobado() {
        return datosAcademicos.esAprobado();
    }

    public String getCodigo() {
        return datosAcademicos.getCodigo();
    }

    // Implementación de Comparable para ordenar
    @Override
    public int compareTo(Estudiante otro) {
        int compararApellidos = this.datosPersonales.getApellidos()
                                 .compareTo(otro.datosPersonales.getApellidos());
        if (compararApellidos != 0) {
            return compararApellidos;
        }
        return this.datosPersonales.getNombres()
                 .compareTo(otro.datosPersonales.getNombres());
    }

    // Mostrar estado completo del estudiante
    public String mostrarEstadoCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════\n");
        sb.append("  🎓 ESTUDIANTE DE ").append(tipo.toUpperCase()).append("\n");
        sb.append("═══════════════════════════════════════════\n\n");
        sb.append(datosPersonales.toString()).append("\n\n");
        sb.append(datosAcademicos.toString()).append("\n\n");
        sb.append(datosContacto.toString()).append("\n\n");
        sb.append("📊 Resumen:\n");
        sb.append("  • Nombre completo: ").append(getNombreCompleto()).append("\n");
        sb.append("  • Estado académico: ").append(estaAprobado() ? "✅ Aprobado" : "❌ Desaprobado").append("\n");
        sb.append("  • Condición: ").append(datosAcademicos.obtenerCondicion()).append("\n");
        sb.append("═══════════════════════════════════════════");
        return sb.toString();
    }

    @Override
    public String toString() {
        return datosPersonales.getApellidos() + ", " + 
               datosPersonales.getNombres() + 
               " (" + datosAcademicos.getCodigo() + ") - " +
               datosAcademicos.getCarrera();
    }
}
