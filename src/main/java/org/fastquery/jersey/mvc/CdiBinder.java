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

package org.fastquery.jersey.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.fastquery.jersey.mvc.filter.AuthorizationFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 把properties交给容器,以便注入使用
 * 
 * @author xixifeng
 * @version 2017年12月28日
 */
public class CdiBinder extends AbstractBinder {

	private static final Logger LOG = LoggerFactory.getLogger(CdiBinder.class);

	@Override
	protected void configure() {
		// 将classpath 目录下的properties文件交给CDI容器
		Map<String, Properties> props = new HashMap<>();
		URL url = this.getClass().getResource("/");
		File file = new File(url.getFile());

		// 找出所有的 properties 文件
		String[] list = file.list();
		for (String f : list) {
			if (f.endsWith(".properties")) {
				Properties p = new Properties();
				File proFile = new File(file.getPath(), f);
				try (InputStream in = new FileInputStream(proFile)) {
					p.load(in);
					String shortName = proFile.getName().replace(".properties", "");
					bind(p).named(shortName);
					props.put(shortName, p);
				} catch (IOException e) {
					LOG.warn("读取:" + f + "错误", e);
				}
			}
		}

		// 将AuthorizationFilter交给CDI容器
		Properties p = props.get("authorization");
		bind(new AuthorizationFilter(p)).to(AuthorizationFilter.class);
		props.clear();
	}
}
