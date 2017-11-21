package expense.fandi.cs.brandies.edu.expensehw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;

public class expense_add extends AppCompatActivity {
    Intent intent;
    EditText text_expense;
    EditText text_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add);

        intent = getIntent();
        cancel();
        save();
    }

    // if cancel is clicked, do nothing
    void cancel() {
        Button cancel = (Button)findViewById(R.id.botton_Cancen);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // read text from edit text, bind into intent, and set as result from view
    void save() {
        Button save  = (Button)findViewById(R.id.button_Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_expense = (EditText)findViewById(R.id.editText_Expense);
                text_note = (EditText)findViewById(R.id.editText_Note);
                try {
                    String expense = text_expense.getText().toString();
                    String note = text_note.getText().toString();
                    // handle invalid input: empty expense is disallowed
                    if (expense != null && expense.length() != 0) {
                        intent.putExtra("note", note);
                        intent.putExtra("expense", expense);
                        setResult(1, intent);
                    }
                } catch (Exception e) {
                    // if error, then directly finish and return
                }
                finish();
            }
        });
    }
}
