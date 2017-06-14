/*******************************************************************************
 * Copyright (c) 2004,2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.data.engine.api;

import java.util.Collection;

/**
 * Describes an Array Collection of IBaseExpression, it will return an result
 * Array.
 * 
 */
public interface IExpressionCollection extends IBaseExpression
{

	/**
	 * Gets the expression collection.
	 */
	public Collection getExpressions( );
}
