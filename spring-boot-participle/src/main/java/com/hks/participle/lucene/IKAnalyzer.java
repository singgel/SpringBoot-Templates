/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 */
package com.hks.participle.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

import java.io.IOException;
import java.io.Reader;

/**
 * IK分词器，Lucene Analyzer接口实现
 * 兼容Lucene 3.1以上版本
 */
public final class IKAnalyzer extends Analyzer{
	
	private boolean useSmart;
	
	/**
	 * <p>useSmart.</p>
	 *
	 * @return a boolean.
	 */
	public boolean useSmart() {
		return useSmart;
	}

	/**
	 * <p>Setter for the field <code>useSmart</code>.</p>
	 *
	 * @param useSmart a boolean.
	 */
	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}

	/**
	 * IK分词器Lucene 3.5 Analyzer接口实现类
	 *
	 * 默认细粒度切分算法
	 */
	public IKAnalyzer(){
		this(false);
	}
	
	/**
	 * IK分词器Lucene Analyzer接口实现类
	 *
	 * @param useSmart 当为true时，分词器进行智能切分
	 */
	public IKAnalyzer(boolean useSmart){
		super();
		this.useSmart = useSmart;
	}

	/** {@inheritDoc} */
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new IKTokenizer(reader , this.useSmart());
	}
	
	/** {@inheritDoc} */
	@Override
	public TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
		Tokenizer _IKTokenizer = (Tokenizer)getPreviousTokenStream();
		if (_IKTokenizer == null) {
			_IKTokenizer = new IKTokenizer(reader , this.useSmart());
			setPreviousTokenStream(_IKTokenizer);
		} else {
			_IKTokenizer.reset(reader);
		}
		return _IKTokenizer;
	}
}
