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

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author xixifeng
 * @version 2018年1月5日
 */
public class ParameterFormatDirective extends Directive {

	private static final Logger LOGX = LoggerFactory.getLogger(ParameterFormatDirective.class);

	@Override
	public String getName() {
		return "paraFormat";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {

		String v = "";

		Object val1 = node.jjtGetChild(0).value(context);
		Object val2 = node.jjtGetChild(1).value(context);

		if (val2 == null) {
			LOGX.error("paraFormat 方法的第二个参数不能为null");
			throw new IllegalArgumentException("paraFormat 方法的第二个参数不能为null");
		}

		if (val1 == null || "".equals(val1)) {
			v = val2.toString();
		} else {
			v = val1.toString();
		}

		writer.write(v);
		return true;
	}

}
