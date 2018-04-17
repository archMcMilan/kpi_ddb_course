package kpi.tef.service;


import kpi.tef.domain.Item;
import kpi.tef.domain.dto.ItemDto;

import java.util.List;

public interface ItemService {

    void createItem(ItemDto itemDto);

    void updateItem(long id, ItemDto itemDto);

    void deleteItem(long id);

    List<Item> getAllItems();
}
