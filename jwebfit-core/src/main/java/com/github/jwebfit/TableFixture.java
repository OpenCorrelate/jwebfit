package com.github.jwebfit;

import fit.RowFixture;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.html.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Fixture to validate data in Html tables.  To test a given table, a concrete subclass
 * of this fixture as well as a concrete subclass of a TableRow must be provided.
 *
 * @author Jim Weaver
 * @author Wilkes Joiner
 */
public abstract class TableFixture extends RowFixture {

    protected abstract String getSourceTableSummaryOrId();

    public Object[] query() throws Exception {
        Table sparseTable =  WebFixture.tester.getDialog().getTable(getSourceTableSummaryOrId());
        Row [] rowObjects = new Row[sparseTable.getRowCount() - getDataStartRow()];
        Row [] allRowObjects = (Row [] )( sparseTable.getRows().toArray() );
        int index = 0;
        for (int i = getDataStartRow(); i < sparseTable.getRowCount(); i++) {
            rowObjects[index] = buildTableRow(allRowObjects[i]);
            index ++;
        }
        return rowObjects;
    }

    /**
     * This is zero based
     * @return
     */
    protected int getDataStartRow() {
        return 2;
    }

    private Row buildTableRow(Row row) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor constructor = getTargetClass().getConstructor(new Class[] {String[].class});
        Cell [] cells = ( Cell [] ) ( row.getCells().toArray() );
        return (Row) constructor.newInstance(cells);
    }

}
