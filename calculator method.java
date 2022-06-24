Link _tempLink = new Link();
ParkingSpot _tempSpot = new ParkingSpot();
Point carPost = new Point();
Utility UT;
ArrayList<Link> _nextLinks = car.link.lNextLink;
ArrayList<ParkingSpot> _listSpots = new ArrayList<ParkingSpot>();
ArrayList<Utility> _listUT = new ArrayList<Utility>();	
ArrayList<Utility> _listUT2 = new ArrayList<Utility>();

Method Math = new Method();

carPost.setLatLon(car.getX(), car.getY());
int num = _nextLinks.size();
for(int i = 0; i < num; i++)
{
	_tempLink = _nextLinks.get(i);		//obtain the information of the ith following link
	if(_tempLink.lSpot.isEmpty() == false)	//if there is parking lot on the link
	{
		for(int j = 0; j < _tempLink.lSpot.size(); j++)	//obtain the information of the associated parking lot
		{
			_tempSpot = _tempLink.lSpot.get(j);
			if(_tempSpot.pFull == false)		//if there is available parking space on the parking lot
			{
				UT = new Utility();
				UT.setLinkID(_tempLink.ID);
				UT.setSpotID(_tempSpot.ID);
				UT.setStatus(false);
				UT.setdDist(Math.distanceTo(_tempSpot.pPost,carPost));
				UT.setwDist(Math.distanceTo(_tempSpot.pPost,car.dest.bPost));
				UT.setValue();
				_listUT.add(UT);
			}
			else							//if there is no available parking space on the parking lot
			{
				UT = new Utility();
				UT.setLinkID(_tempLink.ID);
				UT.setSpotID(_tempSpot.ID);
				UT.setStatus(true);
				UT.setdDist(Math.distanceTo(_tempLink.lPost,carPost));
				UT.setwDist(Math.distanceTo(_tempLink.lPost,car.dest.bPost));
				UT.setValue();
				_listUT.add(UT);
			}
		}
		
		UT = new Utility();
		UT.setLinkID(_tempLink.ID);
		UT.setStatus(true);
		UT.setdDist(Math.distanceTo(_tempLink.lPost,carPost));
		UT.setwDist(Math.distanceTo(_tempLink.lPost,car.dest.bPost));
		UT.setValue();
		_listUT2.add(UT);	//collect the utility function for each link, instead of each parking lot, to design the turning posibility
	}
	else		//if there is no parking space along the link
	{
		UT = new Utility();
		UT.setLinkID(_tempLink.ID);
		UT.setSpotID(0);
		UT.setStatus(true);
		UT.setdDist(Math.distanceTo(_tempLink.lPost,carPost));
		UT.setwDist(Math.distanceTo(_tempLink.lPost,car.dest.bPost));
		UT.setValue();
		_listUT.add(UT);
		_listUT2.add(UT);
	}	
}


Utility minUT = new Utility();
minUT.setValue(10000);		//set the disutility to be a large enough value
for(int i = 0; i < _listUT.size(); i++)
{
	if(_listUT.get(i).value < minUT.value)
	{
		minUT.setUtility(_listUT.get(i)); 	//obtain the minimum disutility
	}
}

if(minUT.isFull == false)		//if there is available parking space associated with the minimum disutility link
{
	car.setSpot(netSpot[minUT.spotID]);		//set the parking spot as the objective parking spot for this car
	car.setWalkingTime(minUT.wDist/4/1);	//set the walking time (m/s)
	netSpot[car.spot.ID].cParked();		// change the status of the objective parking spot to be parked, in case it is assigned to the other cars
	return true;
}
else
{
	double totalDisUT = 0;
	for(int i = 0; i < _listUT2.size(); i++)
	{
		totalDisUT = totalDisUT + exp(-_listUT2.get(i).value);
	}
	
	double rnd = uniform();
	double _tempUT;
	ArrayList<Double> sumDisUT = new ArrayList<Double>();
	for(int i = 0; i < _listUT2.size(); i++)
	{
		_tempUT = 0;
		for(int j = 0; j < i; j++)
		{
			_tempUT = _tempUT + exp(-_listUT2.get(j).value);
		}
		sumDisUT.add(_tempUT);
	}

	for(int i = 0; i < sumDisUT.size(); i++)
	{
		if(i < sumDisUT.size() - 1)
		{
			if(rnd >= sumDisUT.get(i)/totalDisUT && rnd < sumDisUT.get(i+1)/totalDisUT)
			{
				car.setObjLink(_nextLinks.get(i));
			}
		}
		else
		{
 			if(rnd >= sumDisUT.get(i)/totalDisUT)
			{
				car.setObjLink(_nextLinks.get(i));
			}
		}
		
	}

	return false;
}
