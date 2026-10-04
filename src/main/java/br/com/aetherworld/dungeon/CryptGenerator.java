package br.com.aetherworld.dungeon;
import br.com.aetherworld.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.*;
public final class CryptGenerator{
 public static void generate(ServerLevel l,BlockPos o){int x=o.getX(),z=o.getZ(),y=Math.max(-20,Math.min(20,o.getY()));for(int xx=x-10;xx<=x+10;xx++)for(int yy=y-1;yy<=y+7;yy++)for(int zz=z-18;zz<=z+18;zz++)l.setBlockAndUpdate(new BlockPos(xx,yy,zz),Blocks.AIR.defaultBlockState());room(l,new BlockPos(x,y,z-10),9,7,9);room(l,new BlockPos(x,y,z+10),15,9,15);l.setBlockAndUpdate(new BlockPos(x,y+1,z+10),AetherBlocks.GUARDIAN_SEAL.defaultBlockState());}
 private static void room(ServerLevel l,BlockPos c,int w,int h,int d){int minX=c.getX()-w/2,maxX=c.getX()+w/2,minY=c.getY(),maxY=c.getY()+h,minZ=c.getZ()-d/2,maxZ=c.getZ()+d/2;for(int x=minX;x<=maxX;x++)for(int y=minY;y<=maxY;y++)for(int z=minZ;z<=maxZ;z++){boolean b=x==minX||x==maxX||y==minY||y==maxY||z==minZ||z==maxZ;l.setBlockAndUpdate(new BlockPos(x,y,z),b?Blocks.STONE_BRICKS.defaultBlockState():Blocks.AIR.defaultBlockState());}}
}