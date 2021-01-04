/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respaldos;

import DTO.IngredientesReceta;
import DTO.Receta;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import com.itextpdf.text.List;

/**
 *
 * @author CRIS
 */
class GeneratePDFFileIText {

    // Definición de fuentes
    private Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
    private Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public GeneratePDFFileIText() {

    }

    public String createPDF(Receta receta) {
        // Creamos el documento e indicamos el nombre del fichero.

        String iTextExampleImage = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/img/recetas") + "/" + receta.getFoto();

        File pdfNewFile = new File("C:\\Users\\CRIS\\Downloads\\" + receta.getNombre().trim() + ".pdf");

        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                return "No se encontró el fichero para generar el pdf";
            }
            document.open();
            // Añadimos los metadatos del PDF
            document.addTitle(receta.getNombre());
            document.addSubject("Recetas");
            document.addKeywords("Receta," + receta.getTipoPlato() + "," + receta.getDificultad());
            document.addAuthor("FoodLab");
            document.addCreator(receta.getAutor().getNick());

            // Primera página 
            Chunk chunk = new Chunk(receta.getNombre(), chapterFont);

            // Creemos el primer capítulo...
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            //chapter.add(new DottedLineSeparator());
             Paragraph paragraphS = new Paragraph("Ingredientes");
            Section paragraphMoreS = chapter.addSection(paragraphS);
            // Listas por iText
            List list = new List(List.UNORDERED);

            for (Object o : receta.getIngredientesRecetaList()) {
                IngredientesReceta ir = (IngredientesReceta) o;
                ListItem item = new ListItem(ir.getCantidad() + " " + ir.getUnidadMedida() + " " + ir.getIngrediente1().getNombre());
                item.setAlignment(Element.ALIGN_LEFT);
                list.add(item);
            }
            paragraphMoreS.add(list);

          
            chapter.addSection("Preparación");
            chapter.add(new Paragraph(receta.getContenido(), paragraphFont));
            
            // chapter.add(new DottedLineSeparator());
            // Añadimos una imagen...
            Image image;
            try {
                image = Image.getInstance(iTextExampleImage);
                image.scaleAbsolute(250, 150);
               
               
                image.setAlignment(Image.ALIGN_CENTER);
                chapter.add(image);
            } catch (BadElementException ex) {
                Logger.getLogger(GeneratePDFFileIText.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GeneratePDFFileIText.class.getName()).log(Level.SEVERE, null, ex);
            }

            document.add(chapter);
            document.close();

            return "Se ha generado tu hoja PDF";
        } catch (DocumentException documentException) {
            return "Se ha producido un error al generar un documento";
        }
    }
}
