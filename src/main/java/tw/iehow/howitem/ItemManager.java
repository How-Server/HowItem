package tw.iehow.howitem;

import tw.iehow.howitem.items.*;
import tw.iehow.howitem.items.pen.*;
import tw.iehow.howitem.items.popsicle.*;
import tw.iehow.howitem.items.sword.*;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static final Map<Integer, BaseHowItem> itemMap = new HashMap<>();

    static {
        final BaseHowItem[] items = {
                new HowClown(),
                new HowCard(),
                new HowDrum(),
                new HowWine(),
                new WarHorn(),
                new Brush(),
                new Pencil(),
                new FountainPen(),
                new GoldenFountainPen(),
                new PopsicleStick(),
                new PopsicleChocolate(),
                new PopsicleSoda(),
                new PopsiclePudding(),
                new PopsicleStrawberry(),
                new BlackKatana(),
                new BlueKatana(),
                new Keyboard(),
                new SakuraKatana(),
                new HowStick(),
                new HowSnowman(),
                new HowWand(),
        };
        for (BaseHowItem item : items) {
            itemMap.put(item.getHash(), item);
        }
    }

    public static Map<Integer, BaseHowItem> getItems() {
        return itemMap;
    }
}
