package nl.ploentuin.servicetest;

import nl.ploentuin.ploentuin.dto.planner.PlannerItemCatalogDto;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import nl.ploentuin.ploentuin.repository.PlannerItemCatalogRepository;
import nl.ploentuin.ploentuin.service.PlannerItemCatalogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlannerItemCatalogServiceTest {

    @Mock
    private PlannerItemCatalogRepository catalogRepository;

    @InjectMocks
    private PlannerItemCatalogService service;

    @Test
    void testCreateItem() {
        PlannerItemCatalog item = new PlannerItemCatalog();
        item.setName("Tomato");
        when(catalogRepository.save(item)).thenReturn(item);

        var dto = service.createItem(item);

        assertNotNull(dto);
        assertEquals("Tomato", dto.getName());
        verify(catalogRepository, times(1)).save(item);
    }

    @Test
    void testGetAllItems() {
        PlannerItemCatalog item1 = new PlannerItemCatalog(); item1.setName("A");
        PlannerItemCatalog item2 = new PlannerItemCatalog(); item2.setName("B");
        when(catalogRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<?> items = service.getAllItems();

        assertEquals(2, items.size());
    }

    @Test
    void testGetItemsByType() {

        PlannerItemCatalog item = new PlannerItemCatalog();
        item.setName("A");
        item.setType(PlannerItemCatalog.PlannerItemType.VEGETABLES);
        when(catalogRepository.findAllByType(PlannerItemCatalog.PlannerItemType.VEGETABLES))
                .thenReturn(List.of(item));

        List<PlannerItemCatalogDto> items = service.getItemsByType(PlannerItemCatalog.PlannerItemType.VEGETABLES);

        assertEquals(1, items.size());
        assertEquals("A", items.get(0).getName()); // now DTO
    }


    @Test
    void testGetByIdFound() {
        PlannerItemCatalog item = new PlannerItemCatalog(); item.setId(1); item.setName("A");
        when(catalogRepository.findById(1)).thenReturn(Optional.of(item));

        var dto = service.getById(1);

        assertNotNull(dto);
        assertEquals("A", dto.getName());
    }

    @Test
    void testGetByIdNotFound() {
        when(catalogRepository.findById(99)).thenReturn(Optional.empty());

        var dto = service.getById(99);

        assertNull(dto);
    }

    @Test
    void testDeleteItemExists() {
        when(catalogRepository.existsById(1)).thenReturn(true);

        service.deleteItem(1);

        verify(catalogRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteItemNotExists() {
        when(catalogRepository.existsById(99)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.deleteItem(99));
    }
}
