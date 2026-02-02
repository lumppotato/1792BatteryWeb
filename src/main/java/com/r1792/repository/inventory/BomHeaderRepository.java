package com.r1792.repository.inventory;

import com.r1792.model.inventory.BomHeader;
import com.r1792.model.inventory.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BomHeaderRepository extends JpaRepository<BomHeader, Long> {

    Optional<BomHeader> findByParentItemAndActiveTrue(Item parentItem);
}