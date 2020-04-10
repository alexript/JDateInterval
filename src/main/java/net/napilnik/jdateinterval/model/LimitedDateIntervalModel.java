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

import java.util.Date;
import net.napilnik.jdateinterval.validators.DatesOrderValidator;
import net.napilnik.jdateinterval.validators.ValidationResultSet;

/**
 *
 * @author alexr
 */
public class LimitedDateIntervalModel extends DefaultDateIntervalModel {

    private DateIntervalModel limit;

    public LimitedDateIntervalModel() {
        this(null);
    }

    public LimitedDateIntervalModel(DateIntervalModel limit) {
        super();
        setLimit(limit);
    }

    public LimitedDateIntervalModel(Date startDate, Date endDate) {
        this(startDate, endDate, null);

    }

    public LimitedDateIntervalModel(Date startDate, Date endDate, DateIntervalModel limit) {
        super(startDate, endDate);
        setLimit(limit);
    }

    public LimitedDateIntervalModel(DateIntervalModel other, DateIntervalModel limit) {
        super(other);
        setLimit(limit);
    }
    
    public LimitedDateIntervalModel(LimitedDateIntervalModel other) {
        super(other);
        setLimit(other.getLimit());
    }

    public void setLimit(DateIntervalModel limit) {
        this.limit = limit;
    }

    public DateIntervalModel getLimit() {
        return limit;
    }

    @Override
    public void setStartDate(Date startDate) {
        if (limit != null) {
            Date lowLimit = limit.getStartDate();
            if (lowLimit != null) {
                if (startDate.before(lowLimit)) {
                    throw new IllegalArgumentException(String.format("startDate %tc before lowlimit %tc", startDate, lowLimit));
                }
            }
        }
        super.setStartDate(startDate);
    }

    @Override
    public void setEndDate(Date endDate) {
        if (limit != null) {

            Date topLimit = limit.getEndDate();
            if (topLimit != null) {
                if (endDate.after(topLimit)) {
                    throw new IllegalArgumentException(String.format("endDate %tc after toplimit %tc", endDate, topLimit));
                }
            }
        }
        super.setEndDate(endDate);
    }

    @Override
    public boolean isValid() {
        DatesOrderValidator validator = new DatesOrderValidator();

        Date lowLimit = limit.getStartDate();
        Date topLimit = limit.getEndDate();

        ValidationResultSet result = new ValidationResultSet();
        result.add(validator.validate(this));
        if (lowLimit != null) {
            DefaultDateIntervalModel leftPair = new DefaultDateIntervalModel(lowLimit, getStartDate());
            result.add(validator.validate(leftPair));
        }
        if (topLimit != null) {
            DefaultDateIntervalModel rightPair = new DefaultDateIntervalModel(getEndDate(), topLimit);
            result.add(validator.validate(rightPair));
        }
        if (lowLimit != null && topLimit != null) {
            result.add(validator.validate(limit));
        }
        return result.isValid();
    }
}
