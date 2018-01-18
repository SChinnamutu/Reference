package com.perficient.hr.utils;

import javax.ws.rs.core.Response;

public class ResponseHandlingUtil {
	
	private ResponseHandlingUtil(){
		
	}
	
	public static Response prepareResponse(Object object) {
		if(object instanceof WsError) {
			WsError error = (WsError) object;
			if(error.getErrorCode() == null) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(object).build();
			} else {
				return Response.status(error.getErrorCode()).entity(object).build();
			}
		} else {
			return Response.status(Response.Status.OK).entity(object).build();
		}
	}
}
