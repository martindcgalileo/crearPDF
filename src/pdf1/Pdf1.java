/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pdf1;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.ImageIcon;

/**
 *
 * @author martin
 */
public class Pdf1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileOutputStream pdfPrueba = null;
        Connection conexion;
        Statement sentencia;
        ResultSet resultado;
        try {
            // Se crea el documento
            Document documento = new Document();
            // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream("fichero.pdf");
            /* 
            * Se asocia el documento al OutputStream y se indica que el espaciado entre
            * lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
             */
            PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
            // Se abre el documento.
            documento.open();
            //Añadir párrafos
            documento.add(new Paragraph("Clasificacion ATP",
                    FontFactory.getFont("arial", // fuente
                            22, // tamaño
                            Font.ITALIC, // estilo
                            BaseColor.BLUE))); // color
            documento.add(new Paragraph(
                    "\nEl ranking ATP es una clasificación mundial de tenistas profesionales de la Asociación "
                    + "de Tenistas Profesionales. Se actualiza cada semana y abarca los resultados de las últimas 52 semanas. "
                    + "Se utiliza para seleccionar a los habilitados en cada torneo y a los cabezas de serie, el máximo "
                    + "galardón para cualquier tenista es ser considerado entre los 5 mejores del mundo en el ranking ATP.\n"));
            //Añadir foto
            Image foto = Image.getInstance("src/img/atp.png");
            foto.scaleToFit(100, 100);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);
            documento.add(new Paragraph("\n\n"));
            conexion = null;
            conexion=bd.Conexion.mySQL("bdagenda2", "root", "");
            if (conexion == null) {
                System.out.println("No hay conexion");
                System.exit(0);
            }
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM tagenda");
            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Nombre");
            tabla.addCell("Fecha nacimiento");         
            tabla.addCell("Fotografía");
            while (resultado.next()) {
                
                tabla.addCell(resultado.getString("nombre"));
                   tabla.addCell(resultado.getString("fecha_nac"));
                   byte[] bytes=resultado.getBytes("foto");
                   foto=Image.getInstance(bytes);
                   tabla.addCell(foto);
            }
            documento.add(tabla);
             documento.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pdf1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Pdf1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pdf1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Pdf1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pdfPrueba.close();
            } catch (IOException ex) {
                Logger.getLogger(Pdf1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                
            }
        }
    }
    
}
