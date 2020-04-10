/*
 * The MIT License
 *
 * Copyright 2020 alexr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.napilnik.jdateinterval.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author alexr
 */
public class MyJDate extends JXDatePicker {

    private static final SimpleDateFormat TODAY_PATTERN = new SimpleDateFormat("dd.MM.yyyy");
    private final JLabel jb;

    public MyJDate() {
        super();
        jb = new JLabel("Today is " + TODAY_PATTERN.format(new Date()));
        initComponent();
    }

    private void initComponent() {
        
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        jp.setMinimumSize(new Dimension(100, 70));
        jb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        jp.add(jb);
        setLinkPanel(jp);
    }

    public void addTodayClickListener(MouseAdapter adapter) {
        jb.addMouseListener(adapter);
    }
}
