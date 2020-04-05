package com.prulloac.territoriesbase.error;

/**
 * @author Prulloac
 */
public class CountryNotFoundException extends RuntimeException {

	public CountryNotFoundException() {
		super();
	}

	public CountryNotFoundException(String message) {
		super(message);
	}

	public CountryNotFoundException(Throwable cause) {
		super(cause);
	}

	public CountryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public static CountryNotFoundException emptyCollection() {
		return new CountryNotFoundException("No countries found");
	}

	public static CountryNotFoundException isoCodeNotFound(String isoCode) {
		return new CountryNotFoundException("No country found for isoCode "+isoCode);
	}

	public static CountryNotFoundException isoCodeNotFound(String isoCode, Throwable cause) {
		return new CountryNotFoundException("No country found for isoCode "+isoCode, cause);
	}

}
