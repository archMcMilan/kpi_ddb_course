package kpi.tef.service.impl;

import kpi.tef.domain.Item;
import kpi.tef.domain.dto.ItemDto;
import kpi.tef.repository.ItemRepository;
import kpi.tef.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional
    public void createItem(ItemDto itemDto) {
        itemRepository.save(convertToItem(new Item(), itemDto));
    }

    @Override
    @Transactional
    public void updateItem(long id, ItemDto itemDto) {
        Optional<Item> itemOptinal = itemRepository.findById(id);
        if (itemOptinal.isPresent()) {
            itemRepository.save(convertToItem(itemOptinal.get(), itemDto));
        } else {
            System.out.println("there is no item with such id");
        }
    }

    @Override
    @Transactional
    public void deleteItem(long id) {
        itemRepository.delete(itemRepository.getOne(id));
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    private Item convertToItem(Item item, ItemDto itemDto) {
        if (itemDto.getBarcode() != null) {
            item.setBarcode(itemDto.getBarcode());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        return item;
    }
}
