package com.xue.bbs.beanflat;

import javax.servlet.http.HttpSession;

/**
 * 功能：退出登录，注销session
 * @author xuexue
 *
 */
public class ExitLogin {
	
	public static void exit(HttpSession session) {
		session.invalidate();
	}
	

}
