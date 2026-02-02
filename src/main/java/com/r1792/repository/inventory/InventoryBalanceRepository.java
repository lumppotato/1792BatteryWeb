package com.r1792.repository.inventory;
import com.r1792.model.inventory.InventoryBalance;
import com.r1792.model.inventory.Item;
import com.r1792.model.inventory.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryBalanceRepository extends JpaRepository<InventoryBalance, Long> {

    Optional<InventoryBalance> findByItemAndLocation(Item item, Location location);
}