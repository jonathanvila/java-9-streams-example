package org.vilojona.techtalks.java9streams;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;

public class MyFilterProcessor<T> extends SubmissionPublisher<T> implements Flow.Processor<T, T> {
    private Predicate<T> predicate;
    private Flow.Subscription subscription;

    public MyFilterProcessor(Predicate<T> predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("Subscribed");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        if (!predicate.test(item)) {
            submit(item);
        }
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        close();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        super.subscribe(subscriber);
    }
}
