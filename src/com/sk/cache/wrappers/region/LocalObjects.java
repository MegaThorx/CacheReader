package com.sk.cache.wrappers.region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sk.cache.wrappers.StreamedWrapper;
import com.sk.cache.wrappers.loaders.LocalObjectLoader;
import com.sk.datastream.Stream;

public class LocalObjects extends StreamedWrapper {

    private final List<LocalObject> objects = new ArrayList<>();
    @SuppressWarnings("unchecked")
    private final List<LocalObject> located[][][] = new List[4][64][64];

    public LocalObjects(LocalObjectLoader loader, int regionHash) {
        super(loader, regionHash);
    }

    @Override
    public void decode(Stream stream) {
        for (int id = -1, idOff = stream.getSmart(); idOff != 0; idOff = (stream.getLeft() > 0 ? stream.getSmart()
                : 0)) {
            id += idOff;
            for (int loc = 0, locOff = stream.getSmart(); locOff != 0; locOff = stream.getSmart()) {
                loc += locOff - 1;
                int plane = loc >> 12;
                int ly = loc & 0x3f;
                int lx = (loc >> 6) & 0x3f;
                int data = stream.getUByte();
                int type = data >> 2;
                int orientation = data & 0x3;
                final LocalObject object = new LocalObject(this.getLoader(), id, lx, ly, plane, type, orientation);
                final Dimension size = object.getSize();
                for (int x = 0; x < size.width; ++x) {
                    for (int y = 0; y < size.height; ++y) {
                        addObject(object, plane, lx + x, ly + y);
                    }
                }
            }
        }
    }

    private void addObject(LocalObject o) {
        addObject(o, o.plane, o.x, o.y);
    }

    private void addObject(LocalObject o, int plane, int x, int y) {
        objects.add(o);
        if (plane < located.length) {
            if (located[plane][x][y] == null)
                located[plane][x][y] = new ArrayList<>(3);
            located[plane][x][y].add(o);
        }
    }

    public List<LocalObject> getObjectsAt(int x, int y, int plane) {
        if (x < 0 || y < 0 || plane < 0 || plane >= located.length || x >= Region.width || y >= Region.height)
            return Arrays.asList();
        List<LocalObject> ret = located[plane][x][y];
        if (ret == null)
            return Arrays.asList();
        else
            return ret;
    }

    public List<LocalObject> getObjects() {
        return objects;
    }
}
