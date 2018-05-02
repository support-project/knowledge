package org.support.project.ormapping.tool.impl;

import java.io.PrintWriter;
import java.io.Writer;

public class DebugAblePrintWriter extends PrintWriter {
    private StringBuilder builder;
    public DebugAblePrintWriter(Writer out) {
        super(out);
        builder = new StringBuilder();
    }
    /* (non-Javadoc)
     * @see java.io.PrintWriter#print(java.lang.String)
     */
    @Override
    public void print(String s) {
        builder.append(s);
        super.print(s);
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return builder.toString();
    }
    
}
