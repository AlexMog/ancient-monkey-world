/* $Id: StringPrefixFilter.java,v 1.4 2013/04/22 20:37:52 kiheru Exp $ */
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
package games.stendhal.client.gui.chattext;

import games.stendhal.common.filter.CollectionFilter;
import games.stendhal.common.filter.FilterCriteria;

import java.util.Locale;

class StringPrefixFilter extends CollectionFilter<String> {
	public StringPrefixFilter(final String prefix) {
		this.addFilterCriteria(new FilterCriteria<String>() {
			@Override
			public boolean passes(final String o) {
				return prefix.length() > 0 && o.toLowerCase(Locale.ENGLISH).startsWith(prefix.toLowerCase(Locale.ENGLISH));
			}
		});	
	}
}
