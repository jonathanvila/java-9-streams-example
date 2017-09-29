package org.vilojona.techtalks.java9streams;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class MyTransformProcessor<T,R> extends SubmissionPublisher<R> implements Flow.Processor<T, R> {

        private Function function;
        private Flow.Subscription subscription;

        public MyTransformProcessor(Function<? super T, ? extends R> function) {
            super();
            this.function = function;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            submit((R) function.apply(item));
            subscription.request(1);
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println("done");
            close();
        }
    }
