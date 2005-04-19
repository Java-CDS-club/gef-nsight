/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/ 

package org.eclipse.gef.examples.text.model;

import org.eclipse.swt.widgets.Control;

public class CanvasStyle extends Style {

private transient final Control canvas;

public CanvasStyle (Control canvas) {
	this.canvas = canvas;
}

public String getFontFamily() {
	return canvas.getFont().getFontData()[0].getName();
}

public int getFontHeight() {
	return canvas.getFont().getFontData()[0].getHeight();
}

}