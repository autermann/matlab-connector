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
package com.github.autermann.matlab.yaml;

import org.yaml.snakeyaml.nodes.Tag;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public interface MatlabYAMLConstants {
    public static final String CELL_KEY = "cell";
    Tag MATLAB_MATRIX_TAG = new Tag("!matlab-matrix");
    Tag MATLAB_SCALAR_TAG = new Tag("!matlab-scalar");
    Tag MATLAB_EXCEPTION_TAG = new Tag("!matlab-exception");
    Tag MATLAB_CELL_TAG = new Tag("!matlab-cell");
    Tag MATLAB_STRUCT_TAG = new Tag("!matlab-struct");
    Tag MATLAB_REQUEST_TAG = new Tag("!matlab-request");
    Tag MATLAB_STRING_TAG = new Tag("!matlab-string");
    Tag MATLAB_ARRAY_TAG = new Tag("!matlab-array");
    Tag MATLAB_RESULT_TAG = new Tag("!matlab-result");
    Tag MATLAB_BOOLEAN_TAG = new Tag("!matlab-boolean");
    String PARAMETERS_KEY = "parameters";
    String RESULT_COUNT_KEY = "resultCount";
    String MESSAGE_KEY = "message";
    String FUNCTION_KEY = "function";
}