package nl.ploentuin.ploentuin.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import lombok.extern.slf4j.Slf4j;
import nl.ploentuin.ploentuin.dto.planner.PlannerInfoDto;
import nl.ploentuin.ploentuin.dto.planner.PlannerItemPlacementDto;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Slf4j
@Service
public class PlannerExportService {

    private final PlannerImageRendererHelper rendererHelper;

    public PlannerExportService(PlannerImageRendererHelper rendererHelper) {
        this.rendererHelper = rendererHelper;
    }

    public byte[] exportPdf(PlannerInfoDto planner, PlannerItemPlacementDto[][] grid, int cellSize) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = rendererHelper.render(planner, grid, cellSize);

            PdfWriter writer = new PdfWriter(baos);
            com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdf);

            ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", imgBytes);
            Image pdfImage = new Image(ImageDataFactory.create(imgBytes.toByteArray()));

            pdfImage.setAutoScale(true);

            document.add(pdfImage);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            log.error("PDF export failed for planner {}: {}", planner.getId(), e.getMessage());
            throw new RuntimeException("Could not generate PDF file", e);
        }
    }

    public byte[] exportWord(PlannerInfoDto planner, PlannerItemPlacementDto[][] grid, int cellSize) {
        try (XWPFDocument doc = new XWPFDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage image = rendererHelper.render(planner, grid, cellSize);
            ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", imgBytes);

            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();

            run.addPicture(new ByteArrayInputStream(imgBytes.toByteArray()),
                    XWPFDocument.PICTURE_TYPE_PNG, "planner.png",
                    Units.toEMU(image.getWidth()), Units.toEMU(image.getHeight()));

            doc.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("Word export failed for planner {}: {}", planner.getId(), e.getMessage());
            throw new RuntimeException("Could not generate Word file", e);
        }
    }

    public byte[] exportExcel(PlannerInfoDto planner, PlannerItemPlacementDto[][] grid, int cellSize) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage image = rendererHelper.render(planner, grid, cellSize);
            XSSFSheet sheet = workbook.createSheet("Planner");

            ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", imgBytes);
            int idx = workbook.addPicture(imgBytes.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG);

            var helper = workbook.getCreationHelper();
            var drawing = sheet.createDrawingPatriarch();
            var anchor = helper.createClientAnchor();

            anchor.setCol1(0);
            anchor.setRow1(0);

            var pic = drawing.createPicture(anchor, idx);
            pic.resize();

            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("Excel export failed for planner {}: {}", planner.getId(), e.getMessage());
            throw new RuntimeException("Could not generate Excel file", e);
        }
    }
}