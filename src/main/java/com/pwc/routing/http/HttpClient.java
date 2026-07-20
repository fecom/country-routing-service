package com.pwc.routing.http;

import java.util.List;

public interface HttpClient<T> {
    List<T> fetch();
}
