/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.examples.flow;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.eclipse.gef.palette.*;
import org.eclipse.gef.requests.SimpleFactory;

import org.eclipse.gef.examples.flow.model.*;

/**
 * Plugin class used by the flow editor.
 */
public class FlowPlugin extends AbstractUIPlugin {
	
/**
 * Creates a new FlowPlugin with the given descriptor
 * @param descriptor the descriptor
 */
public FlowPlugin(IPluginDescriptor descriptor) {
	super(descriptor);
}

private static List createCategories(PaletteRoot root) {
	List categories = new ArrayList();
	categories.add(createControlGroup(root));
	categories.add(createComponentsDrawer());
	return categories;
}

private static PaletteContainer createComponentsDrawer() {

	PaletteDrawer drawer = new PaletteDrawer(
		"Components",
		ImageDescriptor.createFromFile(Activity.class, "icons/comp.gif"));

	List entries = new ArrayList();
	
	CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
		"Activity",
		"Create a new Activity Node",
		Activity.class,
		new SimpleFactory(Activity.class),
		ImageDescriptor.createFromFile(Activity.class, "icons/comp.gif"), 
		ImageDescriptor.createFromFile(Activity.class, "icons/comp.gif")
	);
	entries.add(combined);

	combined = new CombinedTemplateCreationEntry(
		"Sequential Activity",
		"Create a Sequential Activity",
		SequentialActivity.class,
		new SimpleFactory(SequentialActivity.class),
		ImageDescriptor.createFromFile(Activity.class, "icons/comp.gif"), 
		ImageDescriptor.createFromFile(Activity.class, "icons/comp.gif")
	);
	entries.add(combined);

	combined = new CombinedTemplateCreationEntry(
		"Parallel Activity",
		"Create a  Parallel Activity",
		ParallelActivity.class,
		new SimpleFactory(ParallelActivity.class),
		ImageDescriptor.createFromFile(Activity.class, "icons/comp.gif"), 
		ImageDescriptor.createFromFile(Activity.class, "icons/comp.gif")
	);
	entries.add(combined);
	
	drawer.addAll(entries);
	return drawer;
}

private static PaletteContainer createControlGroup(PaletteRoot root) {
	PaletteGroup controlGroup = new PaletteGroup("Control Group");

	List entries = new ArrayList();

	ToolEntry tool = new SelectionToolEntry();
	entries.add(tool);
	root.setDefaultEntry(tool);

	tool = new MarqueeToolEntry();
	entries.add(tool);
	
	PaletteSeparator sep = new PaletteSeparator(
			"org.eclipse.gef.examples.flow.flowplugin.sep2");
	sep.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
	entries.add(sep);

	tool = new ConnectionCreationToolEntry(
		"Connection Creation",
		"Creating connections",
		null,
		ImageDescriptor.createFromFile(Activity.class, "icons/connection16.gif"),
		ImageDescriptor.createFromFile(Activity.class, "icons/connection24.gif")
	);
	entries.add(tool);
	controlGroup.addAll(entries);
	return controlGroup;
}

/**
 * Creates the PaletteRoot and adds all Palette elements.
 * @return the root
 */
public static PaletteRoot createPalette() {
	PaletteRoot flowPalette = new PaletteRoot();
	flowPalette.addAll(createCategories(flowPalette));
	return flowPalette;
}

}
