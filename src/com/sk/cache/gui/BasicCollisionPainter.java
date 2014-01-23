package com.sk.cache.gui;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import com.sk.cache.dist.pack.SanitizedRegion;
import com.sk.cache.fs.CacheSystem;

@SuppressWarnings("serial")
public class BasicCollisionPainter extends ScalingGridPainter {

	public static void main(String... args) throws FileNotFoundException {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setContentPane(new BasicCollisionPainter(new CacheSystem(new File(
				"/Users/Strikeskids/jagexcache/Runescape/LIVE"))));
		frame.setVisible(true);
	}

	private Map<Integer, SoftReference<SanitizedRegion>> regions = new HashMap<>();
	private final CacheSystem cache;
	private int plane = 0;

	public BasicCollisionPainter(CacheSystem cache) {
		this.cache = cache;
	}

	@Override
	public Color getColor(CellPart part, int x, int y) {
		int flag = getFlag(x, y, plane);
		if ((flag & 0xff) == 0xff) {
			if (part == CellPart.CENTER) {
				return Color.red;
			} else
				return null;
		}

		switch (part) {
		case NORTH:
			if ((flag & 0x2) != 0)
				return Color.green;
			break;
		case EAST:
			if ((flag & 0x8) != 0)
				return Color.green;
			break;
		case SOUTH:
			if ((flag & 0x20) != 0)
				return Color.green;
			break;
		case WEST:
			if ((flag & 0x80) != 0)
				return Color.green;
			break;
		case NORTH_WEST:
			if ((flag & 0x1) != 0)
				return Color.blue;
			break;
		case NORTH_EAST:
			if ((flag & 0x4) != 0)
				return Color.blue;
			break;
		case SOUTH_EAST:
			if ((flag & 0x10) != 0)
				return Color.blue;
			break;
		case SOUTH_WEST:
			if ((flag & 0x40) != 0)
				return Color.blue;
			break;
		default:
			break;
		}
		return null;
	}

	private int getFlag(int x, int y, int plane) {
		SanitizedRegion region = getRegion(x, y);
		if (region == null)
			return 0xff;
		return region.getFlag(x & 0x3f, y & 0x3f, plane);
	}

	private SanitizedRegion getRegion(int x, int y) {
		return getRegion((x >>> 6) | (y >>> 6) << 7);
	}

	private SanitizedRegion getCachedRegion(int regionHash) {
		SoftReference<SanitizedRegion> ref = regions.get(regionHash);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private SanitizedRegion getRegion(int regionHash) {
		SanitizedRegion reg = getCachedRegion(regionHash);
		if (reg == null) {
			if (cache.regionLoader.canLoad(regionHash)) {
				SanitizedRegion loaded = new SanitizedRegion(cache.regionLoader.load(regionHash));
				regions.put(regionHash, new SoftReference<SanitizedRegion>(loaded));
				return loaded;
			}
		}
		return reg;
	}

}