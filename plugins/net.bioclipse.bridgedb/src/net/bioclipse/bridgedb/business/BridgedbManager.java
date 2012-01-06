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

import java.util.HashSet;
import java.util.Set;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.managers.business.IBioclipseManager;

import org.apache.log4j.Logger;
import org.bridgedb.BridgeDb;
import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperException;
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

    public DataSource getSource(String source) throws BioclipseException {
    	return DataSource.getBySystemCode(source);
    }

    public Set<String> listAllSources() {
    	Set<String> sourceCodes = new HashSet<String>();
    	for (DataSource source : DataSource.getDataSources()) {
    		sourceCodes.add(source.getSystemCode());
    	}
    	return sourceCodes;
    }

    public String map(String restService, String identifier, String source, String target) throws BioclipseException {
    	logger.debug("doing stuff...");

    	// now we connect to the driver and create a IDMapper instance.
    	IDMapper mapper;
		try {
			mapper = BridgeDb.connect(restService);
		} catch (IDMapperException exception) {
			throw new BioclipseException("Could not connect to the REST service at: " + restService);
		}

    	// We create an Xref instance for the identifier that we want to look up.
    	// In this case we want to look up Entrez gene 3643.
    	DataSource sourceObj = getSource(source);
    	Xref src = new Xref(identifier, sourceObj);

    	// let's see if there are cross-references to Ensembl Human
    	DataSource targetObj = getSource(target);
    	Set<Xref> dests;
		try {
			dests = mapper.mapID(src, targetObj);
		} catch (IDMapperException exception) {
			throw new BioclipseException(
				"Error while mapping the identifier: " + exception.getMessage()
			);
		}

    	// and print the results.
    	// with getURN we obtain valid MIRIAM urn's if possible.
    	StringBuffer results = new StringBuffer();
    	results.append(src.getURN() + " maps to:\n");
    	for (Xref dest : dests)
    	        results.append("  " + dest.getURN() + "\n");
    	
    	return results.toString();
    }
}
