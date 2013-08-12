/*******************************************************************************
 * Copyright (c) 2012  Egon Willighagen <egon.willighagen@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * www.eclipse.org—epl-v10.html <http://www.eclipse.org/legal/epl-v10.html>
 * 
 * Contact: http://www.bioclipse.net/    
 ******************************************************************************/
package net.bioclipse.bridgedb.tests;

import net.bioclipse.managers.business.IBioclipseManager;

import org.junit.BeforeClass;

public class JavaScriptBridgedbManagerPluginTest
    extends AbstractBridgedbManagerPluginTest {

    @BeforeClass public static void setup() {
    	bridgedb = net.bioclipse.bridgedb.Activator.getDefault()
            .getJavaScriptBridgedbManager();
    }

	@Override
	public IBioclipseManager getManager() {
		return bridgedb;
	}
}
