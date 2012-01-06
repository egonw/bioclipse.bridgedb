/*******************************************************************************
 * Copyright (c) 2011  Egon Willighagen <egon.willighagen@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.bridgedb.business;

import java.util.Set;

import net.bioclipse.managers.business.IBioclipseManager;

import org.apache.log4j.Logger;
import org.bridgedb.BridgeDb;
import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.Xref;
import org.bridgedb.bio.BioDataSource;

public class BridgedbManager implements IBioclipseManager {

    private static final Logger logger = Logger.getLogger(BridgedbManager.class);

    /**
     * Gives a short one word name of the manager used as variable name when
     * scripting.
     */
    public String getManagerName() {
        return "bridgedb";
    }

    public String doStuff() throws Exception {
    	logger.debug("doing stuff...");
    	Class.forName("org.bridgedb.webservice.bridgerest.BridgeRest");
    	BioDataSource.init();

    	// now we connect to the driver and create a IDMapper instance.
    	IDMapper mapper = BridgeDb.connect ("idmapper-bridgerest:http://webservice.bridgedb.org/Human");

    	// We create an Xref instance for the identifier that we want to look up.
    	// In this case we want to look up Entrez gene 3643.
    	Xref src = new Xref ("3643", BioDataSource.ENTREZ_GENE);

    	// let's see if there are cross-references to Ensembl Human
    	Set<Xref> dests = mapper.mapID(src, DataSource.getBySystemCode("EnHs"));

    	// and print the results.
    	// with getURN we obtain valid MIRIAM urn's if possible.
    	StringBuffer results = new StringBuffer();
    	results.append(src.getURN() + " maps to:\n");
    	for (Xref dest : dests)
    	        results.append("  " + dest.getURN() + "\n");
    	
    	return results.toString();
    }
}
