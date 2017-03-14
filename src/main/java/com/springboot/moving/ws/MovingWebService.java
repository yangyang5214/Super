package com.springboot.moving.ws;

import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.filter.BaseWebService;
import com.springboot.moving.service.MovingService;
import com.springboot.moving.ws.dto.MovingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/3/11.
 */
@Controller
@ResponseBody
@RequestMapping("/moving")
public class MovingWebService extends BaseWebService {

    @Autowired
    private MovingService movingService;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ResponseDto publishMoving(
            @RequestParam("file") MultipartFile file,
            @RequestParam("moving") MovingDto movingDto) {
        return movingService.publishMoving(file, movingDto, getUser().getId());
    }

    @RequestMapping(value = "/allMoving", method = RequestMethod.GET)
    public ListResponseDto<MovingDto> listMoving() {
        return movingService.listMoving();
    }
}
