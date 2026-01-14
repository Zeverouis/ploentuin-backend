package nl.ploentuin.ploentuin.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import nl.ploentuin.ploentuin.dto.planner.PlannerInfoDto;
import nl.ploentuin.ploentuin.dto.planner.PlannerItemPlacementDto;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class PlannerExportService {

    private final PlannerImageRendererHelper rendererHelper;

    public PlannerExportService(PlannerImageRendererHelper rendererHelper) {
        this.rendererHelper = rendererHelper;
    }

    public byte[] exportPdf(PlannerInfoDto planner, PlannerItemPlacementDto[][] grid, int cellSize) {
        try {
            BufferedImage image = rendererHelper.render(planner, grid, cellSize);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdf);

            ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", imgBytes);
            Image pdfImage = new Image(ImageDataFactory.create(imgBytes.toByteArray()));

            document.add(pdfImage);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to export PDF", e);
        }
    }

    public byte[] exportWord(PlannerInfoDto planner, PlannerItemPlacementDto[][] grid, int cellSize) {
        try {
            BufferedImage image = rendererHelper.render(planner, grid, cellSize);

            try (XWPFDocument doc = new XWPFDocument();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                ImageIO.write(image, "PNG", imgBytes);

                XWPFParagraph p = doc.createParagraph();
                XWPFRun run = p.createRun();
                run.addPicture(new ByteArrayInputStream(imgBytes.toByteArray()),
                        XWPFDocument.PICTURE_TYPE_PNG, "planner.png",
                        image.getWidth(), image.getHeight());

                doc.write(baos);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to export Word", e);
        }
    }

    public byte[] exportExcel(PlannerInfoDto planner, PlannerItemPlacementDto[][] grid, int cellSize) {
        try {
            BufferedImage image = rendererHelper.render(planner, grid, cellSize);

            try (XSSFWorkbook workbook = new XSSFWorkbook();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                XSSFSheet sheet = workbook.createSheet("Planner");

                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                ImageIO.write(image, "PNG", imgBytes);
                int pictureIdx = workbook.addPicture(imgBytes.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG);

                var helper = workbook.getCreationHelper();
                var drawing = sheet.createDrawingPatriarch();
                var anchor = helper.createClientAnchor();
                anchor.setCol1(0);
                anchor.setRow1(0);
                drawing.createPicture(anchor, pictureIdx);

                workbook.write(baos);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to export Excel", e);
        }
    }
}
