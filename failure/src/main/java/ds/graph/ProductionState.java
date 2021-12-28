package ds.graph;

public class ProductionState {
    private int totalProducts;
    private int defectiveProducts;

    public ProductionState(int totalProducts, int defectiveProducts){
        this.totalProducts = totalProducts;
        this.defectiveProducts = defectiveProducts;
    }

    public int getTotalProducts(){
        return this.totalProducts;
    }

    public int getDefectiveProducts(){
        return this.defectiveProducts;
    }

    public String toString(){
        return this.defectiveProducts + " defective / "  + this.totalProducts + " products\n";
    }
}
