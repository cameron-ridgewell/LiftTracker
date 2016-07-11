/*
 * #%L
 * Simmetrics Core
 * %%
 * Copyright (C) 2014 - 2016 Simmetrics Authors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.lifttracker.utilities.simmetrics.builders;

import java.util.List;
import java.util.Set;

import com.lifttracker.utilities.simmetrics.Metric;
import com.lifttracker.utilities.simmetrics.StringMetric;
import com.lifttracker.utilities.simmetrics.simplifiers.Simplifier;
import com.lifttracker.utilities.simmetrics.tokenizers.Tokenizer;

import com.google.common.collect.Multiset;

@SuppressWarnings("deprecation") // Implementation of StringMetrics will migrate into this class
final class StringMetrics {

	public static StringMetric create(Metric<String> metric) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.create(metric);
	}

	
	public static StringMetric create(Metric<String> metric, Simplifier simplifier) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.create(metric,simplifier);
	}

	
	public static StringMetric createForListMetric(Metric<List<String>> metric, Simplifier simplifier,
			Tokenizer tokenizer) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.createForListMetric(metric,simplifier, tokenizer);

	}

	
	public static StringMetric createForListMetric(Metric<List<String>> metric, Tokenizer tokenizer) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.createForListMetric(metric, tokenizer);
	}

	
	public static StringMetric createForSetMetric(Metric<Set<String>> metric, Simplifier simplifier,
			Tokenizer tokenizer) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.createForSetMetric(metric,simplifier, tokenizer);
	}

	
	public static StringMetric createForSetMetric(Metric<Set<String>> metric, Tokenizer tokenizer) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.createForSetMetric(metric, tokenizer);
	}

	
	
	public static StringMetric createForMultisetMetric(Metric<Multiset<String>> metric, Simplifier simplifier,
			Tokenizer tokenizer) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.createForMultisetMetric(metric,simplifier, tokenizer);
	}

	
	public static StringMetric createForMultisetMetric(Metric<Multiset<String>> metric, Tokenizer tokenizer) {
		return com.lifttracker.utilities.simmetrics.metrics.StringMetrics.createForMultisetMetric(metric, tokenizer);
	}


	private StringMetrics() {
		// Utility class.
	}

}
