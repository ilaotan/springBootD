package com.springBootD.framework.utils;

import java.util.UUID;

public class UuidUtil {

	public static String newid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}