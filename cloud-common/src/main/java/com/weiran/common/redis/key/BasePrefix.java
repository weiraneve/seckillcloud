package com.weiran.common.redis.key;

public abstract class BasePrefix implements KeyPrefix{

	private final String prefix;

	public static final String KEY_SEPARATOR = ":";

	private static final String KEY_PATTERN_SEPARATOR = ":*";

	public BasePrefix( String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		// getClass获得当前对象的类型，Java里Class类,用以描述类型信息；getSimpleName()返回源代码中给出的底层类的简称
		String className = getClass().getSimpleName();
		return className + KEY_SEPARATOR + prefix;
	}

	public String getPattern() {
		String className = getClass().getSimpleName();
		return className + KEY_PATTERN_SEPARATOR;
	}

}
