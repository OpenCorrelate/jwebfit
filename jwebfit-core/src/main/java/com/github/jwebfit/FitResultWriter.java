/*
 * User: DJoiner
 * Date: Oct 8, 2002
 * Time: 11:10:09 AM
 */
package com.github.jwebfit;

import fit.Fixture;
import fit.Counts;

import java.io.File;

public abstract class FitResultWriter {
    private File output;

    FitResultWriter(File output) {
        this.output = output;
    }

    public abstract Counts getCounts();

    public abstract String getLinkString();
    public abstract String getDisplayName();
    public abstract void write();


    public boolean didFail() {
        return getWrong() > 0 || getExceptions() > 0;
    }

    public int getRight() {
        return getCounts().right;
    }

    public int getWrong() {
        return getCounts().wrong;
    }

    public int getIgnores() {
        return getCounts().ignores;
    }

    public int getExceptions() {
        return getCounts().exceptions;
    }

    public File getOutput() {
        return output;
    }
    
    /**
     * @return the total number of executed tests
     */
    public int getTotal() {
        return getRight() + getWrong() + getIgnores() + getExceptions();
    }
}
