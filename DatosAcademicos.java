public class DatosAcademicos {
    private String codigo;
    private String carrera;
    private double promedio;
    private int ciclo;
    private String estado; // "Activo", "Inactivo", "Graduado"

    public DatosAcademicos(String codigo, String carrera, double promedio, int ciclo, String estado) {
        this.codigo = codigo;
        this.carrera = carrera;
        this.promedio = promedio;
        this.ciclo = ciclo;
        this.estado = estado;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    public int getCiclo() { return ciclo; }
    public void setCiclo(int ciclo) { this.ciclo = ciclo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Métodos de negocio
    public boolean esAprobado() {
        return promedio >= 11.0;
    }

    public String obtenerCondicion() {
        if (promedio >= 15) return "Excelente";
        else if (promedio >= 12) return "Bueno";
        else if (promedio >= 11) return "Aprobado";
        else return "Desaprobado";
    }

    @Override
    public String toString() {
        return "📚 DATOS ACADÉMICOS:\n" +
               "  Código: " + codigo + "\n" +
               "  Carrera: " + carrera + "\n" +
               "  Promedio: " + String.format("%.2f", promedio) + "\n" +
               "  Ciclo: " + ciclo + "\n" +
               "  Estado: " + estado + "\n" +
               "  Condición: " + obtenerCondicion();
    }
}
