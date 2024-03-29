/* $Id: QuestNotCompletedConditionTest.java,v 1.14 2013/04/27 08:34:32 kiheru Exp $ */
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import games.stendhal.common.parser.ConversationParser;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;
import utilities.SpeakerNPCTestHelper;

public class QuestNotCompletedConditionTest {
	@BeforeClass
	public static void setUpClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
	}

	@AfterClass
	public static void teardownAfterClass() throws Exception {
		MockStendlRPWorld.reset();
	}


	/**
	 * Tests for fire.
	 */
	@Test
	public final void testFire() {
		assertTrue(new QuestNotCompletedCondition("questname").fire(
				PlayerTestHelper.createPlayer("player"),
				ConversationParser.parse("testQuestNotCompletedCondition"),
				SpeakerNPCTestHelper.createSpeakerNPC()));
		final Player bob = PlayerTestHelper.createPlayer("player");

		bob.setQuest("questname", "");
		assertTrue(new QuestNotCompletedCondition("questname").fire(bob,
				ConversationParser.parse("testQuestNotCompletedCondition"),
				SpeakerNPCTestHelper.createSpeakerNPC()));

		bob.setQuest("questname", null);
		assertTrue(new QuestNotCompletedCondition("questname").fire(bob,
				ConversationParser.parse("testQuestNotCompletedCondition"),
				SpeakerNPCTestHelper.createSpeakerNPC()));

		bob.setQuest("questname", "done");
		assertFalse(new QuestNotCompletedCondition("questname").fire(bob,
				ConversationParser.parse("testQuestNotCompletedCondition"),
				SpeakerNPCTestHelper.createSpeakerNPC()));

	}

	/**
	 * Tests for questNotCompletedCondition.
	 */
	@Test
	public final void testQuestNotCompletedCondition() {
		new QuestNotCompletedCondition("questname");
	}

	/**
	 * Tests for toString.
	 */
	@Test
	public final void testToString() {
		assertEquals("QuestNotCompleted <questname>",
				new QuestNotCompletedCondition("questname").toString());
	}

	/**
	 * Tests for equals.
	 */
	@Test
	public void testEquals() {
		assertFalse(new QuestNotCompletedCondition("questname").equals(null));

		final QuestNotCompletedCondition obj = new QuestNotCompletedCondition(
				"questname");
		assertTrue(obj.equals(obj));
		assertTrue(new QuestNotCompletedCondition("questname").equals(new QuestNotCompletedCondition(
				"questname")));
		assertTrue(new QuestNotCompletedCondition(null).equals(new QuestNotCompletedCondition(
				null)));

		assertFalse(new QuestNotCompletedCondition(null).equals(new QuestNotCompletedCondition(
				"questname")));
		assertFalse(new QuestNotCompletedCondition("questname").equals(new QuestNotCompletedCondition(
				null)));
		assertFalse(new QuestNotCompletedCondition("questname").equals(new Object()));

		assertTrue(new QuestNotCompletedCondition("questname").equals(new QuestNotCompletedCondition(
				"questname") {
			// this is an anonymous sub class
		}));
	}

	/**
	 * Tests for hashCode.
	 */
	@Test
	public void testHashCode() {
		final QuestNotCompletedCondition obj = new QuestNotCompletedCondition(
				"questname");

		assertEquals(obj.hashCode(), obj.hashCode());
		assertEquals(new QuestNotCompletedCondition("questname").hashCode(),
				new QuestNotCompletedCondition("questname").hashCode());
		assertEquals(new QuestNotCompletedCondition(null).hashCode(),
				new QuestNotCompletedCondition(null).hashCode());

	}

}
