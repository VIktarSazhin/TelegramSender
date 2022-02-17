package Handler.Creator;

import Handler.Convertor.JSConverter;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreatorPDF {
    public static File createPDF() {
        File file = new File("newPDF.pdf");
        try {
            PdfWriter pdfWriter = new PdfWriter("newPDF.pdf");
            float[] columnWidth = {200F, 100F, 200F};
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph("RED TEAM").setFontSize(50F)
                    .setFontColor(Color.RED);
            Paragraph date = new Paragraph(String.valueOf(formatter.format(new Date(System.currentTimeMillis()))))
                    .setFontSize(15F);
            document.add(paragraph).add(date);
            Table table = new Table(columnWidth);
            table.addCell(new Cell().add("User name"));
            table.addCell(new Cell().add("Spend time"));
            table.addCell(new Cell().add("Activities"));
            List<User> users = JSConverter.parse();
            for (User el : users
            ) {
                table.addCell(new Cell().add(el.getUser_name()))
                        .addCell(new Cell().add(el.getSpend_time()))
                        .addCell(new Cell().add(el.getActivities()));
            }
            document.add(table);
            document.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
