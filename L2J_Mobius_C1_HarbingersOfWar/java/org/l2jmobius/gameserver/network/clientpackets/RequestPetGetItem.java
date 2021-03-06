/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.gameserver.enums.CreatureState;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.item.instance.Item;
import org.l2jmobius.gameserver.network.ClientThread;

public class RequestPetGetItem extends ClientBasePacket
{
	public RequestPetGetItem(byte[] decrypt, ClientThread client)
	{
		super(decrypt);
		final int objectId = readD();
		
		final Item item = (Item) World.getInstance().findObject(objectId);
		client.getActiveChar().getPet().setTarget(item);
		client.getActiveChar().getPet().setCurrentState(CreatureState.PICKUP_ITEM);
		client.getActiveChar().getPet().moveTo(item.getX(), item.getY(), item.getZ(), 0);
	}
}
