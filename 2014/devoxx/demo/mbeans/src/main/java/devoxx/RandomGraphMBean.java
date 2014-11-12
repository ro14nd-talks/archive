package devoxx;

/**
 * @author roland
 * @since 26.06.13
 */
public interface RandomGraphMBean {

    public int nextValue(int series);

    public int getSample1();
    public int getSample2();
    public int getSample3();
    public int getSample4();
    public int getSample5();
}
