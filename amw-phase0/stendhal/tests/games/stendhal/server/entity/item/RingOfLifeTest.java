/* $Id: RingOfLifeTest.java,v 1.8 2013/05/19 19:40:50 kiheru Exp $ */
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
package games.stendhal.server.entity.item;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import games.stendhal.server.maps.MockStendlRPWorld;

import marauroa.common.Log4J;

import org.junit.BeforeClass;
import org.junit.Test;

import utilities.RPClass.ItemTestHelper;

public class RingOfLifeTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
		
	}
	/**
	 * Tests for describe.
	 */
	@Test
	public void testDescribe() {
		final RingOfLife ring = new RingOfLife();
		assertThat(ring.isBroken(), is(false));
		assertThat(ring.describe(), is("You see an §'emerald ring', known as the ring of life. Wear it, and you risk less from death."));
		
		ring.damage();
		assertThat(ring.isBroken(), is(true));
		assertThat(ring.describe(), is("You see an §'emerald ring', known as the ring of life. The gleam is lost from the stone and it has no powers."));
	}

	/**
	 * Tests for onUsed.
	 */
	@Test
	public void testOnUsed() {
		final RingOfLife ring = new RingOfLife();
		assertThat(ring.isBroken(), is(false));
		assertThat(ring.describe(), is("You see an §'emerald ring', known as the ring of life. Wear it, and you risk less from death."));
		
		ring.onUsed(null);
		assertThat(ring.isBroken(), is(false));
		assertThat(ring.describe(), is("You see an §'emerald ring', known as the ring of life. Wear it, and you risk less from death."));
	}

	

	/**
	 * Tests for repair.
	 */
	@Test
	public void testRepair() {
		final RingOfLife ring = new RingOfLife();
		assertThat(ring.isBroken(), is(false));
		assertThat(ring.describe(), is("You see an §'emerald ring', known as the ring of life. Wear it, and you risk less from death."));
		
		ring.damage();
		assertThat(ring.isBroken(), is(true));
		assertThat(ring.describe(), is("You see an §'emerald ring', known as the ring of life. The gleam is lost from the stone and it has no powers."));
	
		ring.repair();
		assertThat(ring.isBroken(), is(false));
		assertThat(ring.describe(), is("You see an §'emerald ring', known as the ring of life. Wear it, and you risk less from death."));
	}

}
