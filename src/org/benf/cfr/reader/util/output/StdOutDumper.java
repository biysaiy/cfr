package org.benf.cfr.reader.util.output;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 18/04/2011
 * Time: 11:24
 * To change this template use File | Settings | File Templates.
 */
public class StdOutDumper implements Dumper {
    private int indent;
    private boolean atStart = true;
    private boolean pendingCR = false;

    @Override
    public void printLabel(String s) {
        processPendingCR();
        System.out.println(s + ":");
        atStart = true;
    }

    @Override
    public void enqueuePendingCarriageReturn() {
        pendingCR = true;
    }

    @Override
    public void removePendingCarriageReturn() {
        pendingCR = false;
    }

    private void processPendingCR() {
        if (pendingCR) {
            System.out.println();
            atStart = true;
            pendingCR = false;
        }
    }

    @Override
    public void print(String s) {
        processPendingCR();
        doIndent();
        System.out.print(s);
        atStart = false;
        if (s.endsWith("\n")) atStart = true;
    }

    @Override
    public Dumper newln() {
        System.out.println("");
        atStart = true;
        return this;
    }

    private void doIndent() {
        if (!atStart) return;
        String indents = "    ";
        for (int x = 0; x < indent; ++x) System.out.print(indents);
        atStart = false;
    }

    @Override
    public void line() {
        System.out.println("\n// -------------------");
        atStart = true;
    }

    @Override
    public int getIndent() {
        return indent;
    }

    @Override
    public void indent(int diff) {
        indent += diff;
    }

    @Override
    public void dump(List<? extends Dumpable> d) {
        for (Dumpable dumpable : d) {
            dumpable.dump(this);
        }
    }
}