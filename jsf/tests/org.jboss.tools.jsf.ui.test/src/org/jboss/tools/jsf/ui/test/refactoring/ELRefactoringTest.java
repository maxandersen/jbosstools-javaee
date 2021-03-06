/*******************************************************************************
 * Copyright (c) 2012 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.jsf.ui.test.refactoring;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.j2ee.internal.common.classpath.J2EEComponentClasspathUpdater;
import org.jboss.tools.common.EclipseUtil;
import org.jboss.tools.test.util.ProjectImportTestSetup;

public class ELRefactoringTest  extends TestCase {
	static String jsfProjectName = "testJSFProject";
	static IProject jsfProject;
	
	public ELRefactoringTest(String name){
		super(name);
	}
	
	protected void setUp() throws Exception {
		loadProjects();
		List<IProject> projectList = new ArrayList<IProject>();
		projectList.add(jsfProject);
		J2EEComponentClasspathUpdater.getInstance().forceUpdate(projectList);
		loadProjects();
	}

	private void loadProjects() throws Exception {
		jsfProject = ProjectImportTestSetup.loadProject(jsfProjectName);
	}

	protected TestChangeStructure findChange(List<TestChangeStructure> changeList, IFile file){
		for(TestChangeStructure tcs : changeList){
			if(tcs.getFileName().equals("/"+file.getFullPath().removeFirstSegments(1).toString()))
				return tcs;
		}
		return null;
	}
	
	protected IType getJavaType(IProject project, String className){
		IJavaProject javaProject = EclipseUtil.getJavaProject(project);
		if(javaProject != null){
			try{
				return javaProject.findType(className);
			}catch(JavaModelException ex){
				fail(ex.getMessage());
			}
		}
		
		return null;
	}
	
	protected IMethod getJavaMethod(IProject project, String className, String methodName){
		IType type = getJavaType(project, className);
		if(type != null){
			return type.getMethod(methodName, new String[0]);
		}
		return null;
	}
	
	class TestChangeStructure{
		private IProject project;
		private String fileName;
		ArrayList<TestTextChange> textChanges = new ArrayList<TestTextChange>();
		

		public TestChangeStructure(IProject project, String fileName){
			this.project = project;
			this.fileName = fileName;
		}

		public IProject getProject(){
			return project;
		}

		public String getFileName(){
			return fileName;
		}
		
		public ArrayList<TestTextChange> getTextChanges(){
			return textChanges;
		}
		
		public void addTextChange(TestTextChange change){
			textChanges.add(change);
		}
		
		public int size(){
			return textChanges.size();
		}

	}
	
	class TestTextChange{
		private int offset;
		private int length;
		private String text;
		
		public TestTextChange(int offset, int length, String text){
			this.offset = offset;
			this.length = length;
			this.text = text;
		}
		
		public int getOffset(){
			return offset;
		}

		public int getLength(){
			return length;
		}

		public String getText(){
			return text;
		}
	}
}
