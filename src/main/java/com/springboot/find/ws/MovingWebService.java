package com.springboot.find.ws;

import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.find.service.MovingService;
import com.springboot.find.ws.dto.CommentDto;
import com.springboot.find.ws.dto.MovingDto;
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
            List<MultipartFile> file) {
        try {
            return movingService.publishMoving(file);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "moving/allMoving", method = RequestMethod.POST)
    public ListResponseDto<MovingDto> listMoving(
            @RequestParam("size") int size,
            @RequestParam("offset") int offset) {
        return movingService.listMoving(offset, size);
    }

    @RequestMapping(value = "moving/publish/comment", method = RequestMethod.POST)
    public ListResponseDto<CommentDto> publishComment(CommentDto commentDto) {
        return movingService.publishComment(commentDto);
    }


}