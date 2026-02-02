package com.r1792.repository.inventory;

import com.r1792.model.inventory.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // use whatever your field name is on Item (I'm assuming itemCode)
    Optional<Item> findByItemCode(String itemCode);
}