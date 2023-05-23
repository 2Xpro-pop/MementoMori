package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.Inventory;
import org.bayasik.erik.viewmodels.InventoryVM;

public class InventoryCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_INVENTORY)
    public void add(String name, int amount, int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, branchOfficeId);
        var inventory = new Inventory();
        inventory.setName(name);
        inventory.setAmount(amount);
        inventory.setBranchOfficeId(office);
        System.out.println("AddInventorySucc");
        em.persist(inventory);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.ADD_INVENTORY, inventory);
    }

    @Command(Commands.DELETE_INVENTORY)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var inventory = em.find(Inventory.class, id);
        em.remove(inventory);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.DELETE_INVENTORY, inventory);
    }

    @Command(Commands.UPDATE_INVENTORY)
    public void update(int id, String name, int amount, int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, branchOfficeId);
        var inventory = em.find(Inventory.class, id);
        inventory.setName(name);
        inventory.setAmount(amount);
        inventory.setBranchOfficeId(office);
        em.merge(inventory);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.UPDATE_INVENTORY, inventory);
    }

    @Command(Commands.GET_ALL_INVENTORY)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var inventories = em.createQuery("SELECT o FROM Inventory o", Inventory.class).getResultList();

        responser.jsonResponse(Commands.GET_ALL_INVENTORY, InventoryVM.fromCollection(inventories));
    }

    @Command(Commands.GET_INVENTORY_BY_ID)
    public void getInventoryById(int id) {
        var em = DependencyLoader.getEntityManager();
        var inventories = em.createQuery("SELECT o FROM Inventory o WHERE o.id = :inventoryId", Inventory.class).setParameter("inventoryId", id).getResultList();
        System.out.println("GetInventoryByIdSucc");
        System.out.println(inventories);
        responser.jsonResponse(Commands.GET_INVENTORY_BY_ID, new InventoryVM(inventories.get(0)));
    }

    @Command(Commands.GET_INVENTORY_BY_BRANCH_OFFICE)
    public void getInventoryByBranchOffice(int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();

        var branchOffice = new BranchOffice();
        branchOffice.setBranchOfficeId(branchOfficeId);

        var inventories = em.createQuery("SELECT o FROM Inventory o WHERE o.branchOfficeId = :branchOfficeId", Inventory.class).setParameter("branchOfficeId", branchOffice).getResultList();
        System.out.println("GetInventoryByIdSucc");
        System.out.println(inventories);
        responser.jsonResponse(Commands.GET_INVENTORY_BY_BRANCH_OFFICE, InventoryVM.fromCollection(inventories));
    }
}
