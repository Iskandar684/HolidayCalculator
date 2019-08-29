/***********************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.layout;

import org.eclipse.birt.report.engine.layout.area.IArea;

public interface IStackingLayoutManager extends ILayoutManager, ILayoutContext
{

	/**
	 * The method is called by children layout manager. The child layout manager
	 * submits the results of layout to parent
	 * 
	 * @param area
	 * @param keepWithPrevious 
	 * @param keepWithNext 
	 * @return true if submit succeeded
	 */
	boolean addArea( IArea area, boolean keepWithPrevious, boolean keepWithNext );

	/**
	 * Identify if current page is empty
	 * 
	 * @return
	 */
	boolean isPageEmpty( );

}