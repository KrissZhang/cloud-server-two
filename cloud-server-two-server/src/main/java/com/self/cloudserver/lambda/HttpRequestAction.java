package com.self.cloudserver.lambda;

import com.self.cloudserver.utils.http.BaseHttpRequest;

public interface HttpRequestAction {

    void success(BaseHttpRequest request, final String response);

    void failed(BaseHttpRequest request, final String errmsg);

}
