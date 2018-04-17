package kpi.tef.controller;

import kpi.tef.domain.Item;
import kpi.tef.domain.dto.ItemDto;
import kpi.tef.domain.dto.ItemResponse;
import kpi.tef.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping
    public ItemResponse getAllItems() {
        return getItemResponse();
    }

    @PostMapping(value = "/add")
    public ItemResponse addItem(@RequestBody ItemDto itemDto) {

        itemService.createItem(itemDto);

        return getItemResponse();
    }

    @PutMapping(value = "/update/{id}")
    public ItemResponse updateItem(@PathVariable long id, @RequestBody ItemDto itemDto) {

        itemService.updateItem(id, itemDto);

        return getItemResponse();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ItemResponse deleteItem(@PathVariable long id) {

        itemService.deleteItem(id);

        return getItemResponse();
    }

    private ItemResponse getItemResponse() {
        ItemResponse itemResponse = new ItemResponse();
        itemService.getAllItems().forEach(item -> itemResponse.getItemDtos().add(convertToDto(item)));
        return itemResponse;
    }

    private ItemDto convertToDto(Item item) {
        return new ItemDto().builder()
                .id(item.getId())
                .barcode(item.getBarcode())
                .description(item.getDescription())
                .build();
    }

}
