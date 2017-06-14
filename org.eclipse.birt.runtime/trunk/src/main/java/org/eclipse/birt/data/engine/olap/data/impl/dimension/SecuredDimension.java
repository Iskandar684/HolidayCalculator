
/*******************************************************************************
 * Copyright (c) 2004, 2011 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.data.engine.olap.data.impl.dimension;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.data.engine.olap.data.document.IDocumentManager;
import org.eclipse.birt.data.engine.script.ScriptConstants;

/**
 * 
 */

public class SecuredDimension extends Dimension
{
	private static Logger logger = Logger.getLogger( Dimension.class.getName( ) );

	private Set<String> inaccessibleLevels = new HashSet<String>();

	SecuredDimension( String name, IDocumentManager documentManager, Set<String> notAccessibleLevels )
			throws IOException, DataException
	{
		Object[] params = {
				name, documentManager
		};
		logger.entering( SecuredDimension.class.getName( ),
				ScriptConstants.DIMENSION_SCRIPTABLE,
				params );
		this.name = name;
		this.documentManager = documentManager;
		this.inaccessibleLevels = notAccessibleLevels;
		loadFromDisk( );
		logger.exiting( SecuredDimension.class.getName( ),
				ScriptConstants.DIMENSION_SCRIPTABLE );
		
	}
	
	protected Hierarchy loadHierarchy( String hierarchyName )
	{
		return new SecuredHierarchy( documentManager, name, hierarchyName, this.inaccessibleLevels );
	}
}
