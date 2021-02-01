package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {

        Item item = new Item();
        item.setName("노트북");
        item.setPrice(100000);
        item.setContent("삼성 노트북");

        Item newItem = itemRepository.save(item);
        Assertions.assertNotNull(newItem);


    }

    @Test
    public void read(){

        Long id = 3L;

        Optional<Item> item = itemRepository.findById(id);

        item.ifPresent(i -> {
            System.out.println(i);
        });

        Assert.assertTrue(item.isPresent());

    }
}
