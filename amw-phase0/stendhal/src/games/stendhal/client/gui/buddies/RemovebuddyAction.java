/* $Id: RemovebuddyAction.java,v 1.4 2013/04/22 20:28:59 kiheru Exp $ */
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
package games.stendhal.client.gui.buddies;

import games.stendhal.client.actions.SlashActionRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class RemovebuddyAction implements ActionListener {
	private final String buddyName;

	RemovebuddyAction(final String buddyName) {
		this.buddyName = buddyName;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String [] args = new String [1];
		args[0] = buddyName;
		
		SlashActionRepository.get("remove").execute(args, null);
	}
}
