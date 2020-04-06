package com.prulloac.territoriesbase.error;

public class ContinentNotFoundException extends RuntimeException {

	public ContinentNotFoundException() {
		super();
	}

	public ContinentNotFoundException(String message) {
		super(message);
	}

	public ContinentNotFoundException(Throwable cause) {
		super(cause);
	}

	public ContinentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public static ContinentNotFoundException emptyCollection() {
		return new ContinentNotFoundException("No continents found");
	}

	public static ContinentNotFoundException isoCodeNotFound(String isoCode) {
		return new ContinentNotFoundException("No continent found for isoCode "+isoCode);
	}

	public static ContinentNotFoundException isoCodeNotFound(String isoCode, Throwable cause) {
		return new ContinentNotFoundException("No continent found for isoCode "+isoCode, cause);
	}


}
