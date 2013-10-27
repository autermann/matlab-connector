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

import java.util.Map;

import org.yaml.snakeyaml.nodes.Node;

import com.github.autermann.matlab.MatlabException;
import com.github.autermann.matlab.yaml.MatlabYAMLConstants;

/**
 * TODO JavaDoc
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public class MatlabExceptionConstruct extends MatlabConstruct {

    public MatlabExceptionConstruct(MatlabConstructor delegate) {
        super(delegate);
    }

    @Override
    public Object construct(Node node) {
        Map<Object, Object> map = constructMapping(node);
        String message
                = constructString(map.get(MatlabYAMLConstants.MESSAGE_KEY));
        return new MatlabException(message);
    }

}