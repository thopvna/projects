package com.thopv.projects.libraryforreader.support.utils;

import com.thopv.projects.libraryforreader.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by thopv on 12/4/2017.
 */

public class BookIconUtils {
    private static List<Integer> ids;
    private static Random random = new Random();
    public static int getRandomImageDrawableId(){
        if(ids == null){
            ids = new LinkedList<>();
            ids.add(R.drawable.book1);
            ids.add(R.drawable.book2);
            ids.add(R.drawable.book3);
            ids.add(R.drawable.book4);
            ids.add(R.drawable.book5);
            ids.add(R.drawable.book6);
            ids.add(R.drawable.book7);
            ids.add(R.drawable.book8);
            ids.add(R.drawable.book9);
            ids.add(R.drawable.book10);
            ids.add(R.drawable.book11);
            ids.add(R.drawable.book12);
            ids.add(R.drawable.book13);
            ids.add(R.drawable.book14);
            ids.add(R.drawable.book15);
            ids.add(R.drawable.book16);
            ids.add(R.drawable.book17);
            ids.add(R.drawable.book18);
            ids.add(R.drawable.book19);
            ids.add(R.drawable.book20);
        }
        return ids.get(random.nextInt(ids.size()));
    }
}
