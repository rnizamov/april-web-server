package ru.rnizamov.web.server.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Storage {
    private static final Logger logger = LogManager.getLogger(Storage.class.getName());
    private static List<Item> items;

    public static void init() {
        logger.info("Хранилище проинициализировано");
        items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Item item = new Item("item " + i, 100 + (int)(Math.random() * 1000));
            logger.trace("Генерируем новый Item: " + item);
            items.add(item);
        }
        logger.debug("В хранилище добавлены items");
    }

    public static List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public static void save(Item item) {
        item.setId(UUID.randomUUID());
        logger.debug("Сохраняем item: " + item);
        items.add(item);
    }

    public static void updateItem(Item anotherItem) {
        items = items.stream().map(item -> {
           if (item.getId().equals(anotherItem.getId())) {
               item.setPrice(anotherItem.getPrice());
               item.setTitle(anotherItem.getTitle());
               logger.debug("Обновляем item: " + item);
           }
            return item;
        }).collect(Collectors.toList());
    }
}