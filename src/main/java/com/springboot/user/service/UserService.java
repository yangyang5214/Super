package com.springboot.user.service;

import com.google.common.collect.Lists;
import com.springboot.common.dao.BaseDao;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.ExcelUtil;
import com.springboot.common.util.StringUtil;
import com.springboot.user.dao.UserDao;
import com.springboot.user.entity.User;
import com.springboot.user.ws.dto.RegisterDto;
import com.springboot.user.ws.dto.UserDto;
import com.springboot.user.ws.dto.UserPoiDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Created by zl on 2017/3/9.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserDao userDao;

   public ResponseDto register(RegisterDto registerDto){
       ResponseDto responseDto = new ResponseDto();
       User user = baseDao.find(User.class,"USERNAME",registerDto.getUsername());
       if (nonNull(user)){
           responseDto.setSuccess(Boolean.FALSE);
           responseDto.setMessage("用户已存在！");
           return responseDto;
       }
       user = new User();
       user.setUsername(registerDto.getUsername());
       user.setPassword(registerDto.getPassword());
       baseDao.persist(user);
       return new ResponseDto();
   }



    public ResponseDto login(RegisterDto registerDto){
        ResponseDto responseDto = new ResponseDto();
        User user = baseDao.find(User.class,"USERNAME",registerDto.getUsername());
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户未注册！");
            return responseDto;
        }
        if (!user.getPassword().equals(registerDto.getPassword())){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户名或密码错误！");
            return responseDto;
        }
        responseDto.setObj(new UserDto(user));
        return responseDto;
    }

    public ResponseDto updataUserInfo(UserDto userDto){
        ResponseDto responseDto = new ResponseDto();
        if (StringUtil.isEmpty(userDto.getId().toString())){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("用户ID不存在！");
            return responseDto;
        }
        User user = baseDao.findById(User.class,userDto.getId());
        if (isNull(user)){
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage("不存在此用户！");
            return responseDto;
        }
        user.setUniversityName(userDto.getUniversityName());
        user.setNickName(userDto.getNickName());
        user.setSex(userDto.getSex());
        user.setArea(userDto.getArea());
        user.setSignature(userDto.getSignature());
        baseDao.persist(user);
        return  new ResponseDto();
    }

    public  ResponseDto uploadUserMessage(InputStream fileInputStream){
        List<UserPoiDto> userPoiDtoList = Lists.newArrayList();
        ResponseDto responseDto = new ResponseDto();
        Workbook workbook = ExcelUtil.getWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();
        ExcelUtil.ExcelHeadHelper excelHeadHelper = new ExcelUtil.ExcelHeadHelper();
        String errorExcelHead = excelHeadHelper.build(UserPoiDto.class).index(sheet.getRow(0));
        if (nonNull(errorExcelHead)) {
            responseDto.setSuccess(Boolean.FALSE);
            responseDto.setMessage(errorExcelHead);
            return responseDto;
        }
        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            if (nonNull(row)) {
                UserPoiDto userPoiDto = new UserPoiDto();
                excelHeadHelper.fill(row, userPoiDto);
                if (StringUtil.isNotEmpty(userPoiDto.getUsername()) && StringUtil.isNotEmpty(userPoiDto.getUsername())){
                    userPoiDtoList.add(userPoiDto);
                }
            }
        }
        userPoiDtoList.stream().forEach(p->register(new RegisterDto(p)));
        return new ResponseDto();
    }


    public InputStream exportUserMessage(){
        TemplateExportParams params = new TemplateExportParams("export/用户信息导出格式.xls");
        Map<String, Object> maps = new HashMap<>();
        maps.put("user","APP");
        List<Object[]> listUserInfo = userDao.queryUserMessageForExport();
        List<Map<String,Object>> mapsUserInfo = Lists.newArrayList();
        for (Object[] object : listUserInfo) {
            Map<String, Object> map = new HashMap<>();
            map.put("username",object[0].toString());
            map.put("password",object[1].toString());
            mapsUserInfo.add(map);
        }
        maps.put("maplist",mapsUserInfo);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Workbook workbook = ExcelExportUtil.exportExcel(params, maps);
        try {
            workbook.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(output.toByteArray());
    }


}
