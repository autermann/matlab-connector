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

/**
 * Base class for representing a MATLAB value.
 *
 * @author Richard Jones
 *
 */
public abstract class MatlabValue {
    public abstract void accept(MatlabValueVisitor visitor);

    public abstract <T> T accept(ReturningMatlabValueVisitor<T> visitor);

    public abstract MatlabType getType();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    /**
     * Checks if this value is a scalar.
     *
     * @return <code>true</code> if this value is a scalar, <code>false</code>
     *         otherwise
     */
    public boolean isScalar() {
        return getType() == MatlabType.SCALAR;
    }

    /**
     * Checks if this value is a matrix.
     *
     * @return <code>true</code> if this value is a matrix, <code>false</code>
     *         otherwise
     */
    public boolean isMatrix() {
        return getType() == MatlabType.MATRIX;
    }

    /**
     * Checks if this value is an array.
     *
     * @return <code>true</code> if this value is an array, <code>false</code>
     *         otherwise
     */
    public boolean isArray() {
        return getType() == MatlabType.ARRAY;
    }

    /**
     * Checks if this value is a string.
     *
     * @return <code>true</code> if this value is a string, <code>false</code>
     *         otherwise
     */
    public boolean isString() {
        return getType() == MatlabType.STRING;
    }

    /**
     * Checks if this value is a cell.
     *
     * @return <code>true</code> if this value is a cell, <code>false</code>
     *         otherwise
     */
    public boolean isCell() {
        return getType() == MatlabType.CELL;
    }

    /**
     * Checks if this value is a struct.
     *
     * @return <code>true</code> if this value is a struct, <code>false</code>
     *         otherwise
     */
    public boolean isStruct() {
        return getType() == MatlabType.STRUCT;
    }

    /**
     * Checks if this value is a boolean.
     *
     * @return <code>true</code> if this value is a boolean, <code>false</code>
     *         otherwise
     */
    public boolean isBoolean() {
        return getType() == MatlabType.BOOLEAN;
    }

    /**
     * Checks if this value is a file.
     *
     * @return <code>true</code> if this value is a file, <code>false</code>
     *         otherwise
     */
    public boolean isFile() {
        return getType() == MatlabType.FILE;
    }

    /**
     * Checks if this value is a date time.
     *
     * @return <code>true</code> if this value is a file, <code>false</code>
     *         otherwise
     */
    public boolean isDateTime() {
        return getType() == MatlabType.DATE_TIME;
    }

    /**
     * Returns this value as a scalar. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a scalar.
     *
     * @return this value as a {@link MatlabScalar}
     *
     * @see #isScalar()
     */
    public MatlabScalar asScalar() {
        if (isScalar()) {
            return (MatlabScalar) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a matrix. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a matrix.
     *
     * @return this value as a {@link MatlabMatrix}
     *
     * @see #isMatrix()
     */
    public MatlabMatrix asMatrix() {
        if (isMatrix()) {
            return (MatlabMatrix) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a array. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a array.
     *
     * @return this value as a {@link MatlabArray}
     */
    public MatlabArray asArray() {
        if (isArray()) {
            return (MatlabArray) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a string. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a string.
     *
     * @return this value as a {@link MatlabString}
     */
    public MatlabString asString() {
        if (isString()) {
            return (MatlabString) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a cell. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a cell.
     *
     * @return this value as a {@link MatlabCell}
     */
    public MatlabCell asCell() {
        if (isCell()) {
            return (MatlabCell) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a struct. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a struct.
     *
     * @return this value as a {@link MatlabStruct}
     */
    public MatlabStruct asStruct() {
        if (isStruct()) {
            return (MatlabStruct) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a boolean. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a boolean.
     *
     * @return this value as a {@link MatlabBoolean}
     */
    public MatlabBoolean asBoolean() {
        if (isBoolean()) {
            return (MatlabBoolean) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a file. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a file.
     *
     * @return this value as a {@link MatlabFile}
     */
    public MatlabFile asFile() {
        if (isFile()) {
            return (MatlabFile) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns this value as a file. Will throw a
     * {@link UnsupportedOperationException}
     * if this value is not a date time.
     *
     * @return this value as a {@link MatlabDateTime}
     */
    public MatlabDateTime asDateTime() {
        if (isDateTime()) {
            return (MatlabDateTime) this;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(),
                             MatlabEvalStringVisitor.create().apply(this));
    }

}
