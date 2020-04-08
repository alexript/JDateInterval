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
package net.napilnik.jdateinterval.validators;

import java.util.Date;
import net.napilnik.jdateinterval.model.DateIntervalModel;

/**
 *
 * @author alexr
 */
public class DatesOrderValidator implements DateValidator {

    @Override
    public ValidationResultSet validate(DateIntervalModel interval) {
        ValidationResultSet results = new ValidationResultSet();
        Date startDate = interval.getStartDate();
        Date endDate = interval.getEndDate();
        if (startDate == null) {
            results.add("Empty start date");
        }
        if (endDate == null) {
            results.add("Empty end date");
        }

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            results.add("Start date after end date");
        }
        return results;
    }

}
