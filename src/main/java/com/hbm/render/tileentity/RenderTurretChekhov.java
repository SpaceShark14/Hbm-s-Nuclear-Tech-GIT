package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.turret.TileEntityTurretChekhov;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTurretChekhov extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 1D, y, z + 1D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.turret_base_tex);
		ResourceManager.turret_chekhov.renderPart("Base");
		
		TileEntityTurretChekhov turret = (TileEntityTurretChekhov)te;
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);
		
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.turret_carriage_tex);
		ResourceManager.turret_chekhov.renderPart("Carriage");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(pitch, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_chekhov_tex);
		ResourceManager.turret_chekhov.renderPart("Body");
		
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(System.currentTimeMillis() % 360, -1, 0, 0);
		GL11.glTranslated(0, -1.5, 0);
		bindTexture(ResourceManager.turret_chekhov_barrels_tex);
		ResourceManager.turret_chekhov.renderPart("Barrels");

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
