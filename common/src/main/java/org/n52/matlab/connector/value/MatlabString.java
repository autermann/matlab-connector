/*
 * Copyright (C) 2012-2015 by it's authors.
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
package org.n52.matlab.connector.value;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.google.common.base.Objects;

public class MatlabString extends MatlabValue implements
        Comparable<MatlabString> {
    private final String string;

    /**
     * Creates a new <code>MLString</code> instance from a given
     * <code>String</code>.
     *
     * @param string the <code>String</code>
     */
    public MatlabString(String string) {
        this.string = checkNotNull(string);
    }

    /**
     * Returns the string.
     *
     * @return the string
     */
    public String value() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MatlabString) {
            MatlabString other = (MatlabString) o;
            return Objects.equal(value(), other.value());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value());
    }

    @Override
    public void accept(MatlabValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T accept(ReturningMatlabValueVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int compareTo(MatlabString o) {
        return value().compareTo(checkNotNull(o).value());
    }

    @Override
    public MatlabType getType() {
        return MatlabType.STRING;
    }

    public MatlabBoolean toBoolean() {
        return MatlabBoolean.fromBoolean(Boolean.valueOf(value()));
    }

    public MatlabDateTime toDateTime() {
        // FIXME matlab date time parsing
        throw new UnsupportedOperationException();
    }

    public MatlabFile toFile(boolean delete) throws IOException {
        MatlabFile file = new MatlabFile(Paths.get(value()));
        file.load();
        if (delete) {
            file.delete();
        }
        return file;
    }

}
