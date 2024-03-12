///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package controller;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// *
// * @author thangphan
// */
//@Controller
//public class MyErrorController implements ErrorController  {
//
//    @RequestMapping("/error")
//    public String handleError() {
//        //do something like logging
//        return "error";
//    }
//    @RequestMapping("/error")
//public String handleError(HttpServletRequest request) {
//    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//    
//    if (status != null) {
//        Integer statusCode = Integer.valueOf(status.toString());
//    
//        if(statusCode == HttpStatus.NOT_FOUND.value()) {
//            return "error-404";
//        }
//        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//            return "error-500";
//        }
//    }
//    return "error";
//}
//}
