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
package org.jboss.tools.struts.validators.model;

import org.jboss.tools.common.model.impl.*;

public class ValidatorGlobalImpl extends OrderedObjectImpl {
	private static final long serialVersionUID = 9155894014843010756L;

    public String name() {
        return "" + System.identityHashCode(this);
    }

    protected RegularChildren createChildren() {
        return new ValidatorGrouppedChildren();
    }

    public String getPresentationString() {
        return "" + get_0("element type");
    }

}

