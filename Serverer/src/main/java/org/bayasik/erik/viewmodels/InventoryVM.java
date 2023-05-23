package org.bayasik.erik.viewmodels;

import lombok.*;
import org.bayasik.erik.models.Inventory;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryVM {
    private int id;
    private String name;
    private int amount;
    private int branchOfficeId;

    public InventoryVM(Inventory inventory){
        this(inventory.getId(),
                inventory.getName(),
                inventory.getAmount(),
                inventory.getBranchOfficeId().getBranchOfficeId());
    }

    public static ArrayList<InventoryVM> fromCollection(Collection<Inventory> inventories){
        var list = new ArrayList<InventoryVM>();
        for(var inventory : inventories) list.add(new InventoryVM(inventory));

        return list;
    }
}
