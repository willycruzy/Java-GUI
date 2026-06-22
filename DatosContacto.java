public class DatosContacto {
    private String email;
    private String telefono;
    private String celular;
    private String redesSociales;

    public DatosContacto(String email, String telefono, String celular, String redesSociales) {
        this.email = email;
        this.telefono = telefono;
        this.celular = celular;
        this.redesSociales = redesSociales;
    }

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getRedesSociales() { return redesSociales; }
    public void setRedesSociales(String redesSociales) { this.redesSociales = redesSociales; }

    @Override
    public String toString() {
        return "📱 DATOS DE CONTACTO:\n" +
               "  Email: " + email + "\n" +
               "  Teléfono: " + telefono + "\n" +
               "  Celular: " + celular + "\n" +
               "  Redes Sociales: " + redesSociales;
    }
}
