package org.wzy.sqltemplate.script;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Wen
 * 
 */
public class ExpressionEvaluator {

	public boolean evaluateBoolean(String expression, Object parameterObject) {
		Object value = OgnlCache.getValue(expression, parameterObject);
		if (value instanceof Boolean)
			return (Boolean) value;
		if (value instanceof Number)
			return !new BigDecimal(String.valueOf(value))
					.equals(BigDecimal.ZERO);
		return value != null;
	}

	public Iterable<?> evaluateIterable(String expression,
			Object parameterObject) {
		Object value = OgnlCache.getValue(expression, parameterObject);
		if (value == null)
			throw new RuntimeException("The expression '" + expression
					+ "' evaluated to a null value.");
		if (value instanceof Iterable)
			return (Iterable<?>) value;
		if (value.getClass().isArray()) {
			// the array may be primitive, so Arrays.asList() may throw
			// a ClassCastException (issue 209). Do the work manually
			// Curse primitives! :) (JGB)
			int size = Array.getLength(value);
			List<Object> answer = new ArrayList<Object>();
			for (int i = 0; i < size; i++) {
				Object o = Array.get(value, i);
				answer.add(o);
			}
			return answer;
		}
		if (value instanceof Map) {
			return ((Map) value).entrySet();
		}
		throw new RuntimeException("Error evaluating expression '" + expression
				+ "'.  Return value (" + value + ") was not iterable.");
	}

}
