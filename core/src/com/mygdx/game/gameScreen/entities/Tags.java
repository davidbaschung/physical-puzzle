package com.mygdx.game.gameScreen.entities;

import com.mygdx.game.gameScreen.entities.Components.Pivotable;
import com.mygdx.game.gameScreen.entities.Systems.PivotableSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class Tags {
    public static HashMap<String, Class> touchables = new HashMap<String, Class>();
    public static HashMap<String, Class> untouchables = new HashMap<String, Class>();
    public static ArrayList<Class> systemsClasses = new ArrayList();
    public Tags() {
        touchables.put("pivotable", Pivotable.class);

        systemsClasses.add(PivotableSystem.class);
    }
}
