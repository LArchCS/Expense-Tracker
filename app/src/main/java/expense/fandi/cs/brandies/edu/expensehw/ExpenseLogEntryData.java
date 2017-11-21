package expense.fandi.cs.brandies.edu.expensehw;

/**
 * Created by difan on 11/3/2017.
 */

import java.util.Date;

// encapsulate a single expense log entry
public class ExpenseLogEntryData {
    String expense;
    String note;
    String date;

    public ExpenseLogEntryData(String expense, String note) {
        setExpense(expense);
        setNote(note);
        setDate();
    }

    public void setExpense(String s) {
        expense = s;
    }

    public void setNote(String s) {
        note = s;
    }

    public void setDate() {
        date = (new Date()).toString();
    }

    public String getExpense(){
        return expense;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        if (date == null) {
            setDate();
        }
        return date;
    }
}