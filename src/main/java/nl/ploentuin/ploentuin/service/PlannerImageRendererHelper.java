package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.planner.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

@Service
public class PlannerImageRendererHelper {

    public BufferedImage render(
            PlannerInfoDto planner,
            PlannerItemPlacementDto[][] grid,
            int cellSizePx
    ) {
        int width = planner.getColumns() * cellSizePx;
        int height = planner.getRows() * cellSizePx;

        BufferedImage image = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        for (int r = 0; r < planner.getRows(); r++) {
            for (int c = 0; c < planner.getColumns(); c++) {

                int x = c * cellSizePx;
                int y = r * cellSizePx;

                PlannerItemPlacementDto placement = grid[r][c];

                if (placement != null) {
                    PlannerItemCatalogDto item = placement.getCatalogItem();

                    if (item.getColour() != null && !item.getColour().isBlank()) {
                        try {
                            g.setColor(Color.decode(item.getColour()));
                            g.fillRect(x, y, cellSizePx, cellSizePx);
                        } catch (Exception ignored) {}
                    }

                    if (item.getImageUrl() != null && !item.getImageUrl().isBlank()) {
                        try {
                            BufferedImage icon =
                                    ImageIO.read(new URL(item.getImageUrl()));
                            g.drawImage(
                                    icon,
                                    x,
                                    y,
                                    cellSizePx,
                                    cellSizePx,
                                    null
                            );
                        } catch (Exception ignored) {}
                    }
                }

                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSizePx, cellSizePx);
            }
        }

        g.dispose();
        return image;
    }
}
