package com.socket.socketdemo.controller;

import com.socket.socketdemo.server.SocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * This is Description
 *
 * @author aoenu
 * @date 2018/10/22
 */
@RestController
public class IndexController {

    @Autowired
    private SocketServer socketServer;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/sendMsg")
    public String sendMsg(@RequestParam(value = "msg") String msg, @RequestParam List<String> userId) {
        socketServer.sendMsg(msg, userId);
        return "success";
    }
}
