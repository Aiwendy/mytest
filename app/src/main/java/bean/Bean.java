package bean;

import java.io.Serializable;

/**
 * Function:
 * Created by zhang di on 2017-08-31.
 */

public class Bean  implements Serializable{

    private String value;
    private int index;

    public Bean(String value, int index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
