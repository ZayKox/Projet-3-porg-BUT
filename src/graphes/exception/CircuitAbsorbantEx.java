package graphes.exception;

@SuppressWarnings("serial")
public class CircuitAbsorbantEx extends IllegalArgumentException{
	public CircuitAbsorbantEx() {
		super("Le graphe possède un circuit absorbant.");
	}
}
