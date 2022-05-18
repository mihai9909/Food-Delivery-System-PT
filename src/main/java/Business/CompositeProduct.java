package Business;


import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem{

    public List<MenuItem> getItemList() {
        return itemList;
    }

    private List<MenuItem> itemList;

    public CompositeProduct() {
        itemList = new ArrayList<MenuItem>();
    }

    public void addItem(MenuItem menuItem){
        itemList.add(menuItem);
        this.rating += menuItem.rating;
        this.fat += menuItem.fat;
        this.price += menuItem.price;
        this.protein += menuItem.protein;
        this.sodium += menuItem.sodium;
        this.calories += menuItem.calories;
    }

    @Override
    public double computePrice(){
        return itemList.stream().mapToInt(itm -> (int) itm.computePrice()).sum();
    }

    public void computeRating(){
        if(itemList.size() != 0)
            rating /= itemList.size();
    }
}
