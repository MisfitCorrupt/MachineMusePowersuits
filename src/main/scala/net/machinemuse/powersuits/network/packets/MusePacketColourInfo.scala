package net.machinemuse.powersuits.network.packets

import java.io.DataInputStream

import net.machinemuse.api.IModularItem
import net.machinemuse.numina.network.{MusePackager, MusePacket}
import net.machinemuse.utils.MuseItemUtils
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.nbt.NBTTagCompound


/**
 * Author: MachineMuse (Claire Semple)
 * Created: 12:28 PM, 5/6/13
 */
object MusePacketColourInfo extends MusePackager {
  def read(d: DataInputStream, p: EntityPlayer) = {
    val itemSlot = readInt(d)
    val tagData = readIntArray(d)
    new MusePacketColourInfo(p, itemSlot, tagData)
  }
}

class MusePacketColourInfo(player: EntityPlayer, itemSlot: Int, tagData: Array[Int]) extends MusePacket {
  val packager = MusePacketColourInfo

  def write {
    writeInt(itemSlot)
    writeIntArray(tagData)
  }

  override def handleServer(playerEntity: EntityPlayerMP) {
    val stack = playerEntity.inventory.getStackInSlot(itemSlot)
    if (stack != null && stack.getItem.isInstanceOf[IModularItem]) {
      val renderTag: NBTTagCompound = MuseItemUtils.getMuseRenderTag(stack)
      renderTag.setIntArray("colours", tagData)
    }
  }
}