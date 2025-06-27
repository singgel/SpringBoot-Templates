package com.heks.springbootollama;


import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class RagWorker {

    private final VectorStore vectorStore;
    private final Resource pdfResource;

    public RagWorker(VectorStore vectorStore,
                     @Value("classpath:/rag/1.pdf") Resource pdfResource) {
        this.vectorStore = vectorStore;
        this.pdfResource = pdfResource;
        // Ensure the vector store is initialized
        if (this.vectorStore == null) {
            throw new IllegalStateException("VectorStore is not initialized");
        }
        if (this.pdfResource == null || !this.pdfResource.exists()) {
            throw new IllegalArgumentException("PDF resource is not available");
        }
    }

    @PostConstruct
    public void init() {
        //Extract
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(pdfResource);
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
        //Transform, and Load
        vectorStore.add(tokenTextSplitter.apply(tikaDocumentReader.get()));
    }
}