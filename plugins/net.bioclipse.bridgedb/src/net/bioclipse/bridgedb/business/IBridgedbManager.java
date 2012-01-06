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

@PublishedClass(
    value="BridgeDB.org support."
)
public interface IBridgedbManager extends IBioclipseManager {

    @Recorded
    @PublishedMethod(
        methodSummary="Uses a BridgeDB REST service to map something identified into a source database " +
        		"to a target database.",
        params="String restService, String identifier, String source, String target"
    )
    public String map(String restService, String identifier, String source, String target) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="Return a BioDataSource for the given string. It throws a BioclipseException when an" +
        		" unrecognized source String is passes.",
        params="String source"
    )
    public DataSource getSource(String source) throws BioclipseException;

    @Recorded
    @PublishedMethod(
        methodSummary="List all data sources."
    )
    public Set<String> listAllSources();

}
