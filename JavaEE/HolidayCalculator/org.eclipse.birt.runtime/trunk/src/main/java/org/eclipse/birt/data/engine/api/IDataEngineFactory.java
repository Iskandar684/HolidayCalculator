/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.data.engine.api;

import org.eclipse.birt.core.exception.BirtException;

/**
 * Factory class to create an instance of DataEngine
 */
public interface IDataEngineFactory
{

	/**
	 * the extension point used to create the factory object.
	 * 
	 * @see org.eclipse.birt.core.framework.Platform#createFactoryObject(String)
	 */
	static final String EXTENSION_DATA_ENGINE_FACTORY = "org.eclipse.birt.data.DataEngineFactory";

	/**
	 * create a new report engine object.
	 * 
	 * @param DataEngineContext
	 *            context used to create the data engine.
	 * @return the data engine object
	 */
	DataEngine createDataEngine( DataEngineContext context ) throws BirtException;

}
