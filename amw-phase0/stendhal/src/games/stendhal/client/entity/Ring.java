/* $Id: Ring.java,v 1.14 2009/08/13 21:14:33 nhnb Exp $ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.entity;

import marauroa.common.game.RPObject;

/**
 * A ring entity.
 */
public class Ring extends Item {
	/**
	 * Working property.
	 */
	public static final Property PROP_WORKING = new Property();

	/**
	 * Whether the ring is currently working.
	 */
	private boolean working;

	/**
	 * Create a Ring entity.
	 */
	public Ring() {
	}

	//
	// Ring
	//

	/**
	 * Determine if a ring is working.
	 * 
	 * @return <code>true</code> if a ring is working.
	 */
	public boolean isWorking() {
		return working;
	}

	//
	// Entity
	//

	/**
	 * Initialize this entity for an object.
	 * 
	 * @param object
	 *            The object.
	 * 
	 * @see #release()
	 */
	@Override
	public void initialize(final RPObject object) {
		super.initialize(object);

		/*
		 * A ring works either by not having amount or having amount > 0
		 */
		if (object.has("amount")) {
			working = object.getInt("amount") > 0;
		} else {
			working = true;
		}
	}

	//
	// RPObjectChangeListener
	//

	/**
	 * The object added/changed attribute(s).
	 * 
	 * @param object
	 *            The base object.
	 * @param changes
	 *            The changes.
	 */
	@Override
	public void onChangedAdded(final RPObject object, final RPObject changes) {
		super.onChangedAdded(object, changes);

		if (changes.has("amount")) {
			/*
			 * A ring works either by not having amount of having amount > 0
			 */
			working = changes.getInt("amount") > 0;
			fireChange(PROP_WORKING);
		}
	}

	/**
	 * The object removed attribute(s).
	 * 
	 * @param object
	 *            The base object.
	 * @param changes
	 *            The changes.
	 */
	@Override
	public void onChangedRemoved(final RPObject object, final RPObject changes) {
		super.onChangedRemoved(object, changes);

		if (changes.has("amount")) {
			working = true;
			fireChange(PROP_WORKING);
		}
	}
}
