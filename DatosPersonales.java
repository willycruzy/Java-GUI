public class DatosPersonales {
    private String nombres;
    private String apellidos;
    private String domicilio;
    private String fechaNacimiento;

    public DatosPersonales(String nombres, String apellidos, String domicilio, String fechaNacimiento) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    // Método para obtener nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    @Override
    public String toString() {
        return "👤 DATOS PERSONALES:\n" +
               "  Nombres: " + nombres + "\n" +
               "  Apellidos: " + apellidos + "\n" +
               "  Domicilio: " + domicilio + "\n" +
               "  F. Nacimiento: " + fechaNacimiento;
    }
}
