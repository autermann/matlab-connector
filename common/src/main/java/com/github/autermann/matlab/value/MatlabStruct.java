/*
 * Copyright (C) 2012-2013 by it's authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.autermann.matlab.value;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Joiner;

public class MatlabStruct extends MatlabValue {

    private final Map<String, MatlabValue> struct;

    /**
     * Creates a new <code>MLStruct</code> instance.
     *
     */
    public MatlabStruct() {
        this.struct = new HashMap<String, MatlabValue>();
    }

    public void setField(String field, MatlabValue value) {
        struct.put(field, value);
    }

    public MatlabValue getField(String field) {
        return struct.get(field);
    }

    public Map<String, MatlabValue> getStruct() {
        return struct;
    }

    @Override
    public String toMatlabString() {
        StringBuilder sb = new StringBuilder();
        sb.append("struct(");
        for (String field : struct.keySet()) {
            sb.append('\'').append(field).append('\'');
            sb.append(',');
            sb.append(struct.get(field).toMatlabString());
            sb.append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(')');
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("struct: ");
        Joiner.on(",").appendTo(sb, struct.keySet());
        return sb.toString();
    }

}