package org.brutality.api.store;

import me.apachenick.host.AbstractVirtualHost;

/**
 * 
 * @author Nick Hartskeerl <apachenick@hotmail.com>
 *
 */
public class StoreVirtualHost extends AbstractVirtualHost {

	/**
	 * 
	 */
	public static final String HOST_NAME = "store.noxiouspk.com";
	
	/**
	 * 
	 */
	public StoreVirtualHost() {
		super(HOST_NAME);
		setDirectoryListingEnabled(false);
	}
	
}
