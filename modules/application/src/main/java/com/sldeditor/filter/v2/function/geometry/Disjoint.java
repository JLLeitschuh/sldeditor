/*
 * SLD Editor - The Open Source Java SLD Editor
 *
 * Copyright (C) 2016, SCISYS UK Limited
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

package com.sldeditor.filter.v2.function.geometry;

import java.util.List;

import org.geotools.filter.spatial.DisjointImpl;
import org.opengis.filter.Filter;
import org.opengis.filter.expression.Expression;

import com.sldeditor.filter.v2.expression.ExpressionTypeEnum;
import com.sldeditor.filter.v2.function.FilterBase;
import com.sldeditor.filter.v2.function.FilterConfigInterface;
import com.sldeditor.filter.v2.function.FilterExtendedInterface;
import com.sldeditor.filter.v2.function.FilterName;
import com.sldeditor.filter.v2.function.FilterNameParameter;
import com.vividsolutions.jts.geom.Geometry;

/**
 * The Class Disjoint.
 *
 * @author Robert Ward (SCISYS)
 */
public class Disjoint extends FilterBase implements FilterConfigInterface {

    /**
     * The Class DisjointExtended.
     */
    public class DisjointExtended extends DisjointImpl implements FilterExtendedInterface {

        /**
         * Instantiates a new disjoint extended.
         */
        public DisjointExtended() {
            super(null, null);
        }

        /**
         * Instantiates a new disjoint extended.
         *
         * @param expression1 the expression 1
         * @param expression2 the expression 2
         */
        public DisjointExtended(Expression expression1, Expression expression2) {
            super(expression1, expression2);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.geotools.filter.GeometryFilterImpl#toString()
         */
        public String toString() {
            return "[ " + getExpression1() + " disjoint " + getExpression2() + " ]";
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.sldeditor.filter.v2.function.FilterExtendedInterface#getOriginalFilter()
         */
        @Override
        public Class<?> getOriginalFilter() {
            return DisjointImpl.class;
        }
    }

    /**
     * Default constructor.
     */
    public Disjoint(String category) {
        super(category);
    }

    /**
     * Gets the filter configuration.
     *
     * @return the filter configuration
     */
    @Override
    public FilterName getFilterConfiguration() {
        FilterName filterName = new FilterName("Disjoint", Boolean.class);
        filterName.addParameter(
                new FilterNameParameter("property", ExpressionTypeEnum.PROPERTY, Geometry.class));
        filterName.addParameter(new FilterNameParameter("expression", ExpressionTypeEnum.EXPRESSION,
                Geometry.class));

        return filterName;
    }

    /**
     * Gets the filter class.
     *
     * @return the filter class
     */
    @Override
    public Class<?> getFilterClass() {
        return DisjointImpl.class;
    }

    /**
     * Creates the filter.
     *
     * @return the filter
     */
    @Override
    public Filter createFilter() {
        return new DisjointExtended();
    }

    /**
     * Creates the filter.
     *
     * @param parameterList the parameter list
     * @return the filter
     */
    @Override
    public Filter createFilter(List<Expression> parameterList) {

        DisjointImpl filter = null;

        if ((parameterList == null) || (parameterList.size() != 2)) {
            filter = new DisjointExtended();
        } else {
            filter = new DisjointExtended(parameterList.get(0), parameterList.get(1));
        }

        return filter;
    }

    /**
     * Creates the logic filter.
     *
     * @param filterList the filter list
     * @return the filter
     */
    @Override
    public Filter createLogicFilter(List<Filter> filterList) {
        // Not supported
        return null;
    }
}
