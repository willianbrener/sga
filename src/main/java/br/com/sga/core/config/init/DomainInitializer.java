package br.com.sga.core.config.init;

import org.apache.commons.lang3.ArrayUtils;

import br.com.sga.core.config.DomainCoreConfiguration;
import br.com.sga.core.config.DomainJdbcConfiguration;
import br.com.sga.core.config.DomainJpaConfiguration;

public class DomainInitializer {
	private DomainInitializer() {
	}

	public static Class<?>[] getRootConfigClasses() {

		Class<?>[] rootConfigClasses = new Class[] { DomainCoreConfiguration.class };

		rootConfigClasses = ArrayUtils.addAll(rootConfigClasses,
			new Class[] { 
					DomainJpaConfiguration.class, 
					DomainJdbcConfiguration.class, 
			});

		return rootConfigClasses;
	}
}