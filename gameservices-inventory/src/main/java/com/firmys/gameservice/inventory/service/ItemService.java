package com.firmys.gameservice.inventory.service;

import com.firmys.gameservice.inventory.impl.Item;
import com.firmys.gameservice.inventory.impl.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item getPersonById(int id) {
        return itemRepository.findById(id).get();
    }

    public void saveOrUpdate(Item item) {
        itemRepository.save(item);
    }

    public void delete(int id) {
        itemRepository.deleteById(id);
    }

}
