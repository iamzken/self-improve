package com.gupaoedu.dubbo.provider;

import com.gupaoedu.dubbo.api.IRpcCalc;

public class RpcCalc implements IRpcCalc {

	@Override
	public int add(int a, int b) {
		return a + b;
	}

	@Override
	public int sub(int a, int b) {
		return a - b;
	}

	@Override
	public int mult(int a, int b) {
		return a * b;
	}

	@Override
	public int div(int a, int b) {
		return a / b;
	}

}
