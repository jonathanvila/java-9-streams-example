package org.vilojona.techtalks.java9streams;

import java.util.Arrays;
import java.util.concurrent.SubmissionPublisher;

public class MainApp {
    public static void main(String... args) {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        //Create Processor and Subscriber
        MyFilterProcessor<String> filterProcessor = new MyFilterProcessor<>(s -> s.equals("x"));

        MyTransformProcessor<String, Integer> transformProcessor = new MyTransformProcessor<>(Integer::parseInt);

        MySubscriber<Integer> subscriber = new MySubscriber<>();

        //Chain Processor and Subscriber
        publisher.subscribe(filterProcessor);
        filterProcessor.subscribe(transformProcessor);
        transformProcessor.subscribe(subscriber);

        System.out.println("Publishing Items...");
        String[] items = {"1", "x", "2", "x", "3", "x"};
        Arrays.stream(items).forEach(publisher::submit);


        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
