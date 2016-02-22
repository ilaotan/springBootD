/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.springBootD.framework.utils;

/**
 * 处理service抛出运行时异常处理
 * @author lance
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1389958090308317369L;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg, Throwable clause) {
		super(msg, clause);
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable clause) {
		super(clause);
	}
	
}
