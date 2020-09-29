package budget;

public class Item implements Comparable<Item> {
    private String itemName = "";
    private Double itemCost = 0.0;

    public Item (String name, Double cost){
        itemName = name;
        itemCost = cost;
    }


    // Getters.
    public String getItemName(){
        return itemName;
    }

    public Double getItemCost(){
        return itemCost;
    }

    @Override
    // Returns item details formatted.
    public String toString(){
        return String.format("%s $%.2f", itemName, itemCost);
    }

    // Setters.
    public void setItemName(String name){
        itemName = name;
    }

    public void setItemCost(Double cost){
        itemCost = cost;
    }

    @Override
    public int compareTo(Item item){
        return this.getItemCost().compareTo(item.getItemCost());
    }
}
