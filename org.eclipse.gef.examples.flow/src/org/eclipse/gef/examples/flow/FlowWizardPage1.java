package org.eclipse.gef.examples.flow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.examples.flow.model.Activity;
import org.eclipse.gef.examples.flow.model.ActivityDiagram;
import org.eclipse.gef.examples.flow.model.ParallelActivity;
import org.eclipse.gef.examples.flow.model.SequentialActivity;
import org.eclipse.gef.examples.flow.model.Transition;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * FlowWizardPage1
 * @author Daniel Lee
 */
public class FlowWizardPage1 extends WizardNewFileCreationPage {

private IWorkbench	workbench;
private static int exampleCount = 1;

public FlowWizardPage1(IWorkbench aWorkbench, IStructuredSelection selection) {
	super("sampleFlowPage1", selection);
	this.setTitle("Create Flow Example File");
	this.setDescription("Create a new flow file resource");
	this.setImageDescriptor(ImageDescriptor.createFromFile(ActivityDiagram.class,"icons/flowbanner.gif"));
	this.workbench = aWorkbench;
}

/**
 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
 */
public void createControl(Composite parent) {
	super.createControl(parent);
	this.setFileName("flowExample" + exampleCount + ".flow");
	Composite composite = (Composite)getControl();
	setPageComplete(validatePage());
}

private ActivityDiagram createWakeupModel() {
	ActivityDiagram diagram = new ActivityDiagram();
	SequentialActivity wakeup = new SequentialActivity();
	Activity backToSleep = new Activity("Go back to sleep");
	Activity turnOff = new Activity("Turn off alarm");
	wakeup.setName("Wake up");
	wakeup.addChild(new Activity("Hit snooze button"));
	wakeup.addChild(backToSleep);
	wakeup.addChild(turnOff);
	wakeup.addChild(new Activity("Get out of bed"));
	diagram.addChild(wakeup);

	SequentialActivity bathroom = new SequentialActivity();
	bathroom.addChild(new Activity("Brush teeth"));
	bathroom.addChild(new Activity("Take shower"));
	bathroom.addChild(new Activity("Comb hair"));
	bathroom.setName("Bathroom activities");
	diagram.addChild(bathroom);
	
	ParallelActivity relaxation = new ParallelActivity();
	relaxation.addChild(new Activity("Watch cartoons"));
	relaxation.addChild(new Activity("Power Yoga"));
	relaxation.setName("Morning relaxation ritual");
	diagram.addChild(relaxation);
	
	Activity sleep, alarm, alarm2, clothes, spare, no, yes, drive;
	diagram.addChild(sleep = new Activity("Sleep....."));
	diagram.addChild(alarm = new Activity("Alarm!!!"));
	diagram.addChild(alarm2 = new Activity("Alarm!!!"));
	diagram.addChild(clothes = new Activity("Put on clothes"));
	diagram.addChild(spare = new Activity("Is there time to spare?"));
	diagram.addChild(yes = new Activity("YES"));
	diagram.addChild(no = new Activity("NO"));
	diagram.addChild(drive = new Activity("Drive to work"));
	
	sleep.addOutput(new Transition(sleep, alarm));
	alarm.addOutput(new Transition(alarm, wakeup));
	backToSleep.addOutput(new Transition(backToSleep,alarm2));
	alarm2.addOutput(new Transition(alarm2,turnOff));
	wakeup.addOutput(new Transition(wakeup, bathroom));
	bathroom.addOutput(new Transition(bathroom, clothes));
	clothes.addOutput(new Transition(clothes, spare));
	spare.addOutput(new Transition(spare, yes));
	spare.addOutput(new Transition(spare, no));
	yes.addOutput(new Transition(yes, relaxation));
	no.addOutput(new Transition(no, drive));
	relaxation.addOutput(new Transition(relaxation, drive));
	return diagram;
}

protected InputStream getInitialContents() {
	ActivityDiagram diag = createWakeupModel();
	ByteArrayInputStream bais = null;
	try {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(diag);
		oos.flush();
		oos.close();
		baos.close();
		bais = new ByteArrayInputStream(baos.toByteArray());
		bais.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return bais;
}

public boolean finish() {
	IFile newFile = createNewFile();
	if (newFile == null) 
		return false;  // ie.- creation was unsuccessful

	// Since the file resource was created fine, open it for editing
	// iff requested by the user
	try {
		IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = dwindow.getActivePage();
		if (page != null) 
			page.openEditor(newFile);
	} 
	catch (org.eclipse.ui.PartInitException e) {
		e.printStackTrace();
		return false;
	}
	exampleCount++;
	return true;
}
}
