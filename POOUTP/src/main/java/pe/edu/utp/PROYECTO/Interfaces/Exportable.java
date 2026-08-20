package pe.edu.utp.PROYECTO.Interfaces;

public interface Exportable {
    void exportarPDF(String tipoReporte);

    void exportarExcel(String tipoReporte);

    void exportarHTML(String tipoReporte);

}
