package workwithdatabase;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import workwithdatabase.models.Goods;

public final class SelectedGoods {

    public final ObjectProperty<Goods> goods = new SimpleObjectProperty<>();
    public final IntegerProperty count = new SimpleIntegerProperty();

    public SelectedGoods() {
        this(new Goods(), 0);
    }

    public SelectedGoods(Goods goods, int count) {
        setGoods(goods);
        setCount(count);
    }

    public Goods getGoods() {
        return goods.get();
    }

    public void setGoods(Goods goods) {
        this.goods.set(goods);
    }

    public int getCount() {
        return count.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

}
