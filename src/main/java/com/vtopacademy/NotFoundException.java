package com.vtopacademy;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String objectName, String objectID) {
    super("Could not find " + objectName + "with ID" 
    		+  objectID);
  }
	 
}
