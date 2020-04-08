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

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alexr
 */
public class ValidationResultSet extends HashSet<ValidationResult> {

    public ValidationResultSet() {
        super();
    }

    public void add(String message) {
        this.add(new ValidationResult(message));
    }

    public void add(ValidationResultSet otherSet) {
        if (otherSet != null) {
            this.addAll(otherSet);
        }
    }

    public boolean isValid() {
        boolean result = true;
        for (ValidationResult r : this) {
            result &= r.isValid();
        }
        return result;
    }

    public Set<String> getErrors() {
        boolean valid = isValid();
        if (valid) {
            return null;
        }

        Set<String> messages = new HashSet<>();
        for (ValidationResult r : this) {
            if (!r.isValid()) {
                messages.add(r.getMessage());
            }
        }
        return messages;
    }
}
