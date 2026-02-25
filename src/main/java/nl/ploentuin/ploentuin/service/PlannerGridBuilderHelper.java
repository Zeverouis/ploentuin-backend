package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.planner.*;
import org.springframework.stereotype.Service;

@Service
public class PlannerGridBuilderHelper {

    public PlannerItemPlacementDto[][] buildGrid(PlannerInfoDto planner) {

        PlannerItemPlacementDto[][] grid =
                new PlannerItemPlacementDto[planner.getRows()][planner.getColumns()];

        if (planner.getItems() == null) {
            return grid;
        }

        for (PlannerItemPlacementDto item : planner.getItems()) {
            int r = item.getRow();
            int c = item.getColumn();

            if (r >= 0 && r < planner.getRows()
                    && c >= 0 && c < planner.getColumns()) {
                grid[r][c] = item;
            }
        }

        return grid;
    }
}
