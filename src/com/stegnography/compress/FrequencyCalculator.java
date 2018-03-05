package com.stegnography.compress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

import com.stegnography.utils.Log;

public class FrequencyCalculator {
	private ArrayList<StatisticsEntry> items;
	private StatisticsEntry[] items2;

	public FrequencyCalculator(int size) {
		items = new ArrayList<StatisticsEntry>(size);
		items2 = new StatisticsEntry[size];
	}

	private boolean isBlocked = false;

	public void push(int value) {
		assert !isBlocked;
	

		if (items2[value] == null)
			items2[value] = new StatisticsEntry(value);
		items2[value].inc();
	}

	public void sort() {
		isBlocked = true;

		for (StatisticsEntry entry : items2) {
			if (entry != null)
				items.add(entry);
		}

	

		Collections.sort(items);
		Log.getInstance().log(Level.FINER, "FreqStatistics, sort(); Sorted items size = " + items.size());
	}

	public StatisticsTreeEntry buildTree() {
		return StatisticsTreeEntry.buildTree(items);
		// StatisticsTreeEntry treeRoot = null, minLeaf1, minLeaf2;
		// StatisticsEntry item1, item2;
		// while (items.size() > 1){
		// item1 = items.remove(0);
		// item2 = items.remove(0);
		// minLeaf1 = (item1 instanceof
		// StatisticsTreeEntry)?(StatisticsTreeEntry) item1:new
		// StatisticsTreeEntry(item1);
		// minLeaf2 = (item2 instanceof
		// StatisticsTreeEntry)?(StatisticsTreeEntry) item2:new
		// StatisticsTreeEntry(item2);
		// treeRoot = new StatisticsTreeEntry(minLeaf1, minLeaf2);
		//// System.out.println(items.size()+" items, leaf1="+minLeaf1+",
		// leaf2="+minLeaf2+", node="+treeRoot);
		// items.add(treeRoot);
		// Collections.sort(items);
		// }
		// items.clear();
		// return treeRoot;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (StatisticsEntry entry : items) {
			sb.append(entry != null ? entry.toString() + ", " : "null, ");
		}
		return sb.toString();
	}

	public void free() {
		items = null;
		items2 = null;
	}
}
