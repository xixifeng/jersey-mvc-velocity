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
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Properties;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

/**
 * 访问权限拦截器
 * 
 * @author xixifeng
 * @version 2017年12月28日
 */
public class AuthorizationFilter implements ClientRequestFilter {

	// 默认值
	private String accessKeyID = "admin";
	private String accessKeySecret = "123456";

	public AuthorizationFilter(Properties prop) {
		if (prop != null) {
			this.accessKeyID = prop.getProperty("accessKeyID", this.accessKeyID);
			this.accessKeySecret = prop.getProperty("accessKeySecret", this.accessKeySecret);
		}
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(accessKeyID);
		sb.append(':');
		sb.append(accessKeySecret);

		String base = Base64.getEncoder().encodeToString(sb.toString().getBytes(Charset.forName("utf-8")));

		StringBuilder aasicAuth = new StringBuilder();
		aasicAuth.append("Basic ");
		aasicAuth.append(base);

		requestContext.getHeaders().add("Authorization", aasicAuth.toString());
	}
}
