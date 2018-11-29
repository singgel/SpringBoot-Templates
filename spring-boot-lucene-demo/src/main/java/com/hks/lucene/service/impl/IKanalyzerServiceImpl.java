package com.hks.lucene.service.impl;

import com.hks.lucene.service.IKanalyzerService;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class IKanalyzerServiceImpl implements IKanalyzerService {
    @Override
    public Map<String, Integer> getParticipleByStr(String content) {
        return getParticipleByStr(content,true);
    }

    @Override
    public Map<String, Integer> getParticipleByStr(String content, boolean useSmart) {
        Map<String, Integer> res=new HashMap<>();
        try {
            byte[] bt = content.getBytes();// str
            InputStream ip = new ByteArrayInputStream(bt);
            Reader read = new InputStreamReader(ip);
            IKSegmenter iks = new IKSegmenter(read, useSmart);
            Lexeme t;
            while ((t = iks.next()) != null) {
                String tmpKey=t.getLexemeText();
                if(res.containsKey(tmpKey)){
                    Integer val= res.get(tmpKey)+1;
                    res.replace(tmpKey,val);
                }else{
                    res.put(tmpKey,1);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }
}
