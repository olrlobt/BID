package com.ssafy.bid.global.util;

import java.util.Collection;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Preconditions {

	public static <T> void checkContains(T value, Collection<T> collection, String message) {
		if (!collection.contains(value)) {
			throw new IllegalArgumentException(message);
		}
	}
}
