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

import net.bioclipse.managers.business.IBioclipseManager;

import org.apache.log4j.Logger;
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

    public String doStuff() {
    	logger.debug("doing stuff...");
    	Xref ref = new Xref("ENSG00000171105", BioDataSource.ENSEMBL_HUMAN);

    	return ref.getUrl();
    }
}
