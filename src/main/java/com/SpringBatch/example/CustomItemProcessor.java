package com.SpringBatch.example;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Complain, Complain> {
    @Override
    public Complain process(Complain item) throws Exception {
           return item;

    }
}
