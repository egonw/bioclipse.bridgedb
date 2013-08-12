/*******************************************************************************
 * Copyright (c) 2012  Egon Willighagen <egon.willighagen@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.bridgedb.tests;

import java.util.Set;

import net.bioclipse.bridgedb.business.IBridgedbManager;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.tests.AbstractManagerTest;
import net.bioclipse.managers.business.IBioclipseManager;

import org.bridgedb.Xref;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractBridgedbManagerPluginTest
extends AbstractManagerTest {

    protected static IBridgedbManager bridgedb;
    
    @Test
    public void testMap() throws BioclipseException {
    	Set<String> mappedIDs = bridgedb.map("idmapper-bridgerest:http://webservice.bridgedb.org/Human", "3643", "L");
    	Assert.assertNotNull(mappedIDs);
    	Assert.assertNotSame(0, mappedIDs.size());
    	Assert.assertTrue(mappedIDs.contains("urn:miriam:pdb:1RQQ"));
    }

    @Test
    public void testXref() {
    	Xref xref = bridgedb.xref("50-00-0", "Ca");
    	Assert.assertNotNull(xref);
    	Assert.assertEquals("Ca", xref.getDataSource().getSystemCode());
    	Assert.assertEquals("50-00-0", xref.getId());
    }
    
    public Class<? extends IBioclipseManager> getManagerInterface() {
    	return IBridgedbManager.class;
    }
}
