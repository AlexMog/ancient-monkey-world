/* $Id: SoundEvent.java,v 1.6 2013/04/25 21:30:03 kiheru Exp $ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.events;

import games.stendhal.common.constants.Events;
import games.stendhal.common.constants.SoundLayer;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPEvent;
import marauroa.common.game.Definition.DefinitionClass;
import marauroa.common.game.Definition.Type;

/**
 * A sound.
 *
 * @author hendrik
 */
public class SoundEvent extends RPEvent {

	/**
	 * Creates the rpclass.
	 */
	public static void generateRPClass() {
		final RPClass rpclass = new RPClass(Events.SOUND);
		rpclass.add(DefinitionClass.ATTRIBUTE, "sound", Type.STRING);
		rpclass.add(DefinitionClass.ATTRIBUTE, "radius", Type.INT);
		rpclass.add(DefinitionClass.ATTRIBUTE, "volume", Type.INT);
		rpclass.add(DefinitionClass.ATTRIBUTE, "layer", Type.BYTE);
	}

	/**
	 * Creates a new sound event.
	 *
	 * @param sound name of sound to play
	 * @param layer 
	 */
	public SoundEvent(final String sound, SoundLayer layer) {
		super(Events.SOUND);
		put("sound", sound);
		put("layer", layer.ordinal());
	}


	/**
	 * creates a new sound event.
	 *
	 * @param sound name of sound to play
	 * @param radius radius
	 * @param volume volume
	 * @param layer layer (e. g. ambient sound)
	 */
	public SoundEvent(String sound, int radius, int volume, SoundLayer layer) {
		super(Events.SOUND);
		put("sound", sound);
		put("radius", radius);
		put("volume", volume);
		put("layer", layer.ordinal());
	}
}
