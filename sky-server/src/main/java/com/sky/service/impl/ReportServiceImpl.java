package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public TurnoverReportVO getTurnoverStatistic(LocalDate begin, LocalDate end) {

        //建立一个集合用于存放从begin到end范围内的每天的日期
        List<LocalDate> dataList = new ArrayList<>();
        dataList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dataList.add(begin);
        }

        //存放每日的营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dataList) {
            //查询date日期对应的营业额数据，营业额指的是：状态“已完成”的订单金额合计
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            //select sum(amount) from orders where order_time > beginTime and order_Time < endTime and status = 5
            HashMap map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);

            turnover = turnover == null ? 0.0 : turnover;    // 当营业为空时，赋值为0；

            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dataList, ",")) //将日期按照dateList要求的格式，按照“，”分隔开
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO getUserStatistic(LocalDate begin, LocalDate end) {
        //建立一个集合用于存放从begin到end范围内的每天的日期
        List<LocalDate> dataList = new ArrayList<>();
        dataList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dataList.add(begin);
        }

        //存放每日的新增用户的数量 和 用户的总数量
        List<Integer> userList = new ArrayList<>();
        List<Integer> userTotalList = new ArrayList<>();

        for (LocalDate date : dataList) {
            //查询date日期对应的新增的用户数量
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            HashMap map = new HashMap();
            map.put("end", endTime);
            //select sum(amount) from user where  create_time < endTime
            Integer totalUser = userMapper.countByMap(map);    // 总用户的数量

            map.put("begin", beginTime);
            //select sum(amount) from user where create_time > beginTime and create_time < endTime
            Integer newUser = userMapper.countByMap(map);     // 新增用户的数量


            userList.add(newUser);
            userTotalList.add(totalUser);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dataList, ",")) //将日期按照dateList要求的格式，按照“，”分隔开
                .newUserList(StringUtils.join(userList, ","))
                .totalUserList(StringUtils.join(userTotalList, ","))
                .build();
    }
}
