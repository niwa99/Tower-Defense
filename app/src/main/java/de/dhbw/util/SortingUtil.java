package de.dhbw.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import de.dhbw.map.objects.enemy.AEnemy;

public class SortingUtil {
	
	public SortingUtil() {
		
	}

    /**
     * The given map is sorted by value. We need this to get the nearest AEnemy for the towers to shoot.
     * @param unsortMap
     * @return sorted List of enemies
     */
	public static List<AEnemy> getSortedListBySortingMapByValue(Map<AEnemy, Integer> unsortMap) {
        List<Map.Entry<AEnemy, Integer>> list =
                new LinkedList<Map.Entry<AEnemy, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<AEnemy, Integer>>() {
            public int compare(Map.Entry<AEnemy, Integer> o1, Map.Entry<AEnemy, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        List<AEnemy> result = new ArrayList<AEnemy>();
        for (Map.Entry<AEnemy, Integer> entry : list) {
            result.add(entry.getKey());
        }

        return result;
    }

    public static List<AEnemy> sortListByMovedSteps(List<AEnemy> enemies, AEnemy enemy, boolean greater){
	    if(greater){
	        return enemies.stream()//
                .filter(e -> e.getMovedSteps()>enemy.getMovedSteps())//
                .filter(e -> e.getMovedSteps() > 0)//
                .sorted(Comparator.comparingInt(AEnemy::getMovedSteps))
                .collect(Collectors.toList());
        }
	    return enemies.stream()//
            .filter(e -> e.getMovedSteps()<=enemy.getMovedSteps())//
            .filter(e -> e.getMovedSteps() > 0)//
            .sorted(Comparator.comparingInt(AEnemy::getMovedSteps).reversed())
            .collect(Collectors.toList());
    }
	
	
	//could be usefull later 
	public <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> unsortMap) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
	
}
