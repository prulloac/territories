package com.prulloac.territoriesdto.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

public abstract class AbstractMapper {

	@Autowired
	protected MessageSource messageSource;

	protected String getLocalizedMessage(String code, Locale locale) {
		return messageSource.getMessage(code, null, null, locale);
	}

	protected String getLocalizedMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, null, locale);
	}
}
