package pe.edu.utp.PROYECTO.Servicio;

import pe.edu.utp.PROYECTO.AccesoDatos.ProyectoDAO;
import pe.edu.utp.PROYECTO.AccesoDatos.TareaDao;
import pe.edu.utp.PROYECTO.AccesoDatos.UsuarioDAO;
import pe.edu.utp.PROYECTO.Interfaces.Exportable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import pe.edu.utp.PROYECTO.modelo.Proyecto;
import pe.edu.utp.PROYECTO.modelo.Tarea;
import pe.edu.utp.PROYECTO.modelo.Usuario;
import java.awt.Desktop;
import java.io.File;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class GestorReportes implements Exportable {
    private UsuarioDAO usuarioDAO;
    private ProyectoDAO proyectoDAO;
    private TareaDao tareaDAO;

    public GestorReportes() {
        usuarioDAO = new UsuarioDAO();
        proyectoDAO = new ProyectoDAO();
        tareaDAO = new TareaDao();
    }

    @Override
    public void exportarPDF(String tipoReporte) {
        System.out.println("Entró a exportarPDF");
        System.out.println("Tipo recibido: " + tipoReporte);
        switch (tipoReporte.toUpperCase()) {

            case "USUARIOS":
                generarPDFUsuarios();
                break;

            case "PROYECTOS":
                generarPDFProyectos();
                break;

            case "TAREAS":
                generarPDFTareas();
                break;

            default:
                throw new IllegalArgumentException("Tipo de reporte no válido.");
        }
    }

    @Override
    public void exportarExcel(String tipoReporte) {
        switch (tipoReporte.toUpperCase()) {
            case "USUARIOS":
                generarExcelUsuarios();
                break;
            case "PROYECTOS":
                generarExcelProyectos();
                break;
            case "TAREAS":
                generarExcelTareas();
                break;
            default:
                throw new IllegalArgumentException("Tipo de reporte no válido.");
        }
    }

    @Override
    public void exportarHTML(String tipoReporte) {
        switch (tipoReporte.toUpperCase()) {
            case "USUARIOS":
                generarHTMLUsuarios();
                break;
            case "PROYECTOS":
                generarHTMLProyectos();
                break;
            case "TAREAS":
                generarHTMLTareas();
                break;
            default:
                throw new IllegalArgumentException("Tipo de reporte no válido.");
        }
    }

    private void generarPDFUsuarios() {
        System.out.println("Entró a generarPDFUsuarios");
        try {
            // Crear carpeta Reportes
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            // Crear documento
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("Reportes/Usuarios.pdf"));
            documento.open();
            // TÍTULO PRINCIPAL
            Font titulo = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    18
            );
            Paragraph encabezado = new Paragraph(
                    "SISTEMA DE GESTIÓN DE PROYECTOS",
                    titulo
            );
            encabezado.setAlignment(Element.ALIGN_CENTER);
            documento.add(encabezado);
            documento.add(new Paragraph(" "));
            // SUBTÍTULO
            Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15
            );
            Paragraph reporte = new Paragraph("REPORTE DE USUARIOS", subtitulo
            );

            reporte.setAlignment(Element.ALIGN_CENTER);
            documento.add(reporte);
            documento.add(new Paragraph(" "));
            // FECHA Y HORA
            documento.add(new Paragraph("Fecha: " + LocalDate.now()));
            documento.add(new Paragraph("Hora: " + LocalTime.now().withNano(0)));
            documento.add(new Paragraph(" "));
            // TABLA
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.addCell("DNI");
            tabla.addCell("Nombre");
            tabla.addCell("Apellido");
            tabla.addCell("Email");
            tabla.addCell("Teléfono");
            // DATOS
            List<Usuario> usuarios = usuarioDAO.listarUsuario();
            System.out.println("Cantidad de usuarios: " + usuarios.size());
            for (Usuario usuario : usuarios) {
                tabla.addCell(String.valueOf(usuario.getDni()));
                tabla.addCell(usuario.getNombre());
                tabla.addCell(usuario.getApellido());
                tabla.addCell(usuario.getEmail());
                tabla.addCell(usuario.getTelefono());
            }
            documento.add(tabla);
            documento.close();
            File archivo = new File("Reportes/Usuarios.pdf");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
            System.out.println(archivo.getAbsolutePath());
            System.out.println(archivo.exists());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void generarPDFProyectos() {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("Reportes/Proyectos.pdf"));
            documento.open();
            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph encabezado = new Paragraph("SISTEMA DE GESTIÓN DE PROYECTOS", titulo);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            documento.add(encabezado);
            documento.add(new Paragraph(" "));
            Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
            Paragraph reporte = new Paragraph("REPORTE DE PROYECTOS", subtitulo);
            reporte.setAlignment(Element.ALIGN_CENTER);
            documento.add(reporte);
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Fecha: " + LocalDate.now()));
            documento.add(new Paragraph("Hora: " + LocalTime.now().withNano(0)));
            documento.add(new Paragraph(" "));
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.addCell("Nombre");
            tabla.addCell("Descripción");
            tabla.addCell("Inicio");
            tabla.addCell("Fin");
            tabla.addCell("Estado");
            List<Proyecto> proyectos = proyectoDAO.listarProyectos();
            for (Proyecto proyecto : proyectos) {
                tabla.addCell(proyecto.getNombre());
                tabla.addCell(proyecto.getDescripcion());
                tabla.addCell(proyecto.getFechaInicio().toString());
                tabla.addCell(proyecto.getFechaFin().toString());
                tabla.addCell(proyecto.getEstadoProyecto());
            }
            documento.add(tabla);
            documento.close();
            File archivo = new File("Reportes/Proyectos.pdf");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void generarPDFTareas() {

        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("Reportes/Tareas.pdf"));
            documento.open();
            Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph encabezado = new Paragraph("SISTEMA DE GESTIÓN DE PROYECTOS", titulo);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            documento.add(encabezado);
            documento.add(new Paragraph(" "));
            Font subtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
            Paragraph reporte = new Paragraph("REPORTE DE TAREAS", subtitulo);
            reporte.setAlignment(Element.ALIGN_CENTER);
            documento.add(reporte);
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Fecha: " + LocalDate.now()));
            documento.add(new Paragraph("Hora: " + LocalTime.now().withNano(0)));
            documento.add(new Paragraph(" "));
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.addCell("Título");
            tabla.addCell("Descripción");
            tabla.addCell("Estado");
            tabla.addCell("Prioridad");
            tabla.addCell("Inicio");
            tabla.addCell("Fin");
            tabla.addCell("Proyecto");
            List<Tarea> tareas = tareaDAO.listarTareas();
            for (Tarea tarea : tareas) {
                tabla.addCell(tarea.getTitulo());
                tabla.addCell(tarea.getDescripcion());
                tabla.addCell(tarea.getEstadoTarea());
                tabla.addCell(tarea.getPrioridad());
                tabla.addCell(tarea.getFechaInicio().toString());
                tabla.addCell(tarea.getFechaFin().toString());
                tabla.addCell(String.valueOf(tarea.getIdProyecto()));
            }
            documento.add(tabla);
            documento.close();
            File archivo = new File("Reportes/Tareas.pdf");
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generarExcelUsuarios() {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            XSSFWorkbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Usuarios");
            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("DNI");
            encabezado.createCell(1).setCellValue("Nombre");
            encabezado.createCell(2).setCellValue("Apellido");
            encabezado.createCell(3).setCellValue("Email");
            encabezado.createCell(4).setCellValue("Teléfono");
            List<Usuario> usuarios = usuarioDAO.listarUsuario();
            int fila = 1;
            for (Usuario usuario : usuarios) {
                Row row = hoja.createRow(fila++);
                row.createCell(0).setCellValue(usuario.getDni());
                row.createCell(1).setCellValue(usuario.getNombre());
                row.createCell(2).setCellValue(usuario.getApellido());
                row.createCell(3).setCellValue(usuario.getEmail());
                row.createCell(4).setCellValue(usuario.getTelefono());
            }
            for (int i = 0; i < 5; i++) {
                hoja.autoSizeColumn(i);
            }
            File archivo = new File("Reportes/Usuarios.xlsx");
            FileOutputStream salida = new FileOutputStream(archivo);
            libro.write(salida);
            salida.close();
            libro.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void generarExcelProyectos() {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            XSSFWorkbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Proyectos");
            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("Nombre");
            encabezado.createCell(1).setCellValue("Descripción");
            encabezado.createCell(2).setCellValue("Fecha Inicio");
            encabezado.createCell(3).setCellValue("Fecha Fin");
            encabezado.createCell(4).setCellValue("Estado");
            List<Proyecto> proyectos = proyectoDAO.listarProyectos();
            int fila = 1;
            for (Proyecto proyecto : proyectos) {
                Row row = hoja.createRow(fila++);
                row.createCell(0).setCellValue(proyecto.getNombre());
                row.createCell(1).setCellValue(proyecto.getDescripcion());
                row.createCell(2).setCellValue(proyecto.getFechaInicio().toString());
                row.createCell(3).setCellValue(proyecto.getFechaFin().toString());
                row.createCell(4).setCellValue(proyecto.getEstadoProyecto());

            }
            for (int i = 0; i < 5; i++) {
                hoja.autoSizeColumn(i);
            }
            File archivo = new File("Reportes/Proyectos.xlsx");
            FileOutputStream salida = new FileOutputStream(archivo);
            libro.write(salida);
            salida.close();
            libro.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void generarExcelTareas() {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            XSSFWorkbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Tareas");
            Row encabezado = hoja.createRow(0);
            encabezado.createCell(0).setCellValue("Título");
            encabezado.createCell(1).setCellValue("Descripción");
            encabezado.createCell(2).setCellValue("Estado");
            encabezado.createCell(3).setCellValue("Prioridad");
            encabezado.createCell(4).setCellValue("Fecha Inicio");
            encabezado.createCell(5).setCellValue("Fecha Fin");
            encabezado.createCell(6).setCellValue("Proyecto");
            List<Tarea> tareas = tareaDAO.listarTareas();
            int fila = 1;
            for (Tarea tarea : tareas) {
                Row row = hoja.createRow(fila++);
                row.createCell(0).setCellValue(tarea.getTitulo());
                row.createCell(1).setCellValue(tarea.getDescripcion());
                row.createCell(2).setCellValue(tarea.getEstadoTarea());
                row.createCell(3).setCellValue(tarea.getPrioridad());
                row.createCell(4).setCellValue(tarea.getFechaInicio().toString());
                row.createCell(5).setCellValue(tarea.getFechaFin().toString());
                row.createCell(6).setCellValue(tarea.getIdProyecto());

            }
            for (int i = 0; i < 7; i++) {
                hoja.autoSizeColumn(i);
            }
            File archivo = new File("Reportes/Tareas.xlsx");
            FileOutputStream salida = new FileOutputStream(archivo);
            libro.write(salida);
            salida.close();
            libro.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void generarHTMLUsuarios() {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            File archivo = new File("Reportes/Usuarios.html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write("<!DOCTYPE html>");
            writer.newLine();
            writer.write("<html>");
            writer.newLine();
            writer.write("<head>");
            writer.newLine();
            writer.write("<meta charset='UTF-8'>");
            writer.newLine();
            writer.write("<title>Reporte de Usuarios</title>");
            writer.newLine();
            writer.write("</head>");
            writer.newLine();
            writer.write("<body>");
            writer.newLine();
            writer.write("<h1>REPORTE DE USUARIOS</h1>");
            writer.newLine();
            writer.write("<table border='1'>");
            writer.newLine();
            writer.write("<tr>");
            writer.write("<th>DNI</th>");
            writer.write("<th>Nombre</th>");
            writer.write("<th>Apellido</th>");
            writer.write("<th>Email</th>");
            writer.write("<th>Teléfono</th>");
            writer.write("</tr>");
            writer.newLine();
            List<Usuario> usuarios = usuarioDAO.listarUsuario();
            for (Usuario usuario : usuarios) {
                writer.write("<tr>");
                writer.write("<td>" + usuario.getDni() + "</td>");
                writer.write("<td>" + usuario.getNombre() + "</td>");
                writer.write("<td>" + usuario.getApellido() + "</td>");
                writer.write("<td>" + usuario.getEmail() + "</td>");
                writer.write("<td>" + usuario.getTelefono() + "</td>");
                writer.write("</tr>");
                writer.newLine();
            }
            writer.write("</table>");
            writer.newLine();
            writer.write("</body>");
            writer.newLine();
            writer.write("</html>");
            writer.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generarHTMLProyectos() {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            File archivo = new File("Reportes/Proyectos.html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write("<!DOCTYPE html>");
            writer.newLine();
            writer.write("<html>");
            writer.newLine();
            writer.write("<head>");
            writer.newLine();
            writer.write("<meta charset='UTF-8'>");
            writer.newLine();
            writer.write("<title>Reporte de Proyectos</title>");
            writer.newLine();
            writer.write("</head>");
            writer.newLine();
            writer.write("<body>");
            writer.newLine();
            writer.write("<h1>REPORTE DE PROYECTOS</h1>");
            writer.newLine();
            writer.write("<table border='1'>");
            writer.newLine();
            writer.write("<tr>");
            writer.write("<th>Nombre</th>");
            writer.write("<th>Descripción</th>");
            writer.write("<th>Fecha Inicio</th>");
            writer.write("<th>Fecha Fin</th>");
            writer.write("<th>Estado</th>");
            writer.write("</tr>");
            writer.newLine();
            List<Proyecto> proyectos = proyectoDAO.listarProyectos();
            for (Proyecto proyecto : proyectos) {
                writer.write("<tr>");
                writer.write("<td>" + proyecto.getNombre() + "</td>");
                writer.write("<td>" + proyecto.getDescripcion() + "</td>");
                writer.write("<td>" + proyecto.getFechaInicio() + "</td>");
                writer.write("<td>" + proyecto.getFechaFin() + "</td>");
                writer.write("<td>" + proyecto.getEstadoProyecto() + "</td>");
                writer.write("</tr>");
                writer.newLine();
            }
            writer.write("</table>");
            writer.newLine();
            writer.write("</body>");
            writer.newLine();
            writer.write("</html>");
            writer.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void generarHTMLTareas() {
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            File archivo = new File("Reportes/Tareas.html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write("<!DOCTYPE html>");
            writer.newLine();
            writer.write("<html>");
            writer.newLine();
            writer.write("<head>");
            writer.newLine();
            writer.write("<meta charset='UTF-8'>");
            writer.newLine();
            writer.write("<title>Reporte de Tareas</title>");
            writer.newLine();
            writer.write("</head>");
            writer.newLine();
            writer.write("<body>");
            writer.newLine();
            writer.write("<h1>REPORTE DE TAREAS</h1>");
            writer.newLine();
            writer.write("<table border='1'>");
            writer.newLine();
            writer.write("<tr>");
            writer.write("<th>Título</th>");
            writer.write("<th>Descripción</th>");
            writer.write("<th>Estado</th>");
            writer.write("<th>Prioridad</th>");
            writer.write("<th>Fecha Inicio</th>");
            writer.write("<th>Fecha Fin</th>");
            writer.write("<th>Proyecto</th>");
            writer.write("</tr>");
            writer.newLine();
            List<Tarea> tareas = tareaDAO.listarTareas();
            for (Tarea tarea : tareas) {
                writer.write("<tr>");
                writer.write("<td>" + tarea.getTitulo() + "</td>");
                writer.write("<td>" + tarea.getDescripcion() + "</td>");
                writer.write("<td>" + tarea.getEstadoTarea() + "</td>");
                writer.write("<td>" + tarea.getPrioridad() + "</td>");
                writer.write("<td>" + tarea.getFechaInicio() + "</td>");
                writer.write("<td>" + tarea.getFechaFin() + "</td>");
                writer.write("<td>" + tarea.getIdProyecto() + "</td>");
                writer.write("</tr>");
                writer.newLine();
            }
            writer.write("</table>");
            writer.newLine();
            writer.write("</body>");
            writer.newLine();
            writer.write("</html>");
            writer.close();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(archivo.toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
