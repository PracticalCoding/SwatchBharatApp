package practicalcoding.swatchbharat.others;

public class message 
{
	private String msg;
	private String postedDate;
	private String landmarkTemp;
	private String locationTemp;
	private String ObjectId;
	
	
	public String getObjectId() {
		return ObjectId;
	}

	public void setObjectId(String objectId) {
		ObjectId = objectId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}



	
	
	

	public message(String msg,String date, String location, String landmark, String objectID)
	{
	  super();
	  this.msg = msg;
	  this.postedDate = date;
	  this.landmarkTemp = landmark;
	  this.locationTemp = location;
	  this.ObjectId = objectID;
	} 

	public String getLandmarkTemp() {
		return landmarkTemp;
	}



	public void setLandmarkTemp(String landmarkTemp) {
		this.landmarkTemp = landmarkTemp;
	}



	public String getLocationTemp() {
		return locationTemp;
	}



	public void setLocationTemp(String locationTemp) {
		this.locationTemp = locationTemp;
	}




	
   
   
  
	
    
}