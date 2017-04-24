package com.springboot.find.service;

import com.google.common.collect.Lists;
import com.springboot.common.dto.ListResponseDto;
import com.springboot.common.dto.ResponseDto;
import com.springboot.common.util.Collections3;
import com.springboot.common.util.DataUtil;
import com.springboot.common.util.FastDFSUtil;
import com.springboot.common.util.StringUtil;
import com.springboot.find.dao.MovingDao;
import com.springboot.find.entity.*;
import com.springboot.find.ws.dto.*;
import com.springboot.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by zl on 2017/3/10.
 */
@Service
@Transactional
public class MovingService {

    @Value("${ip:}")
    private String ip;

    @Autowired
    private MovingDao movingDao;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    public ResponseDto publishFind(List<MultipartFile> file, String userId, String content, String price, int type) throws UnsupportedEncodingException {
        switch (type) {
            case 0:
                break;
            case 1:
                publishBeauty(file, userId, content);
                break;
            case 2:
                publishMoving(file, userId, content);
                break;
            case 3:
                publishMarket(file, userId, content, price);
                break;
            case 4:
                break;
            default:
                break;
        }
        return new ResponseDto();

    }

    private void publishMarket(List<MultipartFile> file, String userId, String content, String price) throws UnsupportedEncodingException {
        Market market = new Market();
        if (null != file) {
            StringBuffer imageUrls = new StringBuffer();
            try {
                for (int i = 0; i < file.size(); i++) {
                    imageUrls.append(fastDFSUtil.saveImage(file.get(i).getInputStream()));
                    imageUrls.append(",");
                }
                String imageUrl = imageUrls.toString();
                market.setImageUrl(imageUrl.substring(0, imageUrl.length() - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        market.setContent(URLDecoder.decode(content, "utf-8"));
        market.setPrice(price);
        market.setUser(movingDao.findById(User.class, Long.parseLong(userId)));
        movingDao.persist(market);
    }

    private void publishBeauty(List<MultipartFile> file, String userId, String content) throws UnsupportedEncodingException {
        Beauty beauty = new Beauty();
        if (null != file) {
            try {
                beauty.setImageUrl(fastDFSUtil.saveImage(file.get(0).getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        beauty.setContent(URLDecoder.decode(content, "utf-8"));
        beauty.setUser(movingDao.findById(User.class, Long.parseLong(userId)));
        movingDao.persist(beauty);
    }


    public void publishMoving(List<MultipartFile> file, String userId, String content) throws UnsupportedEncodingException {
        Moving moving = new Moving();
        if (null != file) {
            StringBuffer imageUrls = new StringBuffer();
            try {
                for (int i = 0; i < file.size(); i++) {
                    imageUrls.append(fastDFSUtil.saveImage(file.get(i).getInputStream()));
                    imageUrls.append(",");
                }
                String imageUrl = imageUrls.toString();
                moving.setImageUrl(imageUrl.substring(0, imageUrl.length() - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        moving.setContent(URLDecoder.decode(content, "utf-8"));
        moving.setUser(movingDao.findById(User.class, Long.parseLong(userId)));
        movingDao.persist(moving);
    }


    public ListResponseDto<MovingDto> listMoving(int offset, int size) {
        ListResponseDto<MovingDto> listResponseDto = new ListResponseDto<>();
        List<MovingDto> listMovingDto = Lists.newArrayList();
        List<Moving> listMoving = movingDao.listMoving((offset - 1) * size, size);
        listMoving.stream().forEach(p -> listMovingDto.add(formatMoving(p)));
        listResponseDto.setObj(listMovingDto);
        return listResponseDto;
    }

    public List<BeautyDto> listBeauty(int offset, int size) {
        List<BeautyDto> listBeautyDto = Lists.newArrayList();
        List<Beauty> listMoving = movingDao.listBeauty((offset - 1) * size, size);
        listMoving.stream().forEach(p -> listBeautyDto.add(formatBeauty(p)));
        return listBeautyDto;
    }

    public List<MarketDto> listMarket(int offset, int size) {
        List<MarketDto> marketDtos = Lists.newArrayList();
        List<Market> markets = movingDao.listMarket((offset - 1) * size, size);
        markets.stream().forEach(p -> marketDtos.add(formatMarket(p)));
        return marketDtos;
    }

    public BeautyDto formatBeauty(Beauty beauty) {
        BeautyDto BeautyDto = new BeautyDto();
        BeautyDto.setContent(beauty.getContent());
        BeautyDto.setImageUrl(ip + beauty.getImageUrl());
        BeautyDto.setPublishTime(DataUtil.formatDate(beauty.getCreationTime()));
        BeautyDto.setAvatarUrl(ip + beauty.getUser().getAvatarUrl());
        BeautyDto.setUserId(String.valueOf(beauty.getUser().getId()));
        BeautyDto.setUserName(beauty.getUser().getNickName());
        return BeautyDto;
    }


    public MarketDto formatMarket(Market market) {
        MarketDto marketDto = new MarketDto();
        marketDto.setContent(market.getContent());
        if (StringUtil.isNotEmpty(market.getImageUrl())) {
            String[] imageUrl = market.getImageUrl().split(",");
            List<String> imageUrls = Lists.newArrayList();
            for (String s : imageUrl) {
                imageUrls.add(ip + s);
            }
            marketDto.setImageUrl(imageUrls);
        }
        marketDto.setPublishTime(DataUtil.formatDate(market.getCreationTime()));
        marketDto.setPrice(market.getPrice());
        marketDto.setAvatarUrl(ip + market.getUser().getAvatarUrl());
        marketDto.setUserId(String.valueOf(market.getUser().getId()));
        marketDto.setUserName(market.getUser().getNickName());
        return marketDto;
    }

    public MovingDto formatMoving(Moving moving) {
        MovingDto movingDto = new MovingDto();
        movingDto.setAvatarUrl(ip + moving.getUser().getAvatarUrl());
        movingDto.setUserId(String.valueOf(moving.getUser().getId()));
        movingDto.setUserName(moving.getUser().getNickName());
        movingDto.setContent(moving.getContent());
        if (StringUtil.isNotEmpty(moving.getImageUrl())) {
            String[] imageUrl = moving.getImageUrl().split(",");
            List<String> imageUrls = Lists.newArrayList();
            for (String s : imageUrl) {
                imageUrls.add(ip + s);
            }
            movingDto.setImageUrl(imageUrls);
        }
        movingDto.setPublishTime(DataUtil.formatDate(moving.getCreationTime()));
        movingDto.setPosition(moving.getPosition());
        movingDto.setCommentCount(String.valueOf(moving.getCommentCount()));
        movingDto.setGoodCount(String.valueOf(moving.getGoodCount()));
        if (Collections3.isNotEmpty(moving.getCommentList())) {
            List<CommentDto> listComment = Lists.newArrayList();
            moving.getCommentList().stream().forEach(p -> listComment.add(formatComment(p)));
            movingDto.setListComment(listComment);
        }
        return movingDto;
    }

    public CommentDto formatComment(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(comment.getContent());
        commentDto.setCommentUserId(String.valueOf(comment.getCommentUser().getId()));
        commentDto.setUnCommentUserId(String.valueOf(comment.getUnCommentUser().getId()));
        commentDto.setMovingId(String.valueOf(comment.getMoving().getId()));
        return commentDto;
    }

    public ResponseDto publishComment(CommentDto commentDto) {
        ListResponseDto<CommentDto> listResponseDto = new ListResponseDto<>();
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setMoving(movingDao.findById(Moving.class, Long.parseLong(commentDto.getMovingId())));
        comment.setCommentUser(movingDao.findById(User.class, Long.parseLong(commentDto.getCommentUserId())));
        comment.setUnCommentUser(movingDao.findById(User.class, Long.parseLong(commentDto.getUnCommentUserId())));
        movingDao.persist(comment);
        Moving moving = movingDao.findById(Moving.class, commentDto.getMovingId());
        moving.setCommentCount(moving.getCommentCount().add(new BigDecimal(1)));
//        List<CommentDto> commentDtoList = Lists.newArrayList();
//        List<Comment> commentList = movingDao.findById(Moving.class, Long.parseLong(commentDto.getMovingId())).getCommentList();
//        commentList.stream().forEach(p -> commentDtoList.add(formatComment(p)));
//        listResponseDto.setObjs(commentDtoList);
        return new ResponseDto();
    }

    public List<WeiXinDto> allWeiXin() {
        List<WeiXinDto> weiXinDtoList  = Lists.newArrayList();
        List<WeiXin> weiXin = movingDao.listWeiXin();
        for (WeiXin xin : weiXin) {
            WeiXinDto weiXinDto = new WeiXinDto();
            weiXinDto.setContent(xin.getContent());
            weiXinDto.setImageUrl(ip + xin.getImageUrl());
            weiXinDto.setUrl(xin.getUrl());
            weiXinDto.setData(DataUtil.dateToString(xin.getCreationTime()));
            weiXinDtoList.add(weiXinDto);
        }
        return weiXinDtoList;
    }
}
