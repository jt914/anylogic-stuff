/**
 * Utility
 */
public class Utility implements Serializable {

	int linkId;
	int spotId;
	boolean isFull; // whether the spot is full or not
	double dDist; // the distance of the spot (link) to the car
	double wDist; // the distance of the spot to the destination of the car
	double value; // the utility of the spot (link) for the car
	/**
	 * Default constructor
	 */
	public Utility() {
	}

	public Utility(int linkId, int spotId, boolean isFull, double driveDist, double walkDist) {
		this.linkId = linkId;
		this.spotId = spotId;
		isFull = _isFull;
		dDist = _dDist;
		wDist = _wDist;
		value = _value;
	}

	public void setLinkID(int _linkID) {
		linkID = _linkID;
	}

	public void setSpotID(int _spotID) {
		spotID = _spotID;
	}

	public void setStatus(boolean _isFull) {
		isFull = _isFull;
	}

	public void setdDist(double _dDist) {
		dDist = _dDist;
	}

	public void setwDist(double _wDist) {
		wDist = _wDist;
	}

	public void setValue() {
		if (isFull == true) {
			value = ( dDist + 2 * wDist) / 1000;
		} else {
			value = ( dDist + 2 * wDist - 10000) / 1000; // it implies that the importance of the parking
																	// availability dominates the distance
		}
	}

	public void setValue(double _value) {
		value = _value;
	}

	public void setUtility(Utility _UT) {
		linkID = _UT.linkID;
		spotID = _UT.spotID;
		isFull = _UT.isFull;
		dDist = _UT.dDist;
		wDist = _UT.wDist;
		value = _UT.value;
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