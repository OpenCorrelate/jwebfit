/*
 * User: djoiner
 * Date: Oct 5, 2002
 * Time: 1:19:50 PM
 */
package net.sourceforge.jwebunit.fit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DirectoryResult extends FitResult {
    private List results;
    private File directory;

    public int getRight() {
        int sum = 0;
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            FitResult fitResult = (FitResult) iterator.next();
            sum += fitResult.getRight();
        }
        return sum;
    }

    public int getWrong() {
        int sum = 0;
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            FitResult fitResult = (FitResult) iterator.next();
            sum += fitResult.getWrong();
        }
        return sum;

    }

    public int getIgnores() {
        int sum = 0;
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            FitResult fitResult = (FitResult) iterator.next();
            sum += fitResult.getIgnores();
        }
        return sum;

    }

    public int getExceptions() {
        int sum = 0;
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            FitResult fitResult = (FitResult) iterator.next();
            sum += fitResult.getExceptions();
        }
        return sum;

    }

    public DirectoryResult(File directory) {
        super(directory);
        this.directory = directory;
        results = new ArrayList();
    }

    public void addResult(FitResult result) {
        results.add(result);
    }

    public boolean didFail() {
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            FitResult fitResult = (FitResult) iterator.next();
            if(fitResult.didFail()) return true;
        }
        return false;
    }

    public String getDisplayName() {
        return directory.getName();
    }

    public String getOutname() {
        return directory.getName() + "/index.html";
    }

    public void writeIndexFile() {
        File indexFile = new File(directory, "index.html");
        FileWriter writer = null;
        try {
            writer = new FileWriter(indexFile);
            writer.write("<html><head><title>Fit Results Summary</title></head><body>");
            writer.write("<h1>Results</h1>");
            writer.write("<table border=\"1\" cellspacing=\"5\" cellpadding=\"5\">");
            writeResults(writer);
            writer.write("</table>");
            writer.write("<br>");
            writer.write("Cumulative Results: " + counts());
            writer.write("<br><br>");
            writer.write(new Date().toString());
            writer.write("</body></html>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeResults(FileWriter writer) throws IOException {
        for (Iterator iterator = results.iterator(); iterator.hasNext();) {
            FitResult result = (FitResult) iterator.next();
            String color = (result.didFail()) ? "#ffcfcf" : "#cfffcf";
            writeRow(writer, result, color);
        }
    }

    private void writeRow(FileWriter writer, FitResult result, String color) throws IOException {
        writer.write("<tr bgcolor=\"" + color + "\"><td>");
        writer.write("<a href=\"" + result.getOutname() + "\">" + result.getDisplayName() + "</a>");
        writer.write("<td>" + result.counts() + "</td>");
        writer.write("</td></tr>");

    }

}
