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
package net.napilnik.jdateinterval.model;

import java.io.Serializable;
import java.util.Date;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import net.napilnik.jdateinterval.event.IntervalChangedListener;
import net.napilnik.jdateinterval.event.IntervalEvent;
import net.napilnik.jdateinterval.validators.DatesOrderValidator;

/**
 *
 * @author alexr
 */
public class DefaultDateIntervalModel implements Serializable, DateIntervalModel {

    private Date startDate;
    private Date endDate;

    protected EventListenerList listenerList = new EventListenerList();

    public DefaultDateIntervalModel() {
        this(null, null);
    }

    public DefaultDateIntervalModel(Date startDate, Date endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public DefaultDateIntervalModel(DateIntervalModel other) {
        this(new Date(other.getStartDate().getTime()), new Date(other.getEndDate().getTime()));
    }

    @Override
    public void reset() {
        setStartDate(null);
        setEndDate(null);
    }

    @Override
    public void setStartDate(Date startDate) {
        Date oldDate = this.startDate == null ? null : new Date(this.startDate.getTime());
        this.startDate = startDate;
        fireIntervalChanged(this, true, oldDate, this.startDate);
    }

    @Override
    public void setEndDate(Date endDate) {
        Date oldDate = this.endDate == null ? null : new Date(this.endDate.getTime());
        this.endDate = endDate;
        fireIntervalChanged(this, false, oldDate, this.endDate);
    }

    @Override
    public final Date getStartDate() {
        return startDate;
    }

    @Override
    public final Date getEndDate() {
        return endDate;
    }

    @Override
    public boolean isValid() {
        DatesOrderValidator validator = new DatesOrderValidator();
        return validator.validate(this).isValid();
    }

    @Override
    public void addIntervalListener(IntervalChangedListener l) {
        listenerList.add(IntervalChangedListener.class, l);
    }

    @Override
    public void removeIntervalListener(IntervalChangedListener l) {
        listenerList.remove(IntervalChangedListener.class, l);
    }

    public IntervalChangedListener[] getListDataListeners() {
        return listenerList.getListeners(IntervalChangedListener.class);
    }

    protected void fireIntervalChanged(Object source, boolean isStartDate, Date oldDate, Date newDate) {
        Object[] listeners = listenerList.getListenerList();
        IntervalEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == IntervalChangedListener.class) {
                if (e == null) {
                    e = new IntervalEvent(source, oldDate, newDate);
                }
                if (isStartDate) {
                    ((IntervalChangedListener) listeners[i + 1]).startDateChanged(e);
                } else {
                    ((IntervalChangedListener) listeners[i + 1]).endDateChanged(e);
                }
            }
        }
    }

    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }
}
