/**
 * 
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
package com.hks.participle.dic;

/**
 * 表示一次词典匹配的命中
 */
public class Hit {
	//Hit不匹配
	private static final int UNMATCH = 0x00000000;
	//Hit完全匹配
	private static final int MATCH = 0x00000001;
	//Hit前缀匹配
	private static final int PREFIX = 0x00000010;
	
	
	//该HIT当前状态，默认未匹配
	private int hitState = UNMATCH;
	
	//记录词典匹配过程中，当前匹配到的词典分支节点
	private DictSegment matchedDictSegment;
	/*
	 * 词段开始位置
	 */
	private int begin;
	/*
	 * 词段的结束位置
	 */
	private int end;


	/**
	 * 判断是否完全匹配
	 *
	 * @return a boolean.
	 */
	public boolean isMatch() {
		return (this.hitState & MATCH) > 0;
	}
	/**
	 * <p>setMatch.</p>
	 */
	public void setMatch() {
		this.hitState = this.hitState | MATCH;
	}

	/**
	 * 判断是否是词的前缀
	 *
	 * @return a boolean.
	 */
	public boolean isPrefix() {
		return (this.hitState & PREFIX) > 0;
	}
	/**
	 * <p>setPrefix.</p>
	 */
	public void setPrefix() {
		this.hitState = this.hitState | PREFIX;
	}
	/**
	 * 判断是否是不匹配
	 *
	 * @return a boolean.
	 */
	public boolean isUnmatch() {
		return this.hitState == UNMATCH ;
	}
	/**
	 * <p>setUnmatch.</p>
	 */
	public void setUnmatch() {
		this.hitState = UNMATCH;
	}

	/**
	 * <p>Getter for the field <code>matchedDictSegment</code>.</p>
	 *
	 * @return a {@link DictSegment} object.
	 */
	public DictSegment getMatchedDictSegment() {
		return matchedDictSegment;
	}

	/**
	 * <p>Setter for the field <code>matchedDictSegment</code>.</p>
	 *
	 * @param matchedDictSegment a {@link DictSegment} object.
	 */
	public void setMatchedDictSegment(DictSegment matchedDictSegment) {
		this.matchedDictSegment = matchedDictSegment;
	}
	
	/**
	 * <p>Getter for the field <code>begin</code>.</p>
	 *
	 * @return a int.
	 */
	public int getBegin() {
		return begin;
	}
	
	/**
	 * <p>Setter for the field <code>begin</code>.</p>
	 *
	 * @param begin a int.
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}
	
	/**
	 * <p>Getter for the field <code>end</code>.</p>
	 *
	 * @return a int.
	 */
	public int getEnd() {
		return end;
	}
	
	/**
	 * <p>Setter for the field <code>end</code>.</p>
	 *
	 * @param end a int.
	 */
	public void setEnd(int end) {
		this.end = end;
	}	
	
}
