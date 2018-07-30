package com.xczhihui.common.util.vhallyun;

import java.util.HashMap;

import com.jayway.jsonpath.JsonPath;

public class DocumentService {

    static String downPath = "document";

    public static String create(String url) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params = VhallUtil.createRealParam(params);
        String urlImage = VhallUtil.downUrlFile(url, downPath);
        params.put("document",urlImage);
        String result = VhallUtil.formUpload("http://api.yun.vhall.com/api/v1/document/create",params);
        String documentId = JsonPath.read(result, "$.data.document_id");
        return documentId;
    }

}
