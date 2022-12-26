package mapTests;

import world.map.Map;
import org.junit.Assert;
import org.junit.Test;
import world.map.MapBuilder;


public class MapTest {

    @Test
    public void testSimpleOnMap() {
        Map grid = new Map(new MapBuilder());

        Assert.assertEquals(grid.getTileByNeighbor(0,0).equals("brick_hole_e"), true);
    }

    @Test
    public void testOnMapCheckWhenAllStones() {
        Map grid = new Map(new MapBuilder());
        grid.seaLevel = 0;
        grid.buildingLevel = 1;

        grid.generateTypes();

        Assert.assertEquals(grid.getTileByNeighbor(30,30), "brick_corner_c");
    }

    @Test
    public void testOnMapCheckWhenAllStonesNeighbor() {
        Map grid = new Map(new MapBuilder());
        grid.seaLevel = 0;
        grid.buildingLevel = 1;

        grid.generateTypes();

        Assert.assertEquals(grid.getWallNeigh(0,0), 0);
    }

    @Test
    public void testOnMapCheckWhenWallNeighTenByTen() {
        Map grid = new Map(new MapBuilder());
        grid.seaLevel = 0;
        grid.buildingLevel = 1;

        grid.generateTypes();

        Assert.assertEquals(grid.getWallNeigh(10,10), 9);
    }

    @Test
    public void testOnMapTenByTen() {
        Map grid = new Map(new MapBuilder());
        grid.seaLevel = 0;
        grid.buildingLevel = 1000;

        grid.generateTypes();

        Assert.assertEquals(grid.getWallNeigh(10,10), 0);
    }

}
