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
package com.hks.participle.core;

/**
 * IK词元对象
 */
public class Lexeme implements Comparable<Lexeme>{
	//lexemeType常量
	//未知
	/** Constant <code>TYPE_UNKNOWN=0</code> */
	public static final int TYPE_UNKNOWN = 0;
	//英文
	/** Constant <code>TYPE_ENGLISH=1</code> */
	public static final int TYPE_ENGLISH = 1;
	//数字
	/** Constant <code>TYPE_ARABIC=2</code> */
	public static final int TYPE_ARABIC = 2;
	//英文数字混合
	/** Constant <code>TYPE_LETTER=3</code> */
	public static final int TYPE_LETTER = 3;
	//中文词元
	/** Constant <code>TYPE_CNWORD=4</code> */
	public static final int TYPE_CNWORD = 4;
	//中文单字
	/** Constant <code>TYPE_CNCHAR=64</code> */
	public static final int TYPE_CNCHAR = 64;
	//日韩文字
	/** Constant <code>TYPE_OTHER_CJK=8</code> */
	public static final int TYPE_OTHER_CJK = 8;
	//中文数词
	/** Constant <code>TYPE_CNUM=16</code> */
	public static final int TYPE_CNUM = 16;
	//中文量词
	/** Constant <code>TYPE_COUNT=32</code> */
	public static final int TYPE_COUNT = 32;
	//中文数量词
	/** Constant <code>TYPE_CQUAN=48</code> */
	public static final int TYPE_CQUAN = 48;
	
	//词元的起始位移
	private int offset;
    //词元的相对起始位置
    private int begin;
    //词元的长度
    private int length;
    //词元文本
    private String lexemeText;
    //词元类型
    private int lexemeType;
    
    
	/**
	 * <p>Constructor for Lexeme.</p>
	 *
	 * @param offset a int.
	 * @param begin a int.
	 * @param length a int.
	 * @param lexemeType a int.
	 */
	public Lexeme(int offset , int begin , int length , int lexemeType){
		this.offset = offset;
		this.begin = begin;
		if(length < 0){
			throw new IllegalArgumentException("length < 0");
		}
		this.length = length;
		this.lexemeType = lexemeType;
	}
	
    /*
     * 判断词元相等算法
     * 起始位置偏移、起始位置、终止位置相同
     * @see java.lang.Object#equals(Object o)
     */
	/** {@inheritDoc} */
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		
		if(this == o){
			return true;
		}
		
		if(o instanceof Lexeme){
			Lexeme other = (Lexeme)o;
			if(this.offset == other.getOffset()
					&& this.begin == other.getBegin()
					&& this.length == other.getLength()){
				return true;			
			}else{
				return false;
			}
		}else{		
			return false;
		}
	}
	
    /*
     * 词元哈希编码算法
     * @see java.lang.Object#hashCode()
     */
    /**
     * <p>hashCode.</p>
     *
     * @return a int.
     */
    public int hashCode(){
    	int absBegin = getBeginPosition();
    	int absEnd = getEndPosition();
    	return  (absBegin * 37) + (absEnd * 31) + ((absBegin * absEnd) % getLength()) * 11;
    }
    
    /*
     * 词元在排序集合中的比较算法
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
	/**
	 * <p>compareTo.</p>
	 *
	 * @return a int.
	 * @param other a {@link Lexeme} object.
	 */
	public int compareTo(Lexeme other) {
		//起始位置优先
        if(this.begin < other.getBegin()){
            return -1;
        }else if(this.begin == other.getBegin()){
        	//词元长度优先
        	if(this.length > other.getLength()){
        		return -1;
        	}else if(this.length == other.getLength()){
        		return 0;
        	}else {//this.length < other.getLength()
        		return 1;
        	}
        	
        }else{//this.begin > other.getBegin()
        	return 1;
        }
	}
	
	/**
	 * <p>Getter for the field <code>offset</code>.</p>
	 *
	 * @return a int.
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * <p>Setter for the field <code>offset</code>.</p>
	 *
	 * @param offset a int.
	 */
	public void setOffset(int offset) {
		this.offset = offset;
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
	 * 获取词元在文本中的起始位置
	 *
	 * @return int
	 */
	public int getBeginPosition(){
		return offset + begin;
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
	 * 获取词元在文本中的结束位置
	 *
	 * @return int
	 */
	public int getEndPosition(){
		return offset + begin + length;
	}
	
	/**
	 * 获取词元的字符长度
	 *
	 * @return int
	 */
	public int getLength(){
		return this.length;
	}	
	
	/**
	 * <p>Setter for the field <code>length</code>.</p>
	 *
	 * @param length a int.
	 */
	public void setLength(int length) {
		if(this.length < 0){
			throw new IllegalArgumentException("length < 0");
		}
		this.length = length;
	}
	
	/**
	 * 获取词元的文本内容
	 *
	 * @return String
	 */
	public String getLexemeText() {
		if(lexemeText == null){
			return "";
		}
		return lexemeText;
	}

	/**
	 * <p>Setter for the field <code>lexemeText</code>.</p>
	 *
	 * @param lexemeText a {@link String} object.
	 */
	public void setLexemeText(String lexemeText) {
		if(lexemeText == null){
			this.lexemeText = "";
			this.length = 0;
		}else{
			this.lexemeText = lexemeText;
			this.length = lexemeText.length();
		}
	}

	/**
	 * 获取词元类型
	 *
	 * @return int
	 */
	public int getLexemeType() {
		return lexemeType;
	}

	/**
	 * <p>Setter for the field <code>lexemeType</code>.</p>
	 *
	 * @param lexemeType a int.
	 */
	public void setLexemeType(int lexemeType) {
		this.lexemeType = lexemeType;
	}

	/**
	 * 合并两个相邻的词元
	 *
	 * @param l 实例
	 * @param lexemeType 类型
	 * @return boolean 词元是否成功合并
	 */
	public boolean append(Lexeme l , int lexemeType){
		if(l != null && this.getEndPosition() == l.getBeginPosition()){
			this.length += l.getLength();
			this.lexemeType = lexemeType;
			return true;
		}else {
			return false;
		}
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link String} object.
	 */
	public String toString(){
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(this.getBeginPosition()).append("-").append(this.getEndPosition());
		strbuf.append(" : ").append(this.lexemeText).append(" : \t");
		switch(lexemeType) {
			case TYPE_UNKNOWN : 
				strbuf.append("UNKONW");
				break;
			case TYPE_ENGLISH :
				strbuf.append("ENGLISH");
				break;
			case TYPE_ARABIC :
				strbuf.append("ARABIC");
				break;
			case TYPE_LETTER :
				strbuf.append("LETTER");
				break;
			case TYPE_CNWORD : 
				strbuf.append("CN_WORD");
				break;
			case TYPE_CNCHAR : 
				strbuf.append("CN_CHAR");
				break;
			case TYPE_OTHER_CJK :
				strbuf.append("OTHER_CJK");
				break;
			case TYPE_COUNT :
				strbuf.append("COUNT");
				break;
			case TYPE_CNUM :
				strbuf.append("CN_NUM");
				break;
			case TYPE_CQUAN:	
				strbuf.append("CN_QUAN");
				break;

		}
		return strbuf.toString();
	}
	

}
