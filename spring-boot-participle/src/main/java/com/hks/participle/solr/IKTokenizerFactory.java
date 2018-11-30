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
 * 
 */
package com.hks.participle.solr;

import com.hks.participle.lucene.IKTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.solr.analysis.BaseTokenizerFactory;

import java.io.Reader;
import java.util.Map;

/**
 * IK中文分词
 * Solr分词器工厂实现
 *
 * 2012-3-6
 */
public class IKTokenizerFactory extends BaseTokenizerFactory {
	
	
	private boolean useSmart = false;
	
    /** {@inheritDoc} */
    @Override
    public void init(Map<String, String> params) {
        super.init(params);
        String useSmartParam = params.get("useSmart");
        this.useSmart = (useSmartParam != null ? Boolean.parseBoolean(useSmartParam) : false);
    }	

	/* (non-Javadoc)
	 * @see org.apache.solr.analysis.TokenizerFactory#create(java.io.Reader)
	 */
	/** {@inheritDoc} */
	public Tokenizer create(Reader in) {
		return new IKTokenizer(in , this.useSmart);
	}

}
