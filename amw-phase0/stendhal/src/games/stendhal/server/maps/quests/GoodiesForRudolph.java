/* $Id: GoodiesForRudolph.java,v 1.6 2012/12/15 11:21:53 kymara Exp $ */
/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.DropItemAction;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.SetQuestAndModifyKarmaAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * QUEST: Christmas Goodies For Rudolph
 *
 * PARTICIPANTS:
 * <ul>
 * <li>Rudolph (the Red-Nosed Reindeer) - walking around Semos during Christmas season.</li>
 * </ul>
 *
 * STEPS:
 * <ul>
 * <li>Rudolph asks you for some reindeer moss, carrots and apples.</li>
 * <li>You get his goodies by collecting them from around Semos..</li>
 * <li>Rudolph sees you have collected goodies and asks for them and then thanks you.</li>
 * </ul>
 *
 * REWARD:
 * <ul>
 * <li>100 XP</li>
 * <li>50 gold</li>
 * <li>Karma: 60</li>
 * <li>snowglobe</li>
 * </ul>
 *
 * REPETITIONS:
 * <ul>
 * <li>None</li>
 * </ul>
 */
public class GoodiesForRudolph extends AbstractQuest {

	private static final String QUEST_SLOT = "goodies_rudolph";

	

	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}
		res.add("I have met Rudolph. He is the Red-Nosed Reindeer running around in Semos.");
		final String questState = player.getQuest(QUEST_SLOT);
		if ("rejected".equals(questState)) {
			res.add("He asked me to find goodies for him but I rejected his request.");
		}
		if (player.isQuestInState(QUEST_SLOT, "start", "done")) {
			res.add("I promised to find goodies for him because he is a nice reindeer.");
		}
		if (("start".equals(questState) && (player.isEquipped("reindeer moss", 5)  && player.isEquipped("carrot", 10) && player.isEquipped("apple", 10))) || "done".equals(questState)) {
			res.add("I got all the goodies and will take them to Rudolph.");
		}
		if ("done".equals(questState)) {
			res.add("I took the goodies to Rudolph. As a little thank you, he gave ME some goodies. :)");
		}
		return res;
	}

	private void prepareRequestingStep() {
		final SpeakerNPC npc = npcs.get("Rudolph");

		npc.add(
			ConversationStates.ATTENDING,
			ConversationPhrases.QUEST_MESSAGES,
			new QuestNotCompletedCondition(QUEST_SLOT),
			ConversationStates.QUEST_OFFERED, 
			"I want some delicious goodies only you can help me get. Do you think you can help me?",
			null);

		npc.add(
			ConversationStates.ATTENDING,
			ConversationPhrases.QUEST_MESSAGES,
			new QuestCompletedCondition(QUEST_SLOT),
			ConversationStates.ATTENDING, 
			"Thank you very much for the goodies, but I don't have any other task for you this year. Have a wonderful holiday season.",
			null);

		// player is willing to help
		npc.add(
			ConversationStates.QUEST_OFFERED,
			ConversationPhrases.YES_MESSAGES,
			null,
			ConversationStates.ATTENDING,
			"I heard about the wonderful #goodies you have here in Semos. If you get 5 reindeer moss, 10 apples and 10 carrots, I'll give you a reward.",
			new SetQuestAndModifyKarmaAction(QUEST_SLOT, "start", 5.0));

		// player is not willing to help
		npc.add(
			ConversationStates.QUEST_OFFERED,
			ConversationPhrases.NO_MESSAGES, null,
			ConversationStates.ATTENDING,
			"Well, then I guess I'll just ask someone else for them. Woe is me.",
			new SetQuestAndModifyKarmaAction(QUEST_SLOT, "rejected", -5.0));

		// player wants to know what goodies he is referring to
		npc.add(
			ConversationStates.ATTENDING,
			Arrays.asList("goodies"),
			null,
			ConversationStates.ATTENDING,
			"Reindeer moss is a pale green patch of wonderfulness which grows all around this city. Apples are found at the farm to the east of the city, and carrots are to the northeast of the city.",
			null);
	}

	private void prepareBringingStep() {
		final SpeakerNPC npc = npcs.get("Rudolph");

		// player returns while quest is still active
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
			new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
				new QuestInStateCondition(QUEST_SLOT, "start"),
				new AndCondition(
					new PlayerHasItemWithHimCondition("reindeer moss", 5),
					new PlayerHasItemWithHimCondition("apple", 10),
					new PlayerHasItemWithHimCondition("carrot", 10))),
			ConversationStates.QUEST_ITEM_BROUGHT, 
			"Excuse me, please! I see you have delicious goodies. Are they for me?",
			null);

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
			new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
				new QuestInStateCondition(QUEST_SLOT, "start"), 
				new NotCondition(new AndCondition(
					new PlayerHasItemWithHimCondition("reindeer moss", 5),
					new PlayerHasItemWithHimCondition("apple", 10),
					new PlayerHasItemWithHimCondition("carrot", 10)))),
			ConversationStates.ATTENDING, 
			"Oh my. I am so in anticipation of those goodies which I have asked you for. Hopefully it will not be much longer before you can bring them to me.",
			null);

		final List<ChatAction> reward = new LinkedList<ChatAction>();
		reward.add(new EquipItemAction("money", 50));
		reward.add(new EquipItemAction("snowglobe"));
		reward.add(new IncreaseXPAction(100));
		reward.add(new SetQuestAction(QUEST_SLOT, "done"));
		reward.add(new IncreaseKarmaAction(60));

		final List<ChatAction> reward1 = new LinkedList<ChatAction>(reward);
		reward1.add(new DropItemAction("reindeer moss", 5));
		reward1.add(new DropItemAction("carrot", 10));
		reward1.add(new DropItemAction("apple", 10));

		
		
		
		
		npc.add(
			ConversationStates.QUEST_ITEM_BROUGHT,
			ConversationPhrases.YES_MESSAGES,
			// make sure the player isn't cheating by putting the goodies
			// away and then saying "yes"
			
			new AndCondition(
					new PlayerHasItemWithHimCondition("reindeer moss", 5),
					new PlayerHasItemWithHimCondition("apple", 10),
					new PlayerHasItemWithHimCondition("carrot", 10)),

			ConversationStates.ATTENDING, "Oh, I am so excited! I have wanted to eat these things for so long. Thanks so much. And to borrow a phrase, Ho Ho Ho, Merry Christmas.",
			new MultipleActions(reward1));


		npc.add(
			ConversationStates.QUEST_ITEM_BROUGHT,
			ConversationPhrases.NO_MESSAGES,
			null,
			ConversationStates.ATTENDING,
			"Well then, I certainly hope you find those goodies before I pass out from hunger.",
			null);
	}

	@Override
	public void addToWorld() {
		super.addToWorld();
		fillQuestInfo(
				"Goodies for Rudolph",
				"Rudolph, Santa's favorite reindeer, desperately wants some goodies.",
				false);
		prepareRequestingStep();
		prepareBringingStep();
	}

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	@Override
	public String getName() {
		return "GoodiesForRudolph";
	}
	
	@Override
	public int getMinLevel() {
		return 0;
	}
	
	@Override
	public String getRegion() {
		return Region.SEMOS_CITY;
	}
	
	@Override
	public String getNPCName() {
		return "Rudolph";
	}
}
