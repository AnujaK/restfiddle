package com.restfiddle.config.monitoring;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by santoshm1 on 28/06/14.
 */
public class MetricsConfig extends MetricsConfigurerAdapter{
    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
	ConsoleReporter
			.forRegistry(metricRegistry)
			.build()
			.start(1, TimeUnit.MINUTES);
    }
}
