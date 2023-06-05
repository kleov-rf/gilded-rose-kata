package com.gildedrose;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    /*
    * - All items have a SellIn value which denotes the number of days we have to sell the item
    * - All items have a Quality value which denotes how valuable the item is
    * - At the end of each day our system lowers both values for every item
    * - Once the sell by date has passed, Quality degrades twice as fast
    * - The Quality of an item is never negative
    * - "Aged Brie" actually increases in Quality the older it gets
    * - The Quality of an item is never more than 50
    * - "Sulfuras", being a legendary item, never has to be sold and never decreases in Quality
    * - "Backstage passes", like aged brie, increases in Quality as it's SellIn value approaches;
    *   Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less
    *   but Quality drops to 0 after the concert
    *
    * We have recently signed a supplier of conjured items. This requires an update to our system:
    * "Conjured" items degrade in Quality twice as fast as normal items
    */

    @Test
    void golden_master(){
        // act
        String output = TexttestFixture.print(new String[]{});

        // assert
        Approvals.verify(output);
    }

    @Test
    void should_increase_quality_to_2_if_item_is_backstage_pass_with_sell_in_between_6_and_11_and_quality_less_than_50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 7, 0);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        int expectedQuality = 2;
        int actualQuality = item.quality;
        assertEquals(expectedQuality, actualQuality);
    }

    @Test
    void should_increase_quality_to_2_if_item_is_backstage_pass_with_sell_in_less_than_6_and_quality_less_than_50() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 0);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        int expectedQuality = 3;
        int actualQuality = item.quality;
        assertEquals(expectedQuality, actualQuality);
    }

    @Test
    void should_decrease_quality_1_point_if_item_is_neither_backstage_nor_sulfuras_nor_brie_with_quality_greater_than_0_and_sell_in_less_than_0() {
        Item item = new Item("Elixir of the Mongoose", -1, 1);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        int expectedQuality = 0;
        int actualQuality = item.quality;
        assertEquals(expectedQuality, actualQuality);
    }

    @Test
    void quality_should_not_be_higher_than_50() {
        Item item = new Item("Aged Brie", 0, 50);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        int expectedSellIn = -1;
        int expectedQuality = 50;

        app.updateQuality();
        int actualSellIn = item.sellIn;
        int actualQuality = item.quality;

        assertEquals(expectedSellIn, actualSellIn);
        assertEquals(expectedQuality, actualQuality);
    }

    @Test
    void quality_should_not_be_less_than_0() {
        Item item = new Item("Al's dog food", 0, 0);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        int expectedSellIn = -1;
        int expectedQuality = 0;

        app.updateQuality();
        int actualSellIn = item.sellIn;
        int actualQuality = item.quality;

        assertEquals(expectedSellIn, actualSellIn);
        assertEquals(expectedQuality, actualQuality);
    }

    @Test
    void should_not_change_if_item_is_sulfuras() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 0);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        int expectedSellIn = 0;
        int expectedQuality = 0;

        app.updateQuality();
        int actualSellIn = item.sellIn;
        int actualQuality = item.quality;

        assertEquals(expectedSellIn, actualSellIn);
        assertEquals(expectedQuality, actualQuality);
    }

    @Test
    void should_decrease_quality_by_one_if_common_item_has_more_than_one_day_left_to_sell() {
        Item item = new Item("Mom's Chanclas", 2, 3);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        int expectedSellIn = 1;
        int expectedQuality = 2;

        app.updateQuality();
        int actualSellIn = item.sellIn;
        int actualQuality = item.quality;

        assertEquals(expectedSellIn, actualSellIn);
        assertEquals(expectedQuality, actualQuality);
    }

    @Test
    void should_decrease_quality_by_two_if_common_item_has_no_days_left_to_sell() {
        Item item = new Item("Mom's Chanclas", 0, 3);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        int expectedSellIn = -1;
        int expectedQuality = 1;

        app.updateQuality();
        int actualSellIn = item.sellIn;
        int actualQuality = item.quality;

        assertEquals(expectedSellIn, actualSellIn);
        assertEquals(expectedQuality, actualQuality);
    }

}
