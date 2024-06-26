package com.viageria.tripManager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
	
	@Override
	protected String getKeyspaceName() {
		return "travelkeyspace";
	}
	
	@Override
	protected String getContactPoints() {
		return "localhost";
	}
	
	@Override
	protected int getPort() {
		return 9042;
	}

}
