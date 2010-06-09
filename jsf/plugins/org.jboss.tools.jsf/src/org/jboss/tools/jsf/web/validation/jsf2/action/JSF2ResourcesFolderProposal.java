/*******************************************************************************
 * Copyright (c) 2007-2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.jsf.web.validation.jsf2.action;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.jboss.tools.jsf.jsf2.util.JSF2ResourceUtil;
import org.jboss.tools.jsf.messages.JSFUIMessages;
import org.jboss.tools.jsf.web.validation.jsf2.JSF2XMLValidator;
import org.jboss.tools.jsf.web.validation.jsf2.components.IJSF2ValidationComponent;

/**
 * 
 * @author yzhishko
 * 
 */

public class JSF2ResourcesFolderProposal extends JSF2AbstractProposal {

	private String componentPath = null;

	public JSF2ResourcesFolderProposal() {
		super();
	}

	public JSF2ResourcesFolderProposal(IResource validateResource, String compPath) {
		super(validateResource);
		this.componentPath = compPath;
	}

	public String getDisplayString() {
		return JSFUIMessages.Create_JSF_2_Resources_Folder;
	}

	@Override
	protected void runWithMarker(IMarker marker) throws CoreException {
		if (marker != null) {
			componentPath = (String) marker
					.getAttribute(IJSF2ValidationComponent.JSF2_URI_NAME_KEY);
			validateResource = marker.getResource();
		}
		JSF2ResourceUtil.createResourcesFolderByNameSpace(validateResource
				.getProject(), componentPath);
		validateResource.getProject().deleteMarkers(
				JSF2XMLValidator.JSF2_PROBLEM_ID, false, 1);
	}

}