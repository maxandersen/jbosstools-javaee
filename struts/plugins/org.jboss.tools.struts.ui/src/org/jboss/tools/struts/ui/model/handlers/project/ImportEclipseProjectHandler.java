/*******************************************************************************
 * Copyright (c) 2007 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/ 
package org.jboss.tools.struts.ui.model.handlers.project;

import java.util.*;
import org.jboss.tools.common.meta.action.impl.AbstractHandler;
import org.jboss.tools.common.model.XModelException;
import org.jboss.tools.common.model.XModelObject;
import org.jboss.tools.struts.ui.internal.action.ImportProjectAction;

public class ImportEclipseProjectHandler extends AbstractHandler {

	public boolean isEnabled(XModelObject object) {
		return true; 
	}
	
	public void executeHandler(XModelObject object, Properties p) throws XModelException {
		ImportProjectAction action = new ImportProjectAction();
		action.run();
		
	}
	
}
