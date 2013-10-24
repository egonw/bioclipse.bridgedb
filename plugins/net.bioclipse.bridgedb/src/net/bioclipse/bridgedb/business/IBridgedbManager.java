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

import net.bioclipse.core.PublishedClass;
import net.bioclipse.core.PublishedMethod;
import net.bioclipse.core.Recorded;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.managers.business.IBioclipseManager;

import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.Xref;
import org.bridgedb.bio.Organism;

@PublishedClass(
    value="BridgeDB.org support.",
    doi="10.1186/1471-2105-11-5"
)
public interface IBridgedbManager extends IBioclipseManager {

    @Recorded
    @PublishedMethod(
        methodSummary="Uses a BridgeDB REST service to map something identified into a source database " +
        		"to a target database.",
        params="String restService, String identifier, String source, String target"
    )
    public Set<String> map(String restService, String identifier, String source, String target) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Uses a BridgeDB REST service to map something identified into a source database " +
        		"to any other known database.",
        params="String restService, String identifier, String source"
    )
    public Set<String> map(String restService, String identifier, String source) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Uses a local BridgeDB database service to map something identified into a source database " +
        		"to a target database.",
        params="IDMapper database, String identifier, String source, String target"
    )
    public Set<String> map(IDMapper database, String identifier, String source, String target) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Uses a local BridgeDB database to map something identified into a source database " +
        		"to any other known database.",
        params="IDMapper database, String identifier, String source"
    )
    public Set<String> map(IDMapper database, String identifier, String source) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Searches a BridgeDB REST service for the given query string, if the service supports searching.",
        params="String restService, String query, int limit"
    )
    public Set<String> search(String restService, String query, int limit) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Searches a local BridgeDB instance for the given query string.",
        params="IDMapper database, String query, int limit"
    )
    public Set<String> search(IDMapper database, String query, int limit) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Return a BioDataSource for the given system code. It throws a BioclipseException when an" +
        		" unrecognized source String is passed.  Use listAllSources() to" +
        		" get a list of source codes.",
        params="String code"
    )
    public DataSource getSource(String code) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Return a BioDataSource for the given name. It throws a BioclipseException when an" +
        		" unrecognized name String is passed.",
        params="String name"
    )
    public DataSource getSourceFromName(String name) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Guesses the data source type from the given identifire using a set of predefined patterns.",
        params="String identifier"
    )
    public Set<DataSource> guessIdentifierType(String identifier) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="List all data sources."
    )
    public Set<String> listAllSources();

    @Recorded
    @PublishedMethod(
        methodSummary="List all organisms."
    )
    public Set<Organism> listAllOrganisms();

    @Recorded
    @PublishedMethod(
        methodSummary="List all IDMapper providers."
    )
    public Set<String> listIDMapperProviders();

    @Recorded
    @PublishedMethod(
        methodSummary="Get an IDMapper from a gven provider."
    )
    public IDMapper getIDMapper(String provider);
    
    @Recorded
    @PublishedMethod(
        methodSummary="Creates a BridgeBD Xref object from the given identifier and source. Use listAllSources() to" +
        		" get a list of source codes. An example is [\"3643\", \"L\"].",
        params="String identifier, String source"
    )
    public Xref xref(String identifier, String source);
    
    @Recorded
    @PublishedMethod(
        methodSummary="Creates a BridgeBD Xref object from the given identifier and source. An example " +
        		"is \"Wi:Aspirin\".",
        params="String sourcedIdentifier"
    )
    public Xref xref(String sourcedIdentifier);
    
    @Recorded
    @PublishedMethod(
        methodSummary="Loads a BridgeDB Rdb database from a file location.",
        params="String location"
    )
    public IDMapper loadRelationalDatabase(String location) throws BioclipseException;
}
