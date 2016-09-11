//package com.springBootD.wx.kit;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.ProtocolException;
//import org.apache.http.client.RedirectHandler;
//import org.apache.http.protocol.HttpContext;
//
//import java.net.URI;
//
//public class MyRedirectHandler implements RedirectHandler {
//    @Override
//    public boolean isRedirectRequested(HttpResponse response,
//                        HttpContext context){
//                //判断是否重定向
//                //...
//                return isRedirect;
//    }
//    @Override
//    public URI getLocationURI(HttpResponse response,
//                              HttpContext context)
//                          throws ProtocolException {
//        return locationURI;
//    }
//}