/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.commons.network.PacketWriter;
import org.l2jmobius.gameserver.enums.InventorySlot;
import org.l2jmobius.gameserver.model.VariationInstance;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.itemcontainer.PlayerInventory;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExUserInfoEquipSlot extends AbstractMaskPacket<InventorySlot>
{
	private final Player _player;
	private final byte[] _masks = new byte[]
	{
		(byte) 0x00,
		(byte) 0x00,
		(byte) 0x00,
		(byte) 0x00,
		(byte) 0x00
	};
	
	public ExUserInfoEquipSlot(Player player)
	{
		this(player, true);
	}
	
	public ExUserInfoEquipSlot(Player player, boolean addAll)
	{
		_player = player;
		if (addAll)
		{
			addComponentType(InventorySlot.values());
		}
	}
	
	@Override
	protected byte[] getMasks()
	{
		return _masks;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_USER_INFO_EQUIP_SLOT.writeId(packet);
		packet.writeD(_player.getObjectId());
		packet.writeH(InventorySlot.values().length);
		packet.writeB(_masks);
		final PlayerInventory inventory = _player.getInventory();
		for (InventorySlot slot : InventorySlot.values())
		{
			if (containsMask(slot))
			{
				final VariationInstance augment = inventory.getPaperdollAugmentation(slot.getSlot());
				packet.writeH(22); // 10 + 4 * 3
				packet.writeD(inventory.getPaperdollObjectId(slot.getSlot()));
				packet.writeD(inventory.getPaperdollItemId(slot.getSlot()));
				packet.writeD(augment != null ? augment.getOption1Id() : 0);
				packet.writeD(augment != null ? augment.getOption2Id() : 0);
				packet.writeD(inventory.getPaperdollItemVisualId(slot.getSlot()));
			}
		}
		return true;
	}
}