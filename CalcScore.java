/**
 * Utility
 */
public class CalcScore implements Serializable {

	int linkId;
	int spotId;
	boolean isFull; // whether the spot is full or not
	double driveDist, walkDist, score;
	/**
	 * Default constructor
	 */
	public CalcScore() {
	}

	public CalcScore(int linkId, int spotId, boolean isFull, double driveDist, double walkDist) {
		this.linkId = linkId;
		this.spotId = spotId;
		this.isFull = isFull;
		this.driveDist = driveDist;
		this.walkDist = walkDist;
	}


	public void setStatus(boolean isFull) {
		this.isFull = isFull;
	}

	public void setValue(int score){
		this.score = score;
	}

	public void setValue() {
		if (isFull == true) {
			score = (driveDist + 2 * walkDist) / 1000;
		} else {
			score = (driveDist + 2 * walkDist - 10000) / 1000; // it implies that the importance of the parking
																	// availability dominates the distance
		}
	}


	public void setCalc(CalcScore o) {
		this.linkId = o.linkId;
		this.spotId = o.spotId;
		this.isFull = o.isFull;
		this.driveDist = o.driveDist;
		this.walkDist = o.walkDist;
		this.score = o.score;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * This number is here for model snapshot storing purpose<br>
	 * It needs to be changed when this class gets changed
	 */
	private static final long serialVersionUID = 1L;

}