import java.util.Objects;

public class Alumno implements Comparable<Alumno> {
    private String codigo;
    private String apellidos;
    private String nombres;
    private String domicilio;

    // Constructor
    public Alumno(String codigo, String apellidos, String nombres, String domicilio) {
        this.codigo = codigo;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.domicilio = domicilio;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    // Para ordenar por Apellidos y luego Nombres
    @Override
    public int compareTo(Alumno otro) {
        int compararApellidos = this.apellidos.compareTo(otro.apellidos);
        if (compararApellidos != 0) {
            return compararApellidos;
        }
        return this.nombres.compareTo(otro.nombres);
    }

    // Para mostrar los datos en el JTextArea
    @Override
    public String toString() {
        return "Código: " + codigo + 
               "\nApellidos: " + apellidos + 
               "\nNombres: " + nombres + 
               "\nDomicilio: " + domicilio + 
               "\n-----------------------------";
    }
}
