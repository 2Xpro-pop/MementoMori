package org.bayasik.erik.commands;

import org.bayasik.DependencyLoader;
import org.bayasik.commands.Command;
import org.bayasik.commands.CommandHandler;
import org.bayasik.connection.ConnectionContext;
import org.bayasik.connection.Responser;
import org.bayasik.erik.models.BranchOffice;
import org.bayasik.erik.models.BudgetHistory;
import org.bayasik.erik.viewmodels.BudgetHistoryVM;

public class BudgetHistoryCH implements CommandHandler {

    private ConnectionContext context;
    private Responser responser;

    @Override
    public void setContext(ConnectionContext context) {
        this.context = context;
        responser = new Responser(context);
    }

    @Command(Commands.ADD_BUDGET_HISTORY)
    public void add(double action, String description, int BranchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, BranchOfficeId);
        var budgetHistory = new BudgetHistory();
        budgetHistory.setAction(action);
        budgetHistory.setDescription(description);
        budgetHistory.setBranchOfficeId(office);
        System.out.println("AddBudgetSucc");
        em.persist(budgetHistory);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.ADD_BUDGET_HISTORY, budgetHistory);
    }

    @Command(Commands.DELETE_BUDGET_HISTORY)
    public void delete(int id) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var budgetHistory = em.find(BudgetHistory.class, id);
        em.remove(budgetHistory);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.DELETE_BUDGET_HISTORY, budgetHistory);
    }

    @Command(Commands.UPDATE_BUDGET_HISTORY)
    public  void update(int id, double action, String description, int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();
        em.getTransaction().begin();
        var office = em.find(BranchOffice.class, branchOfficeId);
        var budgetHistory = em.find(BudgetHistory.class, id);
        budgetHistory.setAction(action);
        budgetHistory.setDescription(description);
        budgetHistory.setBranchOfficeId(office);
        em.merge(budgetHistory);
        em.getTransaction().commit();

        responser.notifyResponse(Commands.UPDATE_BUDGET_HISTORY, budgetHistory);
    }

    @Command(Commands.GET_ALL_BUDGET_HISTORY)
    public void getAll() {
        var em = DependencyLoader.getEntityManager();
        var budgetHistories = em.createQuery("SELECT o FROM BudgetHistory o", BudgetHistory.class).getResultList();
        System.out.println("GetAllHistorySucc");
        System.out.println(budgetHistories);
        responser.jsonResponse(Commands.GET_ALL_BUDGET_HISTORY, BudgetHistoryVM.fromCollection(budgetHistories));
    }

    @Command(Commands.GET_BUDGET_HISTORY_BY_ID)
    public void getBudgetHistoryById(int id) {
        var em = DependencyLoader.getEntityManager();
        var budgetHistories = em.createQuery("SELECT o FROM BudgetHistory o WHERE o.id = :budgetHistoryId", BudgetHistory.class).setParameter("budgetHistoryId", id).getResultList();
        System.out.println("GetBudgetByIdSucc");
        System.out.println(budgetHistories);
        responser.jsonResponse(Commands.GET_BUDGET_HISTORY_BY_ID, budgetHistories);
    }

    @Command(Commands.GET_BUDGET_HISTORY_BY_BRANCH_OFFICE)
    public void getBudgetHistoryByBranchOffice(int branchOfficeId) {
        var em = DependencyLoader.getEntityManager();

        var branchOffice = new BranchOffice();
        branchOffice.setBranchOfficeId(branchOfficeId);

        var budgetHistories = em.createQuery("SELECT o FROM BudgetHistory o WHERE o.branchOfficeId = :branchOfficeId", BudgetHistory.class).setParameter("branchOfficeId", branchOffice).getResultList();
        System.out.println("GetInventoryByIdSucc");
        System.out.println(budgetHistories);
        responser.jsonResponse(Commands.GET_BUDGET_HISTORY_BY_BRANCH_OFFICE, BudgetHistoryVM.fromCollection(budgetHistories));
    }
}
