package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.turret.TileEntityTurretTauon;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTurretTauon extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 1D, y, z + 1D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.turret_base_tex);
		ResourceManager.turret_chekhov.renderPart("Base");
		
		TileEntityTurretTauon turret = (TileEntityTurretTauon)te;
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_tex);
		ResourceManager.turret_chekhov.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_tauon_tex);
		ResourceManager.turret_tauon.renderPart("Cannon");

		if(turret.target != null && turret.aligned && System.currentTimeMillis() % 500 < 200) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.5D, 0);
			Vec3 pos = turret.getTurretPos();
			Vec3 ent = turret.getEntityPos(turret.target);
			double length = Vec3.createVectorHelper(ent.xCoord - pos.xCoord, ent.yCoord - pos.yCoord, ent.zCoord - pos.zCoord).lengthVector();
			BeamPronter.prontBeam(Vec3.createVectorHelper(length, 0, 0), EnumWaveType.RANDOM, EnumBeamType.LINE, 0xffa200, 0xffd000, (int)te.getWorldObj().getTotalWorldTime() / 5 % 360, (int)length + 1, 0.1F, 0, 0);
			GL11.glPopMatrix();
		}
		
		GL11.glTranslated(0, 1.375, 0);
		GL11.glRotated((te.getWorldObj().getTotalWorldTime() + interp) * 15, -1, 0, 0);
		GL11.glTranslated(0, -1.375, 0);
		ResourceManager.turret_tauon.renderPart("Rotor");

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
