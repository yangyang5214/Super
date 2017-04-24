package com.springboot.find.ws;

import com.google.gson.Gson;
import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.find.entity.Moving;
import com.springboot.find.service.MovingService;
import com.springboot.find.ws.dto.BeautyDto;
import com.springboot.find.ws.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
@Controller
@ResponseBody
@RequestMapping("/find")
public class MovingWebService {

    @Autowired
    private MovingService movingService;

    @RequestMapping(value = "moving/publish", method = RequestMethod.POST)
    public ResponseDto publishMoving(
            @RequestParam("files") List<MultipartFile> file,
            String userId,
            String content,
            String price,
            int type) {
        try {
            return movingService.publishFind(file,userId,content,price,type);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "moving/allMoving", method = RequestMethod.GET)
    public String listMoving(int size, int offset) {
        return new Gson().toJson(movingService.listMoving(offset, size));
    }

    @RequestMapping(value = "allWeiXin", method = RequestMethod.GET)
    public String listWeiXin() {
        return new Gson().toJson(movingService.allWeiXin());
    }

    @RequestMapping(value = "moving/publish/comment", method = RequestMethod.POST)
    public ResponseDto publishComment(CommentDto commentDto) {
        return movingService.publishComment(commentDto);
    }

    @RequestMapping(value = "beauty/allBeauty", method = RequestMethod.GET)
    public String publishComment(int size,int offset) {
        return new Gson().toJson(movingService.listBeauty(offset, size));
    }

    @RequestMapping(value = "market/allMarket", method = RequestMethod.GET)
    public String publishMarket(int size,int offset) {
        return new Gson().toJson(movingService.listMarket(offset, size));
    }


}
