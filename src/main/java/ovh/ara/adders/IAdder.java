package ovh.ara.adders;

public interface IAdder {

    void init();

    void clearArray();

    double add();

    double[] getArray();

    void setCurrentIteration(int a);
}
