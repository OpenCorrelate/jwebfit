package com.github.jwebfit;

import fit.RowFixture;
import fit.Parse;

/**
 * fit RowFixture to validate html options for a select box.
 *
 * @author Jim Weaver
 */
public class OptionDisplay extends RowFixture {

    private String selectName;

    public void doRows(Parse rows) {
        selectName = rows.parts.text();
        super.doRows(rows.more);
    }

    public Class getTargetClass() {
        return Option.class;
    }

    public Object[] query() throws Exception  {
        String[] labels = WebFixture.tester.getDialog().getSelectedOptions(selectName);
        String[] values = WebFixture.tester.getDialog().getSelectOptionValues(selectName);
        return Option.buildOptions(labels, values);
    }

}
