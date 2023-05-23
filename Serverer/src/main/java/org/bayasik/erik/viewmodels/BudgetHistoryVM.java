package org.bayasik.erik.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bayasik.erik.models.BudgetHistory;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetHistoryVM {
    private int id;
    private double action;
    private String description;
    private int branchOfficeId;

    public BudgetHistoryVM(BudgetHistory budgetHistory) {
        this(budgetHistory.getId(),
                budgetHistory.getAction(),
                budgetHistory.getDescription(),
                budgetHistory.getBranchOfficeId().getBranchOfficeId());
    }

    public static ArrayList<BudgetHistoryVM> fromCollection(Collection<BudgetHistory> budgetHistories) {
        var list = new ArrayList<BudgetHistoryVM>();
        for (var budgetHistory : budgetHistories) list.add(new BudgetHistoryVM(budgetHistory));

        return list;
    }
}
