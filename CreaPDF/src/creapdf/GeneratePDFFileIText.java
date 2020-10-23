package creapdf;

import com.itextpdf.text.Anchor;
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
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import creapdf.Socio.Actividad;
import java.awt.Desktop;
import java.io.*; 
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 * Example of using the iText library to work with PDF documents on Java, 
 * lets you create, analyze, modify and maintain documents in this format.
 * Ejemplo de uso de la librería iText para trabajar con documentos PDF en Java, 
 * nos permite crear, analizar, modificar y mantener documentos en este formato.
 *
 * @author xules You can follow me on my website http://www.codigoxules.org/en
 * Puedes seguirme en mi web http://www.codigoxules.org
 */
public class GeneratePDFFileIText {
    
    static ArrayList socios = new ArrayList<>();
    // Fonts definitions (Definición de fuentes).
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    private static final String rutaImagen = "C:\\Users\\elena\\Desktop\\DAM CESUR\\A A Proyecto\\CreaPDF\\src\\creapdf\\logoMAS.png";
    
    /**
     * We create a PDF document with iText using different elements to learn 
     * to use this library.
     * Creamos un documento PDF con iText usando diferentes elementos para aprender 
     * a usar esta librería.
     * @param pdfNewFile  <code>String</code> 
     *      pdf File we are going to write. 
     *      Fichero pdf en el que vamos a escribir. 
     */
    public void createPDF(File pdfNewFile) {
        // We create the document and set the file name.        
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {

                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));

            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
            // We add metadata to PDF
            // Añadimos los metadatos del PDF
            document.addTitle("Table export to PDF (Exportamos la tabla a PDF)");
            document.addSubject("Using iText (usando iText)");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Código Xules");
            document.addCreator("Código Xules");
            
            // First page
            // Primera página 
            Chunk chunk = new Chunk("This is the title", chapterFont);
            chunk.setBackground(BaseColor.GRAY);
            // Let's create de first Chapter (Creemos el primer capítulo)
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph("This is the paragraph", paragraphFont));
            // We add an image (Añadimos una imagen)
            Image image;
            try {
                image = Image.getInstance(rutaImagen);  
                image.setAbsolutePosition(2, 150);
                image.scaleToFit(200, 100);
                chapter.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
            }
            document.add(chapter);
            
            // Second page - some elements
            // Segunda página - Algunos elementos
            Chapter chapSecond = new Chapter(new Paragraph(new Anchor("Some elements (Añadimos varios elementos)")), 1);
            Paragraph paragraphS = new Paragraph("Do it by Xules (Realizado por Xules)", subcategoryFont);
            
            // Underline a paragraph by iText (subrayando un párrafo por iText)
            Paragraph paragraphE = new Paragraph("This line will be underlined with a dotted line (Está línea será subrayada con una línea de puntos).");
            DottedLineSeparator dottedline = new DottedLineSeparator();
            dottedline.setOffset(-2);
            dottedline.setGap(2f);
            paragraphE.add(dottedline);
            chapSecond.addSection(paragraphE);
            
            Section paragraphMoreS = chapSecond.addSection(paragraphS);
            // List by iText (listas por iText)
            String text = "test 1 2 3 ";
            for (int i = 0; i < 5; i++) {
                text = text + text;
            }
            List list = new List(List.UNORDERED);
            ListItem item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            text = "a b c align ";
            for (int i = 0; i < 5; i++) {
                text = text + text;
            }
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            text = "supercalifragilisticexpialidocious ";
            for (int i = 0; i < 3; i++) {
                text = text + text;
            }
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            paragraphMoreS.add(list);
            document.add(chapSecond);
            
            // How to use PdfPTable
            // Utilización de PdfPTable
            
            // We use various elements to add title and subtitle
            // Usamos varios elementos para añadir título y subtítulo
            Anchor anchor = new Anchor("Table export to PDF (Exportamos la tabla a PDF)", categoryFont);
            anchor.setName("Table export to PDF (Exportamos la tabla a PDF)");            
            Chapter chapTitle = new Chapter(new Paragraph(anchor), 1);
            Paragraph paragraph = new Paragraph("Do it by Xules (Realizado por Xules)", subcategoryFont);
            Section paragraphMore = chapTitle.addSection(paragraph);
            paragraphMore.add(new Paragraph("This is a simple example (Este es un ejemplo sencillo)"));
            Integer numColumns = 6;
            Integer numRows = 120;
            // We create the table (Creamos la tabla).
            PdfPTable table = new PdfPTable(numColumns); 
            // Now we fill the PDF table 
            // Ahora llenamos la tabla del PDF
            PdfPCell columnHeader;
            // Fill table rows (rellenamos las filas de la tabla).                
            for (int column = 0; column < numColumns; column++) {
                columnHeader = new PdfPCell(new Phrase("COL " + column));
                columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(columnHeader);
            }
            table.setHeaderRows(1);
            // Fill table rows (rellenamos las filas de la tabla).                
            for (int row = 0; row < numRows; row++) {
                for (int column = 0; column < numColumns; column++) {
                    table.addCell("Row " + row + " - Col" + column);
                }
            }
            // We add the table (Añadimos la tabla)
            paragraphMore.add(table);
            // We add the paragraph with the table (Añadimos el elemento con la tabla).
            document.add(chapTitle);
            document.close();
            System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        inicializarSocios();
        GeneratePDFFileIText generatePDFFileIText = new GeneratePDFFileIText();
        generatePDFFileIText.crearPDF(socios);
        try {
            File path = new File("MASPDF.pdf");
            Desktop.getDesktop().open(path);
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void crearPDF(ArrayList<Socio> socios) {
    
        
        
        // We create the document and set the file name.        
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {

                PdfWriter.getInstance(document, new FileOutputStream(new File("MASPDF.pdf")));

            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
            
            // Añadimos los metadatos del PDF
            document.addTitle("Listado Socios PDF");
            document.addSubject("Seleccion personalizada");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Abraham Garrido");
            document.addCreator("Abraham Garrido");
            
              
            
            
             Image image;
            try {
                image = Image.getInstance(rutaImagen);  
                
                image.setAbsolutePosition(250, 800);
                image.scaleToFit(120, 50);
                
                document.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
            }
 
            Integer numColumns = 4;
            Integer numRows = 20;
            // We create the table (Creamos la tabla).
            PdfPTable table = new PdfPTable(numColumns); 
            //cabecera con nombres de atributos
            // primera
            PdfPCell cabecera = new PdfPCell(new Phrase("NOMBRE"));
            cabecera.setFixedHeight(30);
            cabecera.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecera.setBorder(Rectangle.NO_BORDER);
            //cabecera.setColspan(2);
            table.addCell(cabecera);
            // segunda fila
            cabecera = new PdfPCell(new Phrase("APELLIDOS"));
            cabecera.setFixedHeight(30);
            cabecera.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecera.setBorder(Rectangle.NO_BORDER);
            table.addCell(cabecera);
            //tercera
           
            cabecera = new PdfPCell(new Phrase("TELEFONO"));
            cabecera.setFixedHeight(30);
            cabecera.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecera.setBorder(Rectangle.NO_BORDER);
            table.addCell(cabecera);
            //cuarta
            
            cabecera = new PdfPCell(new Phrase("ACTIVIDAD"));
            cabecera.setFixedHeight(30);
            cabecera.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
            cabecera.setBorder(Rectangle.NO_BORDER);
            table.addCell(cabecera);
            
            table.setHeaderRows(1);
            
            // Ahora llenamos la tabla del PDF se añaden en orden col y fila
            // una vez hemos establecido que son 4 columnas
                       // rellenamos las filas de la tabla               
            for (Socio s : socios) {
                //nombre
                PdfPCell celda = new PdfPCell(new Phrase(s.getNombre()));
                celda.setFixedHeight(30);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBorder(Rectangle.NO_BORDER);
                table.addCell(celda);
                //apellidos
                celda = new PdfPCell(new Phrase(s.getApellidos()));
                celda.setFixedHeight(30);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBorder(Rectangle.NO_BORDER);
                table.addCell(celda);
                //actividad
                celda = new PdfPCell(new Phrase(s.getActividad().toString()));
                celda.setFixedHeight(30);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBorder(Rectangle.NO_BORDER);
                table.addCell(celda);
                //telefono
                celda = new PdfPCell(new Phrase(s.getTelefono()));
                celda.setFixedHeight(30);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBorder(Rectangle.NO_BORDER);
                table.addCell(celda);
                
            }//fin for each
            
 
            document.add(table);
            document.close();
            System.out.println("¡Se ha generado tu hoja PDF con los socios filtrados!");
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento : " + documentException);
        }
    
    
    }

    public static void inicializarSocios() {
        Socio s1 = new Socio("Abraham", "Garrido Rosillo",Actividad.MUAYTHAI, "622231221");
        Socio s2 = new Socio("Elena", "Gutierrez Barrios",Actividad.YOGA, "657856631");
        Socio s3 = new Socio("Jimena", "Garrido Gutierrez",Actividad.TAEKWONDO, "1");
        Socio s4 = new Socio("Pancho", "Garrido Gutierrez",Actividad.MUAYTHAI, "2");
        Socio s5 = new Socio("Jose", "Garrido Gutierrez", Actividad.MUAYTHAI, "3");
        socios = new ArrayList<Socio>();
        socios.add(s1);
        socios.add(s2);
        socios.add(s3);
        socios.add(s4);
        socios.add(s5);

    }
}
