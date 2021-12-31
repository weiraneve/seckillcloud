package com.weiran.common.redis.key;

public abstract class BasePrefix implements KeyPrefix{

	private String prefix;

	public BasePrefix( String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		// getClass获得当前对象的类型，Java里Class类,用以描述类型信息；getSimpleName()返回源代码中给出的底层类的简称
		String className = getClass().getSimpleName();
		return className + ":" + prefix;
	}

}
