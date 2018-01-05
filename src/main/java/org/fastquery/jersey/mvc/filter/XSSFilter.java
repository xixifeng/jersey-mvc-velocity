/*
 * Copyright (c) 2016-2017, fastquery.org and/or its affiliates. All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * For more information, please see http://www.fastquery.org/.
 * 
 */

package org.fastquery.jersey.mvc.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 防范XSS攻击
 * 
 * @author xixifeng
 * @version 2017年12月29日
 */
public class XSSFilter implements ContainerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(XSSFilter.class);

	@Context
	private HttpServletRequest request;

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = resourceInfo.getResourceMethod();
		Produces produces = method.getAnnotation(Produces.class);
		String[] medias = produces.value();
		String media = Arrays.toString(medias);
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = request.getParameter(name);
			if (value != null && value.contains("<")) {
				LOG.error("参数" + name + "不能包含'<'符号");
				if (media.contains("text/html")) {
					requestContext.abortWith(Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build());
					return;
				}

				if (media.contains("application/json")) {
					requestContext.abortWith(Response.ok("{\"ok\":false,\"error\":\"msg\"}").build());
					return;
				}
			}
		}

		// 有待扩展 post from请求等等...
	}

}
