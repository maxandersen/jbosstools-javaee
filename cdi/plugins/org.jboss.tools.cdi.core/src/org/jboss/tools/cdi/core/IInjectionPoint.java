/******************************************************************************* 
 * Copyright (c) 2009 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.tools.cdi.core;

import java.util.Collection;

import org.jboss.tools.common.java.IAnnotationDeclaration;
import org.jboss.tools.common.java.IParametedType;
import org.jboss.tools.common.text.ITextSourceReference;

/**
 * Represents an injection point.
 * 
 * @author Alexey Kazakov
 */
public interface IInjectionPoint extends ICDIElement, IBeanMember {

	/**
	 * Returns the bean that declares this injection point.
	 * 
	 * @return the bean that declares this injection point.
	 */
	IBean getBean();

	/**
	 * Returns the required type of this injection point.
	 * 
	 * @return the required type of this injection point.
	 */
	IParametedType getType();

	/**
	 * Gets the required qualifiers of the injection point.
	 * 
	 * @return the required qualifiers
	 */
	Collection<IQualifierDeclaration> getQualifierDeclarations();

	/**
	 * Returns true if the injection point declares @Default qualifier or doesn't declare any qualifier at all.
	 *  
	 * @return true if the injection point declares @Default qualifier or doesn't declare any qualifier at all
	 */
	boolean hasDefaultQualifier();

	/**
	 * Determines if the injection point is a decorator delegate injection
	 * point.
	 * 
	 * @return <tt>true</tt> if the injection point is a decorator delegate
	 *         injection point, and <tt>false</tt> otherwise
	 */
	boolean isDelegate();

	/**
	 * Returns the @Delegate annotation of this injection point field or
	 * parameter of injection point method. Should not return null if
	 * isDelegate() returns "true".
	 * 
	 * @return the @Delegate annotation of this injection point field or
	 *         parameter of injection point method. May be null.
	 */
	ITextSourceReference getDelegateAnnotation();

	/**
	 * Returns the @Inject annotation declaration
	 * 
	 * @return the @Inject annotation declaration.
	 */
	IAnnotationDeclaration getInjectAnnotation();

	/**
	 * Returns name declared by @Named or null if declaration is missing.
	 * 
	 * @return name declared by @Named or null if declaration is missing
	 */
	public String getBeanName();
}