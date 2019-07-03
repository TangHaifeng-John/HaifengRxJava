package com.haifeng.demo.test;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class ZipObserver<T> {


   public  Queue<T> queue =new LinkedBlockingDeque<>();
}
