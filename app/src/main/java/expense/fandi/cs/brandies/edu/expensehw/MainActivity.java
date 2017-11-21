package expense.fandi.cs.brandies.edu.expensehw;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static final String KEY_ID = DBOpenHelper.KEY_ID;
    static final String EXPENSE = DBOpenHelper.EXPENSE;
    static final String NOTE = DBOpenHelper.NOTE;
    static final String TIME = DBOpenHelper.TIME;

    final Context cxt = MainActivity.this;
    final MainActivity self = this;
    final DBOpenHelper db = new DBOpenHelper(cxt);
    ExpenseAdaptor adp;
    ListView mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainList = (ListView) findViewById( R.id.mainList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifyChange();
    }

    // add the "Add" option to the main menu as option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, Menu.FIRST, Menu.FIRST, "Add");
        return super.onCreateOptionsMenu(menu);
    }

    // once "Add" is selected, send intent and go to expense_add.class
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            startActivityForResult(new Intent(MainActivity.this,expense_add.class),1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // getting the result from activity by intent
    // add data from result into adapter
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == 1) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String note = bundle.getString("note");
                String expense = bundle.getString("expense");
                db.insertExpense(expense, note);
            }
        }
        notifyChange();
    }

    protected void notifyChange() {
        mainList = (ListView) findViewById( R.id.mainList);
        Cursor newCursor = db.getAllExpenses();
        int layout = R.layout.expense_entry;
        String[] from = {EXPENSE, NOTE, TIME};
        int[] to = {R.id.entryExpense, R.id.entryNote, R.id.entryDate};
        adp = new ExpenseAdaptor(cxt, layout, newCursor, from, to, 1);
        mainList.setAdapter(adp); // bind the adapter to mainList
    }

    public class ExpenseAdaptor extends SimpleCursorAdapter {
        Cursor c;
        public ExpenseAdaptor(Context context, int layout, Cursor c, String[] from, int[] to, int flag) {
            super(context,layout, c, from, to, flag);
            this.c = c;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);
            Button delete = (Button) view.findViewById(R.id.entryDelete);
            int entryId = c.getInt(c.getColumnIndex(KEY_ID));
            new ButtonIdBinder(delete, entryId);
        }
    }

    public class ButtonIdBinder {
        Button _b;
        int _i;
        public ButtonIdBinder(Button b, int id) {
            this._b = b;
            this._i = id;
            _b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteExpense(_i);
                    self.notifyChange();
                }
            });
        }
    }
}