package com.sircular.circle.engine;

public class Utils {
	
	public static float setSign(float value, float sign) {
		if (sign == 0f)
			return sign;
		return sign > 0 ? Math.abs(value) : -Math.abs(value);
	}

}
