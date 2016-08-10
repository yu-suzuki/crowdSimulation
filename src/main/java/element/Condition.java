package element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ysuzuki on 2016/08/10.
 * Condition
 */
public class Condition {
    private int itemNum, select, workerNum;

    Condition(int itemNum, int select, int workerNum) {
        this.itemNum = itemNum;
        this.select = select;
        this.workerNum = workerNum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != getClass()) { return false; }
        Condition rhs = (Condition) o;
        return new EqualsBuilder()
                .append(itemNum, rhs.itemNum)
                .append(select, rhs.select)
                .append(workerNum, rhs.workerNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(itemNum).append(select).append(workerNum).toHashCode();
    }

    public int getItemNum() {
        return itemNum;
    }
}
