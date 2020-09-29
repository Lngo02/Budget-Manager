package budget;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Purchases{
    private enum CATEGORY {FOOD, CLOTHES, ENTERTAINMENT, OTHER, ALL}
    private CATEGORY category;
    private LinkedList<Item> itemLinkedList = new LinkedList<Item>();

    public Purchases(int category){
        this.category = intToCategoryEnum(category);
    }

    public void addPurchase(Item item){
        itemLinkedList.add(item);
    }

    public int getCategory(){
        return categoryEnumToInt(category);
    }

    public LinkedList<Item> getList(){
        return itemLinkedList;
    }

    // Sorts the list.
    private void sort(){
        Collections.sort(itemLinkedList, new Comparator<Item>() {
            @Override
            public int compare(Item item, Item t1) {
                return t1.getItemCost().compareTo(item.getItemCost());
                //return (int) (t1.getItemCost() - item.getItemCost());
            }
        });

    }

    public String toStringSorted(){
        sort();
        return toString();
    }

    public boolean isEmpty() {
        return itemLinkedList.size() <= 0;
    }

    public Double getTotalSum(){
        Double total = 0.0;
        for (int i = 0; i < itemLinkedList.size(); i++){
            total += itemLinkedList.get(i).getItemCost();
        }
        return total;
    }

    @Override
    public String toString(){
        String str = "";
        if (category != CATEGORY.ALL){
            str += categoryToString();
        }

        String itemStr = "";
        for (int i = 0; i < itemLinkedList.size(); i++){
            itemStr = itemLinkedList.get(i).toString() + "\n";
            str += itemStr;
        }
        return str;
    }

    public String getCategoryString(){
        switch (category){
            case FOOD:
                return "Food";
            case CLOTHES:
                return "Clothes";
            case ENTERTAINMENT:
                return "Entertainment";
            case OTHER:
                return "Other";
            case ALL:
                return "All";
        }
        return null;
    }

    private String categoryToString(){
        switch (category){
            case FOOD:
                return "Food:\n";
            case CLOTHES:
                return "Clothes:\n";
            case ENTERTAINMENT:
                return "Entertainment:\n";
            case OTHER:
                return "Other:\n";
            case ALL:
                return "All:\n";
        }
        return null;
    }

    // Converts an integer to the CATEGORY enum value.
    private CATEGORY intToCategoryEnum(int i){
        switch (i){
            case 0:
                return CATEGORY.FOOD;
            case 1:
                return CATEGORY.CLOTHES;
            case 2:
                return CATEGORY.ENTERTAINMENT;
            case 3:
                return CATEGORY.OTHER;
            case 4:
                return CATEGORY.ALL;
        }
        return null;
    }

    // Converts a CATEGORY enum value to an integer.
    private int categoryEnumToInt(CATEGORY c){
        switch (c){
            case FOOD:
                return 0;
            case CLOTHES:
                return 1;
            case ENTERTAINMENT:
                return 2;
            case OTHER:
                return 3;
            case ALL:
                return 4;
        }
        return -1;
    }



}
