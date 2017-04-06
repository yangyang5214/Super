package com.springboot.moving.ws;

import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.moving.service.MovingService;
import com.springboot.moving.ws.dto.CommentDto;
import com.springboot.moving.ws.dto.MovingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
@Controller
@ResponseBody
@RequestMapping("/moving")
public class MovingWebService {

    @Autowired
    private MovingService movingService;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ResponseDto publishMoving(
            List<MultipartFile> file) {
        try {
            return movingService.publishMoving(file);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/allMoving", method = RequestMethod.POST)
    public ListResponseDto<MovingDto> listMoving(
            @RequestParam("size") int size,
            @RequestParam("offset") int offset) {
        return movingService.listMoving(offset, size);
    }

    @RequestMapping(value = "/publish/comment", method = RequestMethod.POST)
    public ListResponseDto<CommentDto> publishComment(CommentDto commentDto) {
        return movingService.publishComment(commentDto);
    }


}
