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
package com.github.autermann.matlab.yaml.construct;

import org.yaml.snakeyaml.nodes.Node;

import com.github.autermann.matlab.value.MatlabCell;

/**
 * TODO JavaDoc
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class MatlabCellConstruct extends MatlabConstruct {

    public MatlabCellConstruct(MatlabConstructor delegate) {
        super(delegate);
    }

    @Override
    public MatlabCell construct(Node node) {
        return new MatlabCell(constructValueArray(node));
    }

}
