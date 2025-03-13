package com.pukhovkirill.severstalnotes.usecase;

public interface UseCase<V, K>{
    V execute(K... args);
}
