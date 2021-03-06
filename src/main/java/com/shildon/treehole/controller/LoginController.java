package com.shildon.treehole.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shildon.treehole.model.User;
import com.shildon.treehole.service.SecretService;
import com.shildon.treehole.service.UserService;
import com.shildon.treehole.support.Callback;
import com.shildon.treehole.support.PageView;
import com.shildon.treehole.support.ResultMap;

/**
 * 
 * @author shildon<shildondu@gmail.com>
 * @date Apr 23, 2016
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Resource
	private UserService userService;
	@Resource
	private SecretService secretService;
	@Resource
	private PageView pageView;
	
	@RequestMapping(value = "/signin.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultMap<User> login(final String id, final String password,
			final HttpSession httpSession) {

		return execute(new Callback<User>() {
			@Override
			public boolean callback(ResultMap<User> resultMap) {
				User user = userService.get(id);
				if (null != user && null != password && 
						password.equals(user.getPassword())) {

					httpSession.setAttribute("user", user);
					resultMap.addResult(user);
					return true;

				} else {
					return false;
				}
			}
		});
	}
	
	@RequestMapping("/isLogin.do")
	@ResponseBody
	public ResultMap<User> isLogin(final HttpSession httpSession) {
		return execute(new Callback<User>() {
			@Override
			public boolean callback(ResultMap<User> resultMap) {
				User user = (User) httpSession.getAttribute("user");
				if (null != user) {
					resultMap.addResult(user);
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	@RequestMapping("/logout.do")
	@ResponseBody
	public ResultMap<User> logout(final HttpSession httpSession) {
		return execute(new Callback<User>() {
			@Override
			public boolean callback(ResultMap<User> resultMap) {
				httpSession.removeAttribute("user");
				return true;
			}
		});
	}
	
	@RequestMapping("/pageviews.do")
	@ResponseBody
	public ResultMap<Long> getPageViews() {
		return execute(new Callback<Long>() {
			@Override
			public boolean callback(ResultMap<Long> resultMap) {
				Long pageViews = pageView.getPageViews();
				
				if (pageViews >= 0) {
					resultMap.addResult(pageViews);
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
}
