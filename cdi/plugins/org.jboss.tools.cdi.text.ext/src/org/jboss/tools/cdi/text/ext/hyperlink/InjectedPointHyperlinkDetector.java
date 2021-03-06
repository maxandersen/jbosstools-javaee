/******************************************************************************* 
 * Copyright (c) 2010 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.tools.cdi.text.ext.hyperlink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jdt.internal.ui.text.JavaWordFinder;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.tools.cdi.core.CDICoreNature;
import org.jboss.tools.cdi.core.CDIUtil;
import org.jboss.tools.cdi.core.IBean;
import org.jboss.tools.cdi.core.ICDIProject;
import org.jboss.tools.cdi.core.IInjectionPoint;
import org.jboss.tools.cdi.text.ext.CDIExtensionsPlugin;

public class InjectedPointHyperlinkDetector extends AbstractHyperlinkDetector{
	private IRegion region;
	protected IDocument document;
	protected ITextViewer viewer;
	protected IFile file;

	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {
		this.region = region;
		this.viewer = textViewer;
		
		ITextEditor textEditor= (ITextEditor)getAdapter(ITextEditor.class);
		if (region == null || !(textEditor instanceof JavaEditor))
			return null;
		
		if(textEditor.getEditorInput() instanceof IFileEditorInput){
			file = ((IFileEditorInput)textEditor.getEditorInput()).getFile();
		}
		
		int offset= region.getOffset();
		
		ITypeRoot input= EditorUtility.getEditorInputJavaElement(textEditor, true);
		if (input == null)
			return null;

		document= textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		IRegion wordRegion= JavaWordFinder.findWord(document, offset);
		if (wordRegion == null)
			return null;
		
		IProject project = null;
		
		project = input.getJavaProject().getProject();
		
		if(project == null)
			return null;
		
		CDICoreNature cdiNature = CDIUtil.getCDINatureWithProgress(project);
		if(cdiNature == null)
			return null;
		
		IJavaElement[] elements = null;
		
		try {
			elements = input.codeSelect(wordRegion.getOffset(), wordRegion.getLength());
			if (elements == null) 
				return null;
			
			if(elements.length != 1)
				return null;
			
			ArrayList<IHyperlink> hyperlinks = new ArrayList<IHyperlink>();
			int position = 0;
			if(elements[0] instanceof IType){
				elements[0] = input.getElementAt(wordRegion.getOffset());
				
				if(elements[0] == null)
					return null;
				
				
				if(elements[0] instanceof IMethod){
					position = offset;
				}
			}

			findInjectedBeans(cdiNature, elements[0], position, input.getPath(), hyperlinks, textEditor.isDirty());
			
			if (hyperlinks != null && !hyperlinks.isEmpty()) {
				return (IHyperlink[])hyperlinks.toArray(new IHyperlink[hyperlinks.size()]);
			}
		} catch (JavaModelException jme) {
			CDIExtensionsPlugin.getDefault().logError(jme);
		}
		return null;
	}
	
	protected void findInjectedBeans(CDICoreNature nature, IJavaElement element, int offset, IPath path, ArrayList<IHyperlink> hyperlinks, boolean dirty){
		ICDIProject cdiProject = CDIUtil.getCDIProject(file, nature, dirty);
		
		if(cdiProject == null){
			return;
		}
		
		Collection<IBean> beans = cdiProject.getBeans(path);
		
		IInjectionPoint injectionPoint = CDIUtil.findInjectionPoint(beans, element, offset);
		if(injectionPoint == null){
			return;
		}
		
		List<IBean> resultBeans = CDIUtil.getSortedBeans(cdiProject, true, injectionPoint);
		
		Collection<IBean> assignableBeans = cdiProject.getBeans(false, injectionPoint);
			
		if(assignableBeans.size() > 0){
			if(resultBeans.size() > 0){
				hyperlinks.add(new InjectedPointHyperlink(region, resultBeans.get(0), document, true));
			}
			if(assignableBeans.size() > 1) {
//				hyperlinks.add(new AlternativeInjectedPointListHyperlink(region, alternativeBeans, viewer, document));
				hyperlinks.add(new AssignableBeansHyperlink(region, injectionPoint, document));
			}
		}
	}
}
