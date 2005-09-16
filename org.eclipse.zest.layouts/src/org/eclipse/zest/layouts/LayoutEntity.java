/*******************************************************************************
 * Copyright 2005, CHISEL Group, University of Victoria, Victoria, BC, Canada.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The Chisel Group, University of Victoria
 *******************************************************************************/
package org.eclipse.mylar.zest.layouts;

/**
 * This represents a single entity, providing the layout algorithms with 
 * a common interface to run on.
 * 
 * @author Casey Best
 */
public interface LayoutEntity extends Comparable {
	
	public final static String ATTR_PREFERRED_WIDTH = "tree-preferred-width";
    public final static String ATTR_PREFERRED_HEIGHT = "tree-preferred-height";
	
	public boolean hasPreferredLocation();

    public double getXInLayout();
	public double getYInLayout();
	public double getWidthInLayout();
	public double getHeightInLayout();

	public void setLocationInLayout (double x, double y);
	public void setSizeInLayout (double width, double height);

	
	public Object getLayoutInformation();
	public void setLayoutInformation(Object internalEntity);
	
	
}