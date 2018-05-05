package com.spg.utils;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spg.request.Request;

public class ConversionUtils {
	private static final Logger LOGGER = Logger.getLogger(ConversionUtils.class);
	public static Request convertStringToRequestObject(String request) {
		LOGGER.info("convertStringToRequestObject: "+request);
		try {
			return new ObjectMapper().readValue(request, Request.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("error while converting to Request object: "+request);
			e.printStackTrace();
		}
		return null;
	}
}
