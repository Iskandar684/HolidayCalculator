/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.script.internal.element;

import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IHighlightRule;
import org.eclipse.birt.report.model.api.HighlightRuleHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.eclipse.birt.report.model.api.core.IStructure;
import org.eclipse.birt.report.model.api.elements.structures.HighlightRule;
import org.eclipse.birt.report.model.api.simpleapi.SimpleElementFactory;

/**
 * Implements of HighLightRule.
 */

public class HighlightRuleImpl implements IHighlightRule
{

	private org.eclipse.birt.report.model.api.simpleapi.IHighlightRule highlightRuleImpl;

	/**
	 * Constructor
	 * 
	 * @param ruleHandle
	 */

	public HighlightRuleImpl( )
	{
		highlightRuleImpl = SimpleElementFactory.getInstance( )
				.createHighlightRule( );
	}

	/**
	 * Constructor
	 * 
	 * @param ruleHandle
	 */

	public HighlightRuleImpl( HighlightRuleHandle ruleHandle )
	{
		highlightRuleImpl = SimpleElementFactory.getInstance( )
				.createHighlightRule( ruleHandle );
	}

	/**
	 * Constructor
	 * 
	 * @param rule
	 * @param handle
	 */

	public HighlightRuleImpl( HighlightRule rule )
	{
		highlightRuleImpl = SimpleElementFactory.getInstance( )
				.createHighlightRule( rule );
	}

	public HighlightRuleImpl(
			org.eclipse.birt.report.model.api.simpleapi.IHighlightRule highlightRule )
	{
		highlightRuleImpl = highlightRule;
	}

	public String getColor( )
	{
		return highlightRuleImpl.getColor( );
	}

	public String getDateTimeFormat( )
	{
		return highlightRuleImpl.getDateTimeFormat( );
	}

	public String getFontStyle( )
	{
		return highlightRuleImpl.getFontStyle( );
	}

	public String getFontWeight( )
	{
		return highlightRuleImpl.getFontWeight( );
	}

	public String getStringFormat( )
	{
		return highlightRuleImpl.getStringFormat( );
	}

	public String getTestExpression( )
	{
		return highlightRuleImpl.getTestExpression( );
	}

	public void setColor( String color ) throws ScriptException
	{
		try
		{
			highlightRuleImpl.setColor( color );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}
	}

	public void setDateTimeFormat( String format ) throws ScriptException
	{
		try
		{
			highlightRuleImpl.setDateTimeFormat( format );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setFontStyle( String style ) throws ScriptException
	{
		try
		{

			highlightRuleImpl.setFontStyle( style );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setFontWeight( String weight ) throws ScriptException
	{
		try
		{

			highlightRuleImpl.setFontWeight( weight );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setStringFormat( String format ) throws ScriptException
	{
		try
		{
			highlightRuleImpl.setStringFormat( format );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setTestExpression( String expression ) throws ScriptException
	{
		try
		{
			highlightRuleImpl.setTestExpression( expression );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setValue1( String value1 ) throws ScriptException
	{
		try
		{

			highlightRuleImpl.setValue1( value1 );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setValue2( String value2 ) throws ScriptException
	{
		try
		{

			highlightRuleImpl.setValue2( value2 );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setOperator( String operator ) throws ScriptException
	{
		try
		{
			highlightRuleImpl.setOperator( operator );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public void setBackGroundColor( String color ) throws ScriptException
	{
		try
		{
			highlightRuleImpl.setBackGroundColor( color );
		}
		catch ( SemanticException e )
		{
			throw new ScriptException( e.getLocalizedMessage( ) );
		}

	}

	public IStructure getStructure( )
	{
		return highlightRuleImpl.getStructure( );
	}

	public String getBackGroundColor( )
	{
		return highlightRuleImpl.getBackGroundColor( );
	}

	public String getOperator( )
	{
		return highlightRuleImpl.getOperator( );
	}

	public String getValue1( )
	{
		return highlightRuleImpl.getValue1( );
	}

	public String getValue2( )
	{
		return highlightRuleImpl.getValue2( );
	}

}
