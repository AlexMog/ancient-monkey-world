/* $Id: QuestStateStartsWithCondition.java,v 1.15 2013/04/25 20:47:09 kiheru Exp $ */
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
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Does the quest state start with the specified String?
 */
@Dev(category=Category.CHAT, label="State?")
public class QuestStateStartsWithCondition implements ChatCondition {

	private final String questname;
	private final String state;

	/**
	 * Creates a new QuestStateStartsWithCondition.
	 *
	 * @param questname
	 *            name of quest-slot
	 * @param state
	 *            start of state-string
	 */
	public QuestStateStartsWithCondition(final String questname, final String state) {
		this.questname = questname;
		this.state = state;
	}

	@Override
	public boolean fire(final Player player, final Sentence sentence, final Entity entity) {
		return (player.hasQuest(questname) && player.getQuest(questname).startsWith(
				state));
	}

	@Override
	public String toString() {
		return "QuestStateStartsWith <" + questname + "," + state + ">";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false,
				QuestStateStartsWithCondition.class);
	}
}
