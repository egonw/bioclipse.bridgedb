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
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.managers.business.IBioclipseManager;

import org.apache.log4j.Logger;
import org.bridgedb.BridgeDb;
import org.bridgedb.DataSource;
import org.bridgedb.DataSourcePatterns;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperException;
import org.bridgedb.Xref;

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
    		String code = source.getSystemCode();
    		if (code != null && code.length() > 0) sourceCodes.add(code);
    	}
    	return sourceCodes;
    }

    public Set<String> search(String restService, String query, int limit) throws BioclipseException {
    	logger.debug("doing stuff...");

    	// now we connect to the driver and create a IDMapper instance.
    	IDMapper mapper;
		try {
			mapper = BridgeDb.connect(restService);
		} catch (IDMapperException exception) {
			throw new BioclipseException("Could not connect to the REST service at: " + restService);
		}

		return search(mapper, query, limit);
    }

    public Set<String> search(IDMapper database, String query, int limit) throws BioclipseException {
    	logger.debug("doing stuff...");

		try {
			return extractIdentifierStrings(database.freeSearch(query, limit));
		} catch (IDMapperException exception) {
			throw new BioclipseException("Could not search the REST service: " + exception);
		}
    }

    public Set<DataSource> guessIdentifierType(String identifier) throws BioclipseException {
    	Map<DataSource, Pattern> patterns = DataSourcePatterns.getPatterns();

    	Set<DataSource> sources = new HashSet<DataSource>();
    	for (DataSource source : patterns.keySet()) {
    	        Matcher matcher = patterns.get(source).matcher(identifier);
    	        if (matcher.matches()) sources.add(source);
    	}
    	return sources;
    }

    public Set<String> map(String restService, String identifier, String source) throws BioclipseException {
    	return map(restService, identifier, source, null);
    }

    public Set<String> map(IDMapper database, String identifier, String source) throws BioclipseException {
    	return map(database, identifier, source, null);
    }

    public Set<String> map(String restService, String identifier, String source, String target) throws BioclipseException {
    	logger.debug("doing stuff...");

    	// now we connect to the driver and create a IDMapper instance.
    	IDMapper mapper;
		try {
			mapper = BridgeDb.connect(restService);
		} catch (IDMapperException exception) {
			throw new BioclipseException("Could not connect to the REST service at: " + restService);
		}
		
		return map(mapper, identifier, source, target);
    }

    public Set<String> map(IDMapper database, String identifier, String source, String target) throws BioclipseException {
    	// We create an Xref instance for the identifier that we want to look up.
    	DataSource sourceObj = getSource(source);
    	Xref src = new Xref(identifier, sourceObj);

    	Set<Xref> dests;

    	// let's see if there are cross-references in the target database
    	if (target != null) {
        	DataSource targetObj = getSource(target);
    		try {
    			dests = database.mapID(src, targetObj);
    		} catch (IDMapperException exception) {
    			throw new BioclipseException(
    				"Error while mapping the identifier: " + exception.getMessage()
    			);
    		}
    	} else {
    		try {
    			dests = database.mapID(src);
    		} catch (IDMapperException exception) {
    			throw new BioclipseException(
    				"Error while mapping the identifier: " + exception.getMessage()
    			);
    		}
    	}

    	// and create a list of found, mapped URNs
    	return extractIdentifierStrings(dests);
    }

	private Set<String> extractIdentifierStrings(Set<Xref> dests) {
		Set<String> results = new HashSet<String>();
    	for (Xref dest : dests)
    	    results.add(dest.getURN());
		return results;
	}

	public Xref xref(String identifier, String source) throws BioclipseException {
		return new Xref(identifier, getSource(source));
	}

	public IDMapper loadRelationalDatabase(String location) throws BioclipseException {
		try {
			Class.forName ("org.bridgedb.rdb.IDMapperRdb");
		} catch (ClassNotFoundException exception) {
			throw new BioclipseException("Could not load the IDMapperRdb driver.", exception);
		}
		try {
			return BridgeDb.connect("idmapper-pgdb:" + location);
		} catch (IDMapperException exception) {
			throw new BioclipseException("Could not the database at this location: " + location, exception);
		}
	}
}
