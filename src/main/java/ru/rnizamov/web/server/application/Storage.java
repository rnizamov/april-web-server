package ru.rnizamov.web.server.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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

    public static Item getItemById(int id) {
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getId() == id) {
                return items.get(i);
            }
        }
        return null;
    }

    public static boolean deleteItemById(int id) {
        Iterator<Item> iterator = items.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public static void save(Item item) {
        item.setId((int) (Math.random()*Math.random()/Math.random()));
        logger.debug("Сохраняем item: " + item);
        items.add(item);
    }

    public static void updateItem(Item anotherItem) {
        items = items.stream().map(item -> {
           if (item.getId() == anotherItem.getId()) {
               item.setPrice(anotherItem.getPrice());
               item.setTitle(anotherItem.getTitle());
               logger.debug("Обновляем item: " + item);
           }
            return item;
        }).collect(Collectors.toList());
    }
}