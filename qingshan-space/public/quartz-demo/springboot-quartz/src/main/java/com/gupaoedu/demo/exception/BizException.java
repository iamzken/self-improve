package com.gupaoedu.demo.exception;

/**
 * 自定义异常
 * 
 *
 */
public class BizException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BizException(String msg) {
		super(msg);
	}
}
