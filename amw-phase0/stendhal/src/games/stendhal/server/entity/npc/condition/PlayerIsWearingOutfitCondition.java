/* $Id: PlayerIsWearingOutfitCondition.java,v 1.6 2013/04/25 20:47:09 kiheru Exp $ */
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
package games.stendhal.server.entity.npc.condition;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.annotations.Dev;
import games.stendhal.server.core.config.annotations.Dev.Category;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Does the player wear (at least a part of) the specified outfit?
 *
 * @author jackrabbit
 */
@Dev(category=Category.OUTFIT, label="Outfit?")
public class PlayerIsWearingOutfitCondition implements ChatCondition{

	private final Outfit outfitToCheck;

	/**
	 * Creates a new PlayerIsWearingOutfitCondition
	 *
	 * @param outfit
	 * 			an outfit to be checked if it is worn by the player
	 */

	public PlayerIsWearingOutfitCondition(Outfit outfit) {
		this.outfitToCheck = outfit;
	}

	@Override
	public boolean fire(final Player player, final Sentence sentence, final Entity npc) {
		final Outfit players_outfit = player.getOutfit();
		return this.outfitToCheck.isPartOf(players_outfit);
	}

	@Override
	public String toString() {
		return "Player is wearing " + this.outfitToCheck.getCode() + " ?";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false,
				PlayerIsWearingOutfitCondition.class);
	}
}
