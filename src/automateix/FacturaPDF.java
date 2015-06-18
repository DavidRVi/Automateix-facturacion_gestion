/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automateix;

import automateix.vista.JButtonTableExample;
import automateix.vista.VentanaFacturacion;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.portable.ApplicationException;

/**
 *
 * @author David
 */
public class FacturaPDF {
    
    private VentanaFacturacion factura;
    private Cliente cli;
    private int numero;
    /**
     * Creates a PDF file: hello_memory.pdf
     * 
     */
    public FacturaPDF(VentanaFacturacion listaArt){
            factura = listaArt;
            cli = factura.getCliente();
            numero = factura.getTabla().getFactura().getNumero();
            numero--;
    }
    
    
    
    public void WriteFile()
    {
        try {
            
            //Creamos el archivo en memoria
          
            String path = "facturas/"+factura.getYear()+"/"+factura.getMonth()+"/"+factura.getDay();
            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();
            File file = new File(path + "/" + numero + ".pdf");
            //file.mkdir();
            
            //Para guardarlo en disco
            FileOutputStream fileout = new FileOutputStream(file);
            
            Document doc = new Document();
            
            //Enlazamos el archivo de memoria con el de disco
            PdfWriter.getInstance(doc, fileout);
 
            doc.addTitle("Factura AUTOMATEIX");
            doc.addAuthor("Automateix");
            
            doc.open();
            Image logo = Image.getInstance("logoAE.png");
            
            
            PdfPTable table = new PdfPTable(2);
            
            table.setWidthPercentage(100);
            
            table.setWidths(new int[]{1, 2});
            
            table.addCell(createImageCell(logo));
            table.addCell(createTextCell("AUTOMATEIX" + "\nCTRA DE SABADELL, KM.12700"+"\n08191 RUBI"+"\nTEL: 93170595"+"\nautomateix@hotmail.com"+"\nwww.automateix.es"));
            doc.add(table);
            //doc.add(logo);
            
            DecimalFormat formato = new DecimalFormat("######.00");
            formato.setRoundingMode(RoundingMode.CEILING);
            
            Paragraph p = new Paragraph();
            
            p.setIndentationLeft(50);
            p.setIndentationRight(50);
            p.add("Factura nº: " + Integer.toString(factura.getYear()) +"/"+ numero + "                         ");
            p.add(Chunk.TABBING);
            p.add(Chunk.TABBING);
            p.add(Chunk.TABBING);
            p.add("Fecha: " + factura.getFecha());
            p.add(Chunk.NEWLINE);
            
            p.add("Cliente:  " + cli.getNombre() + " " + cli.getApellidos());
            p.add(Chunk.NEWLINE);
            p.add("Direccion: " + cli.getDireccion());
            p.add(Chunk.NEWLINE);
            p.add("DNI: " + cli.getDNI());
            p.add(Chunk.NEWLINE);
            p.add("Matricula: " + factura.getMatricula() + "                                             " + "KM: " + factura.getKM());
            p.add(Chunk.NEWLINE);
            p.add(Chunk.NEWLINE);
            p.add("Articulos: ");
            p.add(Chunk.NEWLINE);
            doc.add(p);
            table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setHeaderRows(1);
            
            table.addCell("Descripcion");
            table.addCell("Precio ud.");
            table.addCell("Cantidad");
            table.addCell("Importe");
            //p.add("Descripcion                      Precio ud.                  Cantidad                    Importe");
           // p.add(Chunk.NEWLINE);
            for(int i=0; i< factura.getTabla().getFactura().getSize(); i++)
            {
                //p.add()
                Articulo a = factura.getTabla().getFactura().getArticulo(i);
                table.addCell(a.getNombre());
                table.addCell(formato.format(a.getPrecio()) + " €");
                table.addCell(Integer.toString(a.getCantidad()));
                table.addCell((formato.format(a.getPrecioTotal())) +" €");
            }
            
            table.addCell(createTextCell("Sub-Total"));
            table.addCell(createTextCell(""));
            table.addCell(createTextCell(""));
            double valor = factura.getTabla().getFactura().getPrecioTotal() * (factura.getIVA() / 100.0);
            table.addCell(createTextCell(formato.format(factura.getTabla().getFactura().getPrecioTotal() - valor) + " €"));
            table.addCell(createTextCell("IVA"));
            table.addCell(createTextCell(Integer.toString(factura.getIVA())+"%"));
            table.addCell(createTextCell(""));
            
            
            //formato.format
            table.addCell(createTextCell(formato.format(valor) + " €"));
            table.addCell(createTextCell("Total"));
            table.addCell(createTextCell(""));
            table.addCell(createTextCell(""));
            table.addCell(createTextCell(formato.format(factura.getTabla().getFactura().getPrecioTotal()) + " €"));
            
            doc.add(table);
            doc.close();
            
            
            
            
            
            
            //////////////----------------- TICKET -------------------//////////////////
            Rectangle pageSize = new Rectangle(0.0f, 0.0f, 360.0f, 720.0f);
            /*Document ticket = new Document(pageSize);
            doc.open();
            doc.add(new Chunk("Hola Mundo"));
            doc.close();*/
            Document ticket = new Document(pageSize);
           
            File ticketpath = new File("tickets");
            ticketpath.mkdirs();
            File ticketFile = new File("tickets/"+ numero+".pdf");
            
            FileOutputStream fileoutticket = new FileOutputStream(ticketFile);
            PdfWriter.getInstance(ticket, fileoutticket);
            
            ticket.open();
            Font cs = FontFactory.getFont("HELVETICA", 30, Font.BOLD);
           
            Paragraph automateix = new Paragraph(16,"AUTOMATEIX", cs);
            
            automateix.setAlignment(Element.ALIGN_CENTER);
            
            
            Paragraph title = new Paragraph();
            title.setAlignment(Element.ALIGN_CENTER);
            title.add(new Chunk(Chunk.NEWLINE));
            title.add("CTRA. DE SABADELL, 213. NAVE 5");
            title.add(new Chunk(Chunk.NEWLINE));
            title.add("RUBI (08191) - TEL.: 93.517.05.95");
            title.add(new Chunk(Chunk.NEWLINE));
            
            title.add("Ticket: " + numero + "      " + factura.getFecha());
            title.add(Chunk.NEWLINE);
            title.add(Chunk.NEWLINE);
            ticket.add(automateix);
            ticket.add(title);
            
            
            ////////////----TICKET TABLE-----////////////////
            
            Font titleTicketFont = FontFactory.getFont("COURIER", 13);
            Font tableTicketFont = FontFactory.getFont("COURIER", 15);
            PdfPTable tableTicket = new PdfPTable(3);
            tableTicket.setWidthPercentage(100);
            tableTicket.setHeaderRows(1);
            
            tableTicket.addCell(new Phrase("Descripcion", titleTicketFont));
            //tableTicket.addCell(new Phrase("Precio ud.", titleTicketFont));
            tableTicket.addCell(new Phrase("Cantidad", titleTicketFont));
            tableTicket.addCell(new Phrase("Importe", titleTicketFont));
            //p.add("Descripcion                      Precio ud.                  Cantidad                    Importe");
           // p.add(Chunk.NEWLINE);
            for(int i=0; i< factura.getTabla().getFactura().getSize(); i++)
            {
                //p.add()
                Articulo a = factura.getTabla().getFactura().getArticulo(i);
                tableTicket.addCell(new Phrase(a.getNombre(), tableTicketFont));
                //tableTicket.addCell(new Phrase(formato.format(a.getPrecio()) + " €", tableTicketFont));
                tableTicket.addCell(new Phrase(Integer.toString(a.getCantidad()), tableTicketFont));
                tableTicket.addCell(new Phrase(formato.format(a.getPrecioTotal()) + " €", tableTicketFont));
            }
            tableTicket.addCell(createTextCell("Sub-Total", tableTicketFont));
            
            tableTicket.addCell(createTextCell(""));
            tableTicket.addCell(createTextCell(formato.format(factura.getTabla().getFactura().getPrecioTotal() - valor) + " €", tableTicketFont));
            tableTicket.addCell(createTextCell("IVA", tableTicketFont));
            tableTicket.addCell(createTextCell(Integer.toString(factura.getIVA())+"%",tableTicketFont));
            
            
            
            //formato.format
            tableTicket.addCell(createTextCell(formato.format(valor) + " €", tableTicketFont));
            tableTicket.addCell(createTextCell("Total", tableTicketFont));
            
            tableTicket.addCell(createTextCell(""));
            tableTicket.addCell(createTextCell(formato.format(factura.getTabla().getFactura().getPrecioTotal()) + " €", tableTicketFont));
            
            
            ticket.add(tableTicket);
            Paragraph fin = new Paragraph();
            
            fin.setAlignment(Element.ALIGN_CENTER);
            fin.add(new Chunk(Chunk.NEWLINE));
            
            fin.add("www.automateix.es");
            fin.add(new Chunk(Chunk.NEWLINE));
            fin.add("GRACIAS POR SU VISITA");
            ticket.add(fin);
            ticket.close();
            Desktop.getDesktop().open(ticketFile);
            
            
            
            
        } catch (DocumentException ex) {
            Logger.getLogger(PdfWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public static PdfPCell createImageCell(Image img) throws DocumentException, IOException {
        
        PdfPCell cell = new PdfPCell(img, false);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
    
    public static PdfPCell createTextCell(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text );
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
    
    public static PdfPCell createTextCell(String text, Font f) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text,f);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
