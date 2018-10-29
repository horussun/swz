package com.ratelimit.guava;

import com.google.common.util.concurrent.RateLimiter;

public class BasicUsage {
    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(5); //令牌桶容量为5，即每200毫秒产生1个令牌
        System.out.println(rateLimiter.acquire()); //阻塞获取一个令牌
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        /*
        RateLimiter rateLimiter = RateLimiter.create(5); //令牌桶容量为5，即每200毫秒产生1个令牌
        System.out.println(rateLimiter.acquire(10)); //透支令牌
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        System.out.println(rateLimiter.acquire());
        */
        /*
        RateLimiter rateLimiter = RateLimiter.create(1000); //每秒投放1000个令牌
        for (int i = 0; i < 10; i++) {
            if (rateLimiter.tryAcquire()) { //tryAcquire检测有没有可用的令牌，结果马上返回
                System.out.println("处理请求");
            } else {
                System.out.println("拒绝请求");
            }
        }
        */
    }
    /*
     * public static RateLimiter create(double permitsPerSecond)
根据指定的稳定吞吐率创建RateLimiter，这里的吞吐率是指每秒多少许可数（通常是指QPS，每秒多少查询）。
The returned RateLimiter ensures that on average no more than permitsPerSecond are issued during any given second, with sustained requests being smoothly spread over each second. When the incoming request rate exceeds permitsPerSecond the rate limiter will release one permit every (1.0 / permitsPerSecond) seconds. When the rate limiter is unused, bursts of up to permitsPerSecond permits will be allowed, with subsequent requests being smoothly limited at the stable rate of permitsPerSecond.
返回的RateLimiter 确保了在平均情况下，每秒发布的许可数不会超过permitsPerSecond，每秒钟会持续发送请求。当传入请求速率超过permitsPerSecond，速率限制器会每秒释放一个许可(1.0 / permitsPerSecond 这里是指设定了permitsPerSecond为1.0) 。当速率限制器闲置时，允许许可数暴增到permitsPerSecond，随后的请求会被平滑地限制在稳定速率permitsPerSecond中。
参数:
permitsPerSecond – 返回的RateLimiter的速率，意味着每秒有多少个许可变成有效。
抛出:
IllegalArgumentException – 如果permitsPerSecond为负数或者为0
     */
    
    /*
     * public static RateLimiter create(double permitsPerSecond,long warmupPeriod,TimeUnit unit)
根据指定的稳定吞吐率和预热期来创建RateLimiter，这里的吞吐率是指每秒多少许可数（通常是指QPS，每秒多少查询），在这段预热时间内，RateLimiter每秒分配的许可数会平稳地增长直到预热期结束时达到其最大速率（只要存在足够请求数来使其饱和）。同样地，如果RateLimiter 在warmupPeriod时间内闲置不用，它将会逐步地返回冷却状态。也就是说，它会像它第一次被创建般经历同样的预热期。返回的RateLimiter 主要用于那些需要预热期的资源，这些资源实际上满足了请求（比如一个远程服务），而不是在稳定（最大）的速率下可以立即被访问的资源。返回的RateLimiter 在冷却状态下启动（即预热期将会紧跟着发生），并且如果被长期闲置不用，它将回到冷却状态。
参数:

 

    permitsPerSecond – 返回的RateLimiter的速率，意味着每秒有多少个许可变成有效。
    warmupPeriod – 在这段时间内RateLimiter会增加它的速率，在抵达它的稳定速率或者最大速率之前
    unit – 参数warmupPeriod 的时间单位

抛出:

    IllegalArgumentException – 如果permitsPerSecond为负数或者为0

     */

}
