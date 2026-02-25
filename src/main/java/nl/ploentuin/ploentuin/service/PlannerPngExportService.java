package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.planner.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class PlannerPngExportService {

    private final PlannerService plannerService;
    private final PlannerGridBuilderHelper gridBuilder;
    private final PlannerImageRendererHelper renderer;

    public PlannerPngExportService(
            PlannerService plannerService,
            PlannerGridBuilderHelper gridBuilder,
            PlannerImageRendererHelper renderer
    ) {
        this.plannerService = plannerService;
        this.gridBuilder = gridBuilder;
        this.renderer = renderer;
    }

    public byte[] export(int plannerId) {
        PlannerInfoDto planner = plannerService.getPlanner(plannerId);

        PlannerItemPlacementDto[][] grid =
                gridBuilder.buildGrid(planner);

        BufferedImage image =
                renderer.render(planner, grid, 80);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PNG export failed", e);
        }
    }
}
