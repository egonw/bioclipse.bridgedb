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
import org.bridgedb.Xref;

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
        methodSummary="Searches a BridgeDB REST service for the given query string, if the service supports searching.",
        params="String restService, String query, int limit"
    )
    public Set<String> search(String restService, String query, int limit) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Return a BioDataSource for the given string. It throws a BioclipseException when an" +
        		" unrecognized source String is passed.  Use listAllSources() to" +
        		" get a list of source codes.",
        params="String source"
    )
    public DataSource getSource(String source) throws BioclipseException;

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
        methodSummary="Creates a BridgeBD Xref object from the given identifier and source. Use listAllSources() to" +
        		" get a list of source codes.",
        params="String identifier, String source"
    )
    public Xref xref(String identifier, String source);

}
