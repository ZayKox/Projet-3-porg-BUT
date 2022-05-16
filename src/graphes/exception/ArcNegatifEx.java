package graphes.exception;

@SuppressWarnings("serial")
public class ArcNegatifEx extends IllegalArgumentException  {
    public ArcNegatifEx() {
        super("Un arc a une valuation négative.");
    }
}
